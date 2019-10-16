package com.example.kplusregistration.view.ui


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.kplusregistration.databinding.FragmentRegisterBinding
import com.example.kplusregistration.viewmodel.RegisterViewModel
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.fragment_register.*
import org.json.JSONException


class RegisterFragment : Fragment() {

    private lateinit var mRegisterViewModel: RegisterViewModel
    private lateinit var mCallbackManager: CallbackManager
    private lateinit var mGoogleSignInOptions: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val TAG = "RegisterFragment"
    private val RC_GOOGLE_SIGN_IN = 0
    private lateinit var mBinding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        mBinding.lifecycleOwner = this
        return mBinding.root
//        return inflater.inflate(com.example.kplusregistration.R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRegisterViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        mBinding.registerViewModel = mRegisterViewModel

        mRegisterViewModel.registerUserMutableLiveData.observe(this, Observer {
            Log.d(TAG, "${mBinding.idEmailEditTextFragmentRegister.text}")
        })

        mCallbackManager = CallbackManager.Factory.create()
        id_facebook_login_button_fragment_register.setPermissions(listOf("email", "public_profile", "user_birthday"))
        id_facebook_login_button_fragment_register.fragment = this

        //for facebook
        var loggedOut: Boolean = AccessToken.getCurrentAccessToken() == null

        //for Facebook
        if (!loggedOut) {
            Log.d("API123", "Username is: ${Profile.getCurrentProfile().name}")

            getUserProfile(AccessToken.getCurrentAccessToken())
        }
        id_facebook_login_button_fragment_register.registerCallback(
            mCallbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    // App code
                    var loggedIn: Boolean = AccessToken.getCurrentAccessToken() == null
                    Log.d("API123", "loggedIn: $loggedIn ")
                    Toast.makeText(context, "onSuccess", Toast.LENGTH_SHORT).show()
                }

                override fun onCancel() {
                    // App code
                    Log.d("API123", "onCancel")
                }

                override fun onError(exception: FacebookException) {
                    // App code
                    Log.d("API123", "onError")
                }
            })

        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(activity as MainActivity, mGoogleSignInOptions)
        val iAccount: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(activity as MainActivity)

        if (iAccount == null)
            googleSignIn()
        else {
            //update(iAccount)
            googleUpdateUI(iAccount)

        }

        id_google_sign_in_button_fragment_register.setSize(SignInButton.SIZE_STANDARD)
        id_google_sign_in_button_fragment_register.setOnClickListener {
            //            Toast.makeText(this, "gmail", Toast.LENGTH_SHORT).show()
            googleSignIn()
        }
    }

    private fun googleSignIn() {
        val iSignInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(iSignInIntent, RC_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val iTask: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignTaskResult(iTask)
        }
    }

    private fun handleGoogleSignTaskResult(kCompletedTask: Task<GoogleSignInAccount>) {
        try {
            val iAccount: GoogleSignInAccount? = kCompletedTask.getResult(ApiException::class.java)
//            Signed in successfully, show authenticated UI.
//            Toast.makeText(this, "${iAccount?.email} and ${iAccount?.displayName}", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "${iAccount?.email} and ${iAccount?.displayName}")
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
//            updateUI(null);
        }
    }

    private fun getUserProfile(kCurrentAccessToken: AccessToken) {
        var iRequest: GraphRequest = GraphRequest.newMeRequest(
            kCurrentAccessToken
        ) { `object`, _ ->
            try {
                Log.d("API123", `object`.toString())
                val iFirstName = `object`["first_name"].toString()
                val iLastName = `object`["last_name"].toString()
                val iEmail = `object`["email"].toString()
                val iId = `object`["id"].toString()
                val userBday = `object`["user_birthday"].toString()
                val iImageUrl = "https://graph.facebook.com/$id/picture?type=normal"

                Log.d("API123", "$iFirstName, $iLastName, $iEmail, $iId, $iImageUrl, $userBday")
            } catch (e: JSONException) {
                println("$e")
            }
        }

        val iParameters = Bundle()
        iParameters.putString("fields", "first_name,last_name,email,id,picture.type(large),birthday")
        iRequest.parameters = iParameters
        iRequest.executeAsync()
    }

    private fun googleUpdateUI(kAccount: GoogleSignInAccount) {
//        val iIntent = Intent(context, ProfileFragment::class.java)
//        iIntent.putExtra("GOOGLE_ACCOUNT", kAccount )
//        startActivity(iIntent)
    }
}

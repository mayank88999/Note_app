package com.example.note_app.views

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.note_app.R
import com.example.note_app.databinding.FragmentLoginFragmentBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class Login_fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentLoginFragmentBinding
    private lateinit var auth: FirebaseAuth
    companion object {
        private const val RC_SIGN_IN = 9001
    }
    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {

            val data: Intent? = result.data
            handleSignInResult(data)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLoginFragmentBinding.inflate(inflater,container, false)
        binding.signInButton.setOnClickListener {
            signIn()
        }
        auth = FirebaseAuth.getInstance()
        return binding.root
    }
    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        val signInIntent = googleSignInClient.signInIntent

        signInLauncher.launch(signInIntent)
    }

    private fun handleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)

            account?.let { firebaseAuthWithGoogle(it.idToken!!) }
        } catch (e: ApiException) {
            Log.d("Error", "Google sign in failed: ${e.message}")
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser

                    findNavController().navigate(R.id.action_login_fragment_to_notes_fragment)
                    Toast.makeText(requireContext(), "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("Error", "Authentication failed")
                }
            }
    }
}
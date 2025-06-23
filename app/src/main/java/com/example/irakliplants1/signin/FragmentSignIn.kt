package com.example.irakliplants1.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.irakliplants1.R
import com.example.irakliplants1.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth

class FragmentSignIn : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.signInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (email.isEmpty()) {
                binding.emailInputLayout.error = "Please enter email"
                return@setOnClickListener
            } else {
                binding.emailInputLayout.error = null
            }

            if (password.isEmpty()) {
                binding.passwordInputLayout.error = "Please enter password"
                return@setOnClickListener
            } else {
                binding.passwordInputLayout.error = null
            }

            signInUser(email, password)
        }

        binding.goToSignUpTextView.setOnClickListener {
            findNavController().navigate(FragmentSignInDirections.actionFragmentSignInToFragmentSignUp())
        }
        binding.forgotPasswordTextView.setOnClickListener {
            findNavController().navigate(FragmentSignInDirections.actionFragmentSignInToFragmentForgetPassword())
        }
    }

    private fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Signed in successfully", Toast.LENGTH_SHORT).show()

                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.fragmentSignIn, true)
                        .build()

                    findNavController().navigate(
                        FragmentSignInDirections.actionFragmentSignInToFragmentPlantList(),
                        navOptions
                    )
                } else {
                    Toast.makeText(requireContext(), "Authentication failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.irakliplants1.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.irakliplants1.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FragmentSignUp : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private val database = FirebaseDatabase.getInstance("https://irakliplants1-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val usersRef = database.getReference("users")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.signUpButton.setOnClickListener {
            val firstName = binding.firstNameEditText.text.toString().trim()
            val lastName = binding.lastNameEditText.text.toString().trim()
            val ageStr = binding.ageEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val repeatPassword = binding.repeatPasswordEditText.text.toString().trim()

            if (firstName.isEmpty()) {
                binding.firstNameInputLayout.error = "შეიყვანე სახელი"
                return@setOnClickListener
            } else binding.firstNameInputLayout.error = null

            if (lastName.isEmpty()) {
                binding.lastNameInputLayout.error = "შეიყვანე გვარი"
                return@setOnClickListener
            } else binding.lastNameInputLayout.error = null

            val age = ageStr.toIntOrNull()
            if (age == null || age <= 0) {
                binding.ageInputLayout.error = "შეიყვანე სწორი ასაკი"
                return@setOnClickListener
            } else binding.ageInputLayout.error = null

            if (email.isEmpty()) {
                binding.emailInputLayout.error = "შეიყვანე ელფოსტა"
                return@setOnClickListener
            } else binding.emailInputLayout.error = null

            if (password.length < 6) {
                binding.passwordInputLayout.error = "პაროლი მინიმუმ 6 სიმბოლო უნდა იყოს"
                return@setOnClickListener
            } else binding.passwordInputLayout.error = null

            if (password != repeatPassword) {
                binding.repeatPasswordInputLayout.error = "პაროლები არ ემთხვევა"
                return@setOnClickListener
            } else binding.repeatPasswordInputLayout.error = null

            signUpUser(email, password, firstName, lastName, age)
        }

        binding.goToSignInTextView.setOnClickListener {
            findNavController().navigate(FragmentSignUpDirections.actionFragmentSignUpToFragmentSignIn())
        }
    }

    private fun signUpUser(email: String, password: String, firstName: String, lastName: String, age: Int) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    if (uid == null) {
                        Toast.makeText(requireContext(), "მომხმარებელი ვერ ავყავით ავტორიზაციაში", Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }

                    val user = User(uid, email, firstName, lastName, age)
                    usersRef.child(uid).setValue(user)
                        .addOnCompleteListener { saveTask ->
                            if (saveTask.isSuccessful) {
                                Toast.makeText(requireContext(), "რეგისტრაცია წარმატებით დასრულდა", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(FragmentSignUpDirections.actionFragmentSignUpToFragmentPlantList())
                            } else {
                                Toast.makeText(requireContext(), "მომხმარებლის შენახვა ვერ მოხერხდა: ${saveTask.exception?.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                } else {
                    Toast.makeText(requireContext(), "რეგისტრაცია ვერ მოხერხდა: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    data class User(
        val uid: String = "",
        val email: String = "",
        val firstName: String = "",
        val lastName: String = "",
        val age: Int = 0
    )
}

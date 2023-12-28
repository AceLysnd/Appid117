package com.qatros.logibug.ui.user.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.R
import com.qatros.logibug.databinding.FragmentRegisterBinding

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.isEnabled = false

        binding.apply {
            binding.tvLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }

            etEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //Before
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    updateStatusButtonRegister()
                }

                override fun afterTextChanged(p0: Editable?) {
                    //After
                }

            })

            etName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //Before
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    updateStatusButtonRegister()
                }

                override fun afterTextChanged(p0: Editable?) {
                    //after
                }

            })

            etPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //Before
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    updateStatusButtonRegister()
                }

                override fun afterTextChanged(p0: Editable?) {
                    //After
                }

            })

            etConfirmationPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //Before
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    updateStatusButtonRegister()
                }

                override fun afterTextChanged(p0: Editable?) {
                    //After
                }

            })

            btnRegister.setOnClickListener {
                registerValidate()
                registerViewModel.isSuccess.observe(viewLifecycleOwner) { isSuccess ->
                    if (isSuccess == true) {
                        it.findNavController()
                            .navigate(R.id.action_registerFragment_to_loginFragment)
                        Toast.makeText(
                            requireContext(),
                            "Successful registration",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Registration failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }
    }

    private fun registerValidate() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmationPassword = binding.etConfirmationPassword.text.toString()

        if (email.isNotBlank() && password.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email)
                .matches() && password.length > 7 && password == confirmationPassword
        ) {
            registerViewModel.register(
                name,
                email,
                password,
                confirmationPassword
            )
        }

    }

    private fun updateStatusButtonRegister() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmationPassword = binding.etConfirmationPassword.text.toString().trim()

        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.length > 7
        val isConfirmationPasswordValid = password == confirmationPassword

        binding.btnRegister.isEnabled =
            name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmationPassword.isNotEmpty() && isEmailValid && isPasswordValid && isConfirmationPasswordValid

        if (binding.btnRegister.isEnabled) {
            val colorButtonPrimary = ContextCompat.getColor(requireActivity(), R.color.Primary)
            val colorTextWhite = ContextCompat.getColor(requireActivity(), R.color.white)

            binding.btnRegister.setBackgroundColor(colorButtonPrimary)
            binding.btnRegister.setTextColor(colorTextWhite)
        } else {
            val colorButtonFalse = ContextCompat.getColor(requireActivity(), R.color.neutral_second)
            val colorTextNeutral = ContextCompat.getColor(requireActivity(), R.color.neutral)

            binding.btnRegister.setBackgroundColor(colorButtonFalse)
            binding.btnRegister.setTextColor(colorTextNeutral)
        }

        if (email.isNotEmpty() && !isEmailValid) {
            binding.etEmail.error = "Invalid email address"
        } else {
            binding.etEmail.error = null
        }

        if (!isPasswordValid) {
            binding.itPassword.isPasswordVisibilityToggleEnabled = false
            binding.etPassword.error = "Password must be at least 8 character"
        } else {
            binding.itPassword.isPasswordVisibilityToggleEnabled = true
            binding.etPassword.error = null
        }

        if (!isConfirmationPasswordValid){
            binding.itConfirmationPassword.isPasswordVisibilityToggleEnabled = false
            binding.etConfirmationPassword.error = "Must be the same as the password"
        }else{
            binding.itConfirmationPassword.isPasswordVisibilityToggleEnabled = true
            binding.etConfirmationPassword.error = null
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
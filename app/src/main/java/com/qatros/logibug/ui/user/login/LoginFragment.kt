package com.qatros.logibug.ui.user.login

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
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.R
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentLoginBinding

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.isEnabled = false

        binding.apply {

            tvForgotpassword.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "This feature is currently not available",
                    Toast.LENGTH_SHORT
                ).show()
            }

            tvRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

            etEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //Before Text Change
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    updateStatusButtonLogin()
                }

                override fun afterTextChanged(p0: Editable?) {
                    //After Text Change
                }

            })

            etPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //Before Text Change
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    updateStatusButtonLogin()
                }

                override fun afterTextChanged(p0: Editable?) {
                    //After Text Change
                }

            })

            btnLogin.setOnClickListener {
                loginValidate()
            }

            loginViewModel.isSuccess.observe(viewLifecycleOwner) {
                if (it) {
                    findNavController().navigate(R.id.action_loginFragment_to_homePageFragment)
                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Login failed, check email and password! or check activation",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
    }

    private fun updateStatusButtonLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.length > 7

        binding.btnLogin.isEnabled =
            email.isNotEmpty() && password.isNotEmpty() && isEmailValid && isPasswordValid

        if (binding.btnLogin.isEnabled) {
            val colorButtonPrimary = ContextCompat.getColor(requireActivity(), R.color.Primary)
            val colorTextWhite = ContextCompat.getColor(requireActivity(), R.color.white)

            binding.btnLogin.setBackgroundColor(colorButtonPrimary)
            binding.btnLogin.setTextColor(colorTextWhite)
        } else {
            val colorButtonFalse = ContextCompat.getColor(requireActivity(), R.color.neutral_second)
            val colorTextNeutral = ContextCompat.getColor(requireActivity(), R.color.neutral)

            binding.btnLogin.setBackgroundColor(colorButtonFalse)
            binding.btnLogin.setTextColor(colorTextNeutral)
        }

        if (email.isNotEmpty() && !isEmailValid) {
            binding.etEmail.error = "Invalid email address"
        } else {
            binding.etEmail.error = null
        }

        if (password.isNotEmpty() && !isPasswordValid) {
            binding.passwordTextInputLayout.isPasswordVisibilityToggleEnabled = false
            binding.etPassword.error = "Password must be at least 8 character"
        } else {
            binding.passwordTextInputLayout.isPasswordVisibilityToggleEnabled = true
            binding.etPassword.error = null
        }

    }

    private fun loginValidate() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isNotBlank() && password.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email)
                .matches() && password.length > 7
        ) {
            loginViewModel.login(email, password)
            preferenceViewModel.saveLoginState()
            loginViewModel.loginResponse.observe(viewLifecycleOwner) {
                preferenceViewModel.saveUser(
                    it.token, it.refreshToken, it.exp, it.data.email, it.data.id, it.data.name
                )
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


package com.qatros.logibug.ui.user.delete_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.qatros.logibug.R
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.DialogDeleteUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteUser : DialogFragment() {
    private var _binding: DialogDeleteUserBinding? = null
    private val binding get() = _binding!!

    private var token = ""
    private var idUser = 0

    private val deleteUserViewModel: DeleteUserViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogDeleteUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
            idUser = it.idUser
            binding.tvNameUser.text = it.name
        }

        binding.apply {
            btnDeleteUser.setOnClickListener {
                deleteUserViewModel.deleteUser(
                    token, idUser
                )
                deleteUserViewModel.message.observe(viewLifecycleOwner) {
                    Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                    if (it == "User success deleted") {
                        findNavController().navigate(R.id.action_deleteUser_to_loginFragment)
                    } else {
                        dismiss()
                    }
                }
            }
            btnCancelDeleteUser.setOnClickListener {
                dismiss()
            }
        }

    }
}
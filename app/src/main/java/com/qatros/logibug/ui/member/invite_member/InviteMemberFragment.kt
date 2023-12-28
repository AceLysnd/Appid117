package com.qatros.logibug.ui.member.invite_member

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.R
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentInviteMemberBinding

@AndroidEntryPoint
class InviteMemberFragment : Fragment() {

    private var _binding: FragmentInviteMemberBinding? = null
    private val binding get() = _binding!!

    private val inviteMemberViewModel: InviteMemberViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private val args: InviteMemberFragmentArgs by navArgs()

    private var role = "Role"

    private var listRole = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentInviteMemberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnInviteMember.isEnabled = false

        var token = ""
        val projectId = args.projectId
        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
        }

        binding.etEmailInviteMember.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Before
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updateStatusInviteButton()
            }

            override fun afterTextChanged(p0: Editable?) {
                //After
            }

        })

        listRole = mutableListOf("Role", "dev", "qa")
        val roleDropDown = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_dropdown_item, listRole
        )
        binding.actRoleInviteMember.setAdapter(roleDropDown)
        binding.actRoleInviteMember.setText(listRole[0], false)
        binding.actRoleInviteMember.setOnItemClickListener { _, _, position, _ ->
            role = listRole[position]
        }

        inviteMemberViewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            if (it == "Success for invite") {
                findNavController().popBackStack()
            }
        }

        binding.btnInviteMember.setOnClickListener {
            val email = binding.etEmailInviteMember.text.toString()
            val role = role

            if (role == "Role") {
                Toast.makeText(requireContext(), "Silahkan pilih role", Toast.LENGTH_SHORT).show()
            }else{
                inviteMemberViewModel.inviteMember(
                    token,
                    projectId,
                    email,
                    role
                )
            }
        }

        binding.ibNavigateBackInviteMember.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun updateStatusInviteButton(){
        val email = binding.etEmailInviteMember.text.toString()

        binding.btnInviteMember.isEnabled = email.isNotEmpty()

        if (binding.btnInviteMember.isEnabled) {
            val colorButtonPrimary = ContextCompat.getColor(requireActivity(), R.color.Primary)
            val colorTextWhite = ContextCompat.getColor(requireActivity(), R.color.white)

            binding.btnInviteMember.setBackgroundColor(colorButtonPrimary)
            binding.btnInviteMember.setTextColor(colorTextWhite)
        } else {
            val colorButtonFalse = ContextCompat.getColor(requireActivity(), R.color.neutral_second)
            val colorTextNeutral = ContextCompat.getColor(requireActivity(), R.color.neutral)

            binding.btnInviteMember.setBackgroundColor(colorButtonFalse)
            binding.btnInviteMember.setTextColor(colorTextNeutral)
        }
    }

}
package com.qatros.logibug.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.qatros.logibug.R
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val preferenceViewModel: PreferenceViewModel by viewModels()
    private val getProfilesViewModel: GetProfilesViewModel by viewModels()

    private var token = ""
    private var userId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userLogin()
        getUserData()
        btnAction()

    }

    private fun showDialog() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog_logout, null)
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)

        val alertDialog = alertDialogBuilder.create()
        alertDialog.setCancelable(false)
        alertDialog.setCanceledOnTouchOutside(false)

        val btnConfirm = dialogView.findViewById<Button>(R.id.confirm_yes_logout_button)
        val btnCancel = dialogView.findViewById<Button>(R.id.confirm_no_logout_button)

        btnConfirm.setOnClickListener {
            preferenceViewModel.logout()
            alertDialog.dismiss()
            findNavController().navigate(R.id.action_accountAchievementFragment_to_loginFragment)
        }

        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun getUserData() {
        binding.apply {

            preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
                token = it.token
                userId = it.idUser
                tvNameAccount.text = it.name
                tvEmailAccount.text = it.email
            }

            getProfilesViewModel.profiles.observe(viewLifecycleOwner) {
                tvNameAccount.text = it.data.name
                Glide.with(requireContext())
                    .load("${it.data.imageProfile.replace("http:", "https:")}")
                    .centerCrop()
                    .error(R.drawable.ic_upload)
                    .fitCenter()
                    .into(ivProfileAccount)
            }
        }

    }

    private fun btnAction() {
        binding.apply {
            tvLogout.setOnClickListener {
                preferenceViewModel.logout()
                preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
                    if (it.token.isEmpty()) {
                        findNavController().navigate(R.id.action_accountAchievementFragment_to_loginFragment)
                    }
                }
            }

            tvAccountSetting.setOnClickListener {
                findNavController().navigate(R.id.action_accountAchievementFragment_to_accountSettingFragment)
            }
            tvAchievement.setOnClickListener {
                findNavController().navigate(R.id.action_accountAchievementFragment_to_achievementFragment)
            }
            tvAbout.setOnClickListener {
                findNavController().navigate(R.id.action_accountAchievementFragment_to_aboutFragment)
            }
            tvLogout.setOnClickListener {
                showDialog()
            }

        }
    }

    private fun userLogin() {
        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            getProfilesViewModel.getProfiles(it.token)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
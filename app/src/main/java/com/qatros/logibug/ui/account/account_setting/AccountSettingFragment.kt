package com.qatros.logibug.ui.account.account_setting

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.R
import com.qatros.logibug.core.data.utils.createCustomTempFile
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.DialogDeleteUserBinding
import com.qatros.logibug.databinding.FragmentAccountSettingBinding
import com.qatros.logibug.ui.homepage.HomePageFragmentDirections
import com.qatros.logibug.ui.scenario.list_scenario.ListScenarioFragmentDirections
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

@AndroidEntryPoint
class AccountSettingFragment : Fragment() {

    private var bind: FragmentAccountSettingBinding? = null
    private val binding get() = bind!!
    private val addAccount: AccountSettingViewModel by viewModels()
    private val preferenceViewModel : PreferenceViewModel by viewModels()
    private var token = ""
    private var imgFile = ""
    private var name = ""
    private val REQUEST_IMAGE_SELECT = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        bind = FragmentAccountSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "ubah profile"

        onClickListener()

        binding.apply {
            preferenceViewModel.getLoginState().observe(viewLifecycleOwner){
                token = it.token
            }

            etEmail.isEnabled = false
            addAccount.message.observe(viewLifecycleOwner){
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                if (it == "isSuccessful") {
                    findNavController().navigate(R.id.action_accountSettingFragment_to_accountAchievementFragment)
                }
            }
            btnSave.setOnClickListener {
                name = binding.etName.text.toString()
                if (name.isEmpty()){
                    etName.error = "Text cannot be empty"
                }
                else{
                    addAccount.addProfile(token, name, imgFile)
                }
            }

            ibNavigateBackAccountSetting.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        binding.tvDeleteAccount.setOnClickListener{
            findNavController().navigate(R.id.action_accountSettingFragment_to_deleteUser)
        }

    }

    private fun onClickListener() {
        with(binding){
            selectImg.setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, REQUEST_IMAGE_SELECT)
            }

        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_SELECT && resultCode == Activity.RESULT_OK && data != null){
            val selectImage: Uri? = data.data
            binding.imageProfile.setImageURI(selectImage)
            val imageprofil = uriToFile(selectImage!!, requireContext())
            imgFile = imageprofil.toString()
        }
    }
    private fun nullCheck(string: String): String {
        if (string == "null") {
            return ""
        } else {
            return string
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        bind = null
    }

    private fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createCustomTempFile(context)

        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }
}
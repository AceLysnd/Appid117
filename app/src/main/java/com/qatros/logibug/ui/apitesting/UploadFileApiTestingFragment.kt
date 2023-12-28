package com.qatros.logibug.ui.apitesting

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.core.data.utils.timeStamp
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentUploadFileApiTestingBinding
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

@AndroidEntryPoint
class UploadFileApiTestingFragment : Fragment() {

    private var _binding: FragmentUploadFileApiTestingBinding? = null
    private val binding get() = _binding!!

    private val uploadFileJsonViewModel: UploadFileJsonViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private val args: UploadFileApiTestingFragmentArgs by navArgs()

    private var selectedUri: Uri? = null

    private var selectedFileJson = ""

    private var token = ""

    private val filePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val fileName = getFileName(uri)
                binding.tvUploadFileJson.text = fileName
                if (fileName != "Upload file json") {
                    binding.ivUploadRequestUpload.visibility = View.GONE
                }
                selectedUri = it
                val fileJson = uriToFile(selectedUri!!, requireContext())
                selectedFileJson = fileJson.toString()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUploadFileApiTestingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projectId = args.projectId
        val versionId = args.versionId

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
        }

        binding.cvUploadFileApiTesting.setOnClickListener {
            filePickerLauncher.launch("*/*")
        }

        binding.ibNavigateBackUploadFile.setOnClickListener {
            findNavController().popBackStack()
        }

        uploadFileJsonViewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            if (it == "Berhasil menyimpan file") {
                val action =
                    UploadFileApiTestingFragmentDirections.actionUploadFileApiTestingFragmentToListDataJsonFragment(
                        projectId,
                        versionId
                    )
                findNavController().navigate(action)
            }
        }

        binding.btnSendRequestUpload.setOnClickListener {
            val inputStream = selectedUri?.let { it ->
                requireContext().contentResolver.openInputStream(
                    it
                )
            }
            if (inputStream != null) {
                uploadFileJsonViewModel.uploadFileJson(token, versionId, selectedFileJson)
            } else {
                Toast.makeText(requireContext(), "Choose file json", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            requireContext().contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    result =
                        cursor.getString(cursor.getColumnIndexOrThrow("_display_name"))
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != -1) {
                result = result?.substring(cut!! + 1)
            }
        }
        return result ?: ""
    }

    private fun uriToFile(selectedFile: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createCustomTempFile(context)

        val inputStream = contentResolver.openInputStream(selectedFile) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }

    private fun createCustomTempFile(context: Context): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        return File.createTempFile(timeStamp, ".json", storageDir)
    }

}
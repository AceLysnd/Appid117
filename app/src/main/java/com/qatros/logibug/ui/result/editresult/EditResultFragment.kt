package com.qatros.logibug.ui.result.editresult

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.BuildConfig
import com.qatros.logibug.R
import com.qatros.logibug.core.data.utils.uriToFile
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentEditResultBinding
@AndroidEntryPoint
class EditResultFragment : Fragment() {

    private var bind: FragmentEditResultBinding? = null
    private val binding get() = bind!!

    private val editResultViewModel : EditResultViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private val args: EditResultFragmentArgs by navArgs()
    private var token = ""

    private var status = "Result State"
    private var priority = "Priority"
    private var severity = "Severity"

    private var liststatus = mutableListOf<String>()
    private var listpriority = mutableListOf<String>()
    private var listseverity = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        bind = FragmentEditResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var testCaseId = args.testCaseId
        var isEdit = args.isEdited
        var resultId = args.resultId

        var imgURI: Uri? = null

        binding.apply {

            etActuallyAddResult.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //Before
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    updateStatusCreateButton()
                }

                override fun afterTextChanged(p0: Editable?) {
                    //After
                }
            })
            etNoteAddResult.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //Before
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    updateStatusCreateButton()
                }

                override fun afterTextChanged(p0: Editable?) {
                    //After
                }
            })

            if (isEdit) {

                btnSaveAddResult.isEnabled = false

                preferenceViewModel.getLoginState().observe(viewLifecycleOwner){
                    token = it.token
                }

                editResultViewModel.results.observe(viewLifecycleOwner){
                    etActuallyAddResult.setText(it.data.actual.replace("\"", ""))
                    etNoteAddResult.setText(it.data.note.replace("\"", ""))
                }

                editResultViewModel.getResultsById(token, testCaseId)
                liststatus = mutableListOf("result state", "pass", "fail")
                val statusDropDown = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    liststatus
                )
                actResultStateInAddResult.setAdapter(statusDropDown)
                actResultStateInAddResult.setText(liststatus[0], false)
                actResultStateInAddResult.setOnItemClickListener{ _, _, position, _ ->
                    status = liststatus[position]
                }

                listpriority = mutableListOf("priority", "urgent", "high", "medium", "low")
                val priorityDropDown = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    listpriority
                )
                actPriorityInAddResult.setAdapter(priorityDropDown)
                actPriorityInAddResult.setText(listpriority[0], false)
                actPriorityInAddResult.setOnItemClickListener{ _, _, position, _ ->
                    priority = listpriority[position]
                }

                listseverity = mutableListOf("severity", "critical", "major", "minor", "low")
                val severityDropDown = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    listseverity
                )
                actSeverityInAddResult.setAdapter(severityDropDown)
                actSeverityInAddResult.setText(listseverity[0], false)
                actSeverityInAddResult.setOnItemClickListener{ _, _, position, _ ->
                    severity = listseverity[position]
                }

                val resultLauncher =
                    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                        if (result.resultCode == Activity.RESULT_OK) {
                            imgURI = result.data?.data
                            Log.d("test data uri", imgURI.toString())
                            ivUploadAddResult.setImageURI(imgURI)
                        }
                    }

                ivUploadAddResult.setOnClickListener{
                    Dexter.withContext(requireContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(object : PermissionListener {
                            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                                val i = Intent(Intent.ACTION_PICK)
                                i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                                resultLauncher.launch(i)
                            }

                            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                                val alertBuilder = AlertDialog.Builder(requireContext())
                                alertBuilder.apply {
                                    setTitle("Membutuhkan persetujuan!")
                                    setMessage("Aplikasi ini memerlukan izin untuk dapat mengakses fitur ini, anda dapat mengatur perizinan aplikasi pada pengaturan perangkat!")
                                    setPositiveButton("Pengaturan") { dialog, _ ->
                                        dialog.cancel()
                                        val goSetting =
                                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                        val uri =
                                            Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                                        goSetting.data = uri
                                        activity?.startActivity(goSetting)
                                    }
                                    setNegativeButton("Batalkan") { dialog, _ ->
                                        dialog.cancel()
                                    }
                                    show()
                                }
                            }

                            override fun onPermissionRationaleShouldBeShown(
                                permission: PermissionRequest?,
                                token: PermissionToken?
                            ) {
                                token?.continuePermissionRequest()
                            }

                        }).withErrorListener {
                            Log.e("permissionError", it.name)
                        }.check()
                }

                editResultViewModel.message.observe(viewLifecycleOwner){
                    Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                    if (it == "Berhasil mengubah result") {
                        findNavController().popBackStack()
                    }
                }

                binding.ibNavigateBackAddResult.setOnClickListener{
                    findNavController().popBackStack()
                }

                btnSaveAddResult.setOnClickListener {
                    val actualResult = binding.etActuallyAddResult.text.toString()
                    val resultState = status
                    val priorityResult = priority
                    val severityResult = severity

                    val noteResult = binding.etNoteAddResult.text.toString()

                    if (actualResult.isEmpty()) {
                        etActuallyAddResult.error = "Teks tidak boleh kosong !!!"
                    } else if (resultState == "Result state") {
                        Toast.makeText(
                            requireContext(),
                            "Pilih Status",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (priorityResult == "Priority"){
                        Toast.makeText(
                            requireContext(),
                            "Pilih prioritas",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (severityResult == "Severity"){
                        Toast.makeText(
                            requireContext(),
                            "Pilih severity level",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (noteResult.isEmpty()){
                        etNoteAddResult.error = "Note tidak boleh kosong"
                    } else if (imgURI == null){
                        Toast.makeText(
                            requireActivity(),
                            "Mohon pilih gambar terlebih dahulu!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    } else{
                        editResultViewModel.apply {
                            val imageFile = uriToFile(imgURI!!, requireContext())
                            Log.d(ContentValues.TAG, "status $imageFile")
                            updateResults(
                                token,
                                testCaseId,
                                resultId,
                                actualResult,
                                status,
                                priority,
                                severity,
                                imageFile,
                                noteResult
                            )
                        }
                    }
                }
            }
        }
    }
    private fun updateStatusCreateButton(){
        val addresultName = binding.etActuallyAddResult.toString()
        val noteResult = binding.etNoteAddResult.toString()
        binding.btnSaveAddResult.isEnabled = addresultName.isNotEmpty() && noteResult.isNotEmpty()

        if (binding.btnSaveAddResult.isEnabled){
            val colorButtonPrimary = ContextCompat.getColor(requireActivity(), R.color.white)
            val colorTextWhite = ContextCompat.getColor(requireActivity(), R.color.Primary)

            binding.btnSaveAddResult.setBackgroundColor(colorButtonPrimary)
            binding.btnSaveAddResult.setBackgroundColor(colorTextWhite)
        } else {
            val colorButtonFalse = ContextCompat.getColor(requireActivity(), R.color.Primary)
            val colorTextNeutral = ContextCompat.getColor(requireActivity(), R.color.white)

            binding.btnSaveAddResult.setBackgroundColor(colorButtonFalse)
            binding.btnSaveAddResult.setBackgroundColor(colorTextNeutral)
        }
    }
}
package com.qatros.logibug.ui.version.edit_version

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentEditVersionBinding

@AndroidEntryPoint
class EditVersionFragment : DialogFragment() {

    private var _binding: FragmentEditVersionBinding? = null
    private val binding get() = _binding!!

    private val editVersionViewModel: EditVersionViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private val args: EditVersionFragmentArgs by navArgs()
    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditVersionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var projectId = args.projectId
        var versionId = args.versionId
        var isEdit = args.isEdited
        val typeTest = args.typeTest

        if (isEdit){
            preferenceViewModel.getLoginState().observe(viewLifecycleOwner){
                token = it.token
                editVersionViewModel.getVersionById(token, versionId)
                editVersionViewModel.version.observe(viewLifecycleOwner){ get ->
                    binding.etVersionNameEditProjectVersion.setText(get.data.firstOrNull()?.name?.replace("\"", ""))
                }
                editVersionViewModel.message.observe(viewLifecycleOwner){
                    Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                    if (it == "Berhasil mengubah versi") {
                        val action = EditVersionFragmentDirections.actionEditVersionFragmentToListAllVersionFragment(typeTest, projectId)
                        findNavController().navigate(action)
                    }
                }

                binding.ibClosedEditProjectVersion.setOnClickListener {
                    dismiss()
                }

                binding.btnSaveEditProjectVersion.setOnClickListener {
                    val versionName = binding.etVersionNameEditProjectVersion.text.toString()

                    if (versionName.isEmpty()){
                        binding.etVersionNameEditProjectVersion.error = "Tidak boleh kosong"
                    }else{
                        editVersionViewModel.updateVersion(
                            token,
                            versionId,
                            versionName
                        )
                    }
                }

            }
        }

    }

}
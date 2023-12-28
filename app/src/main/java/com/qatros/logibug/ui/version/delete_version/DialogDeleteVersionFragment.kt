package com.qatros.logibug.ui.version.delete_version

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
import com.qatros.logibug.databinding.FragmentDialogDeleteVersionBinding
import com.qatros.logibug.ui.version.list_version.ListVersionViewModel

@AndroidEntryPoint
class DialogDeleteVersionFragment : DialogFragment() {

    private var _binding: FragmentDialogDeleteVersionBinding? = null
    private val binding get() = _binding!!

    private var token = ""

    private val deleteViewModel: ListVersionViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private val args: DialogDeleteVersionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogDeleteVersionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val versionId = args.versionId
        val projectId = args.projectId
        val versionName = args.versionName
        val typeTest = args.typeTest

        binding.tvDescriptionDeleteVersion.text = "Are you sure to delete $versionName"

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
        }

        binding.apply {
            btnDeleteAlertDialogDeleteVersion.setOnClickListener {
                deleteViewModel.deleteVersion(
                    token, versionId
                )
                deleteViewModel.message.observe(viewLifecycleOwner) {
                    Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                    if (it == "Berhasil mengahapus version") {
                        val action =
                            DialogDeleteVersionFragmentDirections.actionDialogDeleteVersionFragmentToListAllVersionFragment(
                                typeTest, projectId
                            )
                        findNavController().navigate(action)
                    } else {
                        dismiss()
                    }
                }
            }

            btnCancelAlertDialogDeleteVersion.setOnClickListener {
                dismiss()
            }

        }
    }

}
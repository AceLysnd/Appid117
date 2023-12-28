package com.qatros.logibug.ui.version.clone_version

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
import com.qatros.logibug.databinding.FragmentDialogDuplicateVersionBinding

@AndroidEntryPoint
class DialogDuplicateVersionFragment : DialogFragment() {

    private var _binding: FragmentDialogDuplicateVersionBinding? = null
    private val binding get() = _binding!!

    private var token = ""
    private val cloneViewModel: CloneVersionViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private val args: DialogDuplicateVersionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogDuplicateVersionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val versionId = args.versionId
        val projectId = args.projectId
        val versionName = args.versionName
        val typeTest = args.typeTest

        binding.tvDescriptionDuplicateVersion.text =
            "Do you want to duplicate version $versionName?"

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
        }

        binding.apply {
            btnDuplicateVersion.setOnClickListener {
                cloneViewModel.cloneVersion(
                    token, versionId
                )
                cloneViewModel.message.observe(viewLifecycleOwner) {
                    Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                    if (it == "Berhasil menduplicate version") {
                        val action =
                            DialogDuplicateVersionFragmentDirections.actionDialogDuplicateVersionFragmentToListAllVersionFragment(
                                typeTest, projectId
                            )
                        findNavController().navigate(action)
                    } else {
                        dismiss()
                    }
                }
            }

            btnCancelDuplicateVersion.setOnClickListener {
                dismiss()
            }

        }

    }

}
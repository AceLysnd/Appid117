package com.qatros.logibug.ui.homepage.delete_project

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
import com.qatros.logibug.R
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.ui.homepage.list_project.ListProjectViewModel
import com.qatros.logibug.databinding.FragmentDialogDeleteProjectBinding

@AndroidEntryPoint
class DialogDeleteProjectFragment : DialogFragment() {
    private var _binding: FragmentDialogDeleteProjectBinding? = null
    private val binding get() = _binding!!

    private var token = ""

    private val deleteViewModel: ListProjectViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private val args: DialogDeleteProjectFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogDeleteProjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val projectId = args.projectId
        val projectName = args.projectName

        binding.tvDescriptionDeleteProject.text = "Are you sure to delete project $projectName"

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
        }

        binding.apply {
            btnDeleteAlertDialogDeleteProjectHomepage.setOnClickListener {
                deleteViewModel.deleteProject(
                    token, projectId
                )
                deleteViewModel.message.observe(viewLifecycleOwner) {
                    Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                    if (it == "Berhasil Menghapus Project") {
                        findNavController().navigate(R.id.action_dialogDeleteProjectFragment_to_homePageFragment)
                    } else {
                        dismiss()
                    }
                }
            }

            btnCancelAlertDialogDeleteProjectHomepage.setOnClickListener {
                dismiss()
            }

        }

    }
}
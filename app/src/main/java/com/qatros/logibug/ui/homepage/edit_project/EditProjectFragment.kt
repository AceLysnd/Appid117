package com.qatros.logibug.ui.homepage.edit_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.R
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentEditProjectBinding

@AndroidEntryPoint
class EditProjectFragment : Fragment() {

    private var _binding: FragmentEditProjectBinding? = null
    private val binding get() = _binding!!

    private val editProjectViewModel: EditProjectViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private val args: EditProjectFragmentArgs by navArgs()
    private var token = ""

    private var typeTest = "Type Test"
    private var platform = "Platform"


    private var listTypeTest = mutableListOf<String>()
    private var listPlatform = mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditProjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var projectId = args.projectId
        var isEdit = args.isEdited

        if (isEdit) {
            preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
                token = it.token
                editProjectViewModel.geProjectById(token, projectId)
                editProjectViewModel.project.observe(viewLifecycleOwner) { get ->
                    binding.apply {
                        etProjectNameEditProject.setText(get.name.replace("\"", ""))
                        listTypeTest = mutableListOf("Type Test", "manual", "automatic")
                        val typeTestDropDown = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            listTypeTest
                        )
                        actTypeTestEditProject.setAdapter(typeTestDropDown)
                        actTypeTestEditProject.setText(listTypeTest[0], false)
                        actTypeTestEditProject.setOnItemClickListener { _, _, position, _ ->
                            typeTest = listTypeTest[position]
                        }


                        listPlatform = mutableListOf("Platform", "mobile", "web")
                        val platformDropdown = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            listPlatform
                        )
                        actPlatformEditProject.setAdapter(platformDropdown)
                        actPlatformEditProject.setText(listPlatform[0], false)
                        actPlatformEditProject.setOnItemClickListener { _, _, position, _ ->
                            platform = listPlatform[position]
                        }
                    }
                }

                binding.apply {
                    editProjectViewModel.message.observe(viewLifecycleOwner) {
                        Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                        if (it == "Berhasil Mengubah Project") {
                            findNavController().navigate(R.id.action_editProjectFragment_to_homePageFragment)
                        }
                    }

                    ibNavigateBackEditCreateProject.setOnClickListener {
                        findNavController().navigate(R.id.action_editProjectFragment_to_homePageFragment)
                    }

                    btnSaveEditProject.setOnClickListener {
                        val projectName = binding.etProjectNameEditProject.text.toString()
                        val typeTestProject = typeTest
                        val platformProject = platform

                        if (projectName.isEmpty()) {
                            etProjectNameEditProject.error = "Harus diisi"
                        } else if (typeTestProject == "Type Test") {
                            Toast.makeText(
                                requireContext(),
                                "Pilih type test untuk project yang di test",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (platformProject == "Platform") {
                            Toast.makeText(
                                requireContext(),
                                "Pilih platform untuk project yang di test",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            editProjectViewModel.updateProject(
                                token,
                                projectId,
                                projectName,
                                typeTest,
                                platform
                            )
                        }
                    }

                }

            }
        }

    }

}
package com.qatros.logibug.ui.homepage.create_project

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.R
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentCreateProjectBinding

@AndroidEntryPoint
class CreateProjectFragment : Fragment() {

    private var _binding: FragmentCreateProjectBinding? = null
    private val binding get() = _binding!!

    private var typeTest = "Type Test"
    private var platform = "Platform"


    private var listTypeTest = mutableListOf<String>()
    private var listPlatform = mutableListOf<String>()

    private val createProjectViewModel: CreateProjectViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateProjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCreateProject.isEnabled = false

        binding.apply {

            var token = ""

            etProjectNameCreateProject.addTextChangedListener(object : TextWatcher {
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

            preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
                token = it.token
            }

            listTypeTest = mutableListOf("Type Test", "manual", "automatic")
            val typeTestDropDown = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                listTypeTest
            )
            actTypeTestCreateProject.setAdapter(typeTestDropDown)
            actTypeTestCreateProject.setText(listTypeTest[0], false)
            actTypeTestCreateProject.setOnItemClickListener { _, _, position, _ ->
                typeTest = listTypeTest[position]
            }


            listPlatform = mutableListOf("Platform", "mobile", "web")
            val platformDropdown = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                listPlatform
            )
            actPlatformCreateProject.setAdapter(platformDropdown)
            actPlatformCreateProject.setText(listPlatform[0], false)
            actPlatformCreateProject.setOnItemClickListener { _, _, position, _ ->
                platform = listPlatform[position]
            }

            createProjectViewModel.message.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                if (it == "Berhasil Menambahkan Project") {
                    view.findNavController()
                        .navigate(R.id.action_createProjectFragment_to_homePageFragment)
                }
            }

            btnCreateProject.setOnClickListener {
                val projectName = binding.etProjectNameCreateProject.text.toString()
                val typeTestProject = typeTest
                val platformProject = platform

                if (typeTestProject == "Type Test") {
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
                    createProjectViewModel.createProject(
                        token,
                        projectName,
                        typeTest,
                        platform
                    )
                }
            }

            ibNavigateBackCreateProject.setOnClickListener {
                findNavController().navigate(R.id.action_createProjectFragment_to_homePageFragment)
            }
        }

    }

    private fun updateStatusCreateButton() {
        val projectName = binding.etProjectNameCreateProject.text.toString()

        binding.btnCreateProject.isEnabled = projectName.isNotEmpty()

        if (binding.btnCreateProject.isEnabled) {
            val colorButtonPrimary = ContextCompat.getColor(requireActivity(), R.color.Primary)
            val colorTextWhite = ContextCompat.getColor(requireActivity(), R.color.white)

            binding.btnCreateProject.setBackgroundColor(colorButtonPrimary)
            binding.btnCreateProject.setTextColor(colorTextWhite)
        } else {
            val colorButtonFalse = ContextCompat.getColor(requireActivity(), R.color.neutral_second)
            val colorTextNeutral = ContextCompat.getColor(requireActivity(), R.color.neutral)

            binding.btnCreateProject.setBackgroundColor(colorButtonFalse)
            binding.btnCreateProject.setTextColor(colorTextNeutral)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("ANJAY", "onDestroy: Called")
        _binding = null
    }
}
package com.qatros.logibug.ui.version.create_version

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.R
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentCreateVersionBinding

@AndroidEntryPoint
class CreateVersionFragment : DialogFragment() {

    private var _binding: FragmentCreateVersionBinding? = null
    private val binding get() = _binding!!

    private val createVersionViewModel: CreateVersionViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private val args: CreateVersionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCreateVersionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveProjectVersion.isEnabled = false

        var token = ""
        val projectId = args.projectId
        val typeTest = args.typeTest
        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
        }

        binding.etVersionNameProjectVersion.addTextChangedListener(object : TextWatcher {
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

        createVersionViewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            if (it == "Berhasil menambahkan version") {
                val action =
                    CreateVersionFragmentDirections.actionCreateTestingVersionFragmentToListAllVersionFragment(
                        typeTest, projectId
                    )
                findNavController().navigate(action)
            }
        }

        binding.btnSaveProjectVersion.setOnClickListener {
            val versionName = binding.etVersionNameProjectVersion.text.toString()
            createVersionViewModel.createVersion(
                token,
                versionName,
                projectId
            )
        }

        binding.ibClosedProjectVersion.setOnClickListener {
            dismiss()
        }
    }

    private fun updateStatusCreateButton() {
        val versionName = binding.etVersionNameProjectVersion.text.toString()

        binding.btnSaveProjectVersion.isEnabled = versionName.isNotEmpty()

        if (binding.btnSaveProjectVersion.isEnabled) {
            val colorButtonPrimary = ContextCompat.getColor(requireActivity(), R.color.Primary)
            val colorTextWhite = ContextCompat.getColor(requireActivity(), R.color.white)

            binding.btnSaveProjectVersion.setBackgroundColor(colorButtonPrimary)
            binding.btnSaveProjectVersion.setTextColor(colorTextWhite)
        } else {
            val colorButtonFalse = ContextCompat.getColor(requireActivity(), R.color.neutral_second)
            val colorTextNeutral = ContextCompat.getColor(requireActivity(), R.color.neutral)

            binding.btnSaveProjectVersion.setBackgroundColor(colorButtonFalse)
            binding.btnSaveProjectVersion.setTextColor(colorTextNeutral)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
package com.qatros.logibug.ui.testcase.delete_test_case

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
import com.qatros.logibug.databinding.FragmentDialogDeleteTestCaseBinding
import com.qatros.logibug.ui.testcase.list_test_case.ListTestCaseViewModel

@AndroidEntryPoint
class DialogDeleteTestCaseFragment : DialogFragment() {

    private var _binding: FragmentDialogDeleteTestCaseBinding? = null
    private val binding get() = _binding!!

    private var token = ""

    private val deleteViewModel: ListTestCaseViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private val args: DialogDeleteTestCaseFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDialogDeleteTestCaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val projectId = args.projectId
        val versionId = args.versionId
        val testCaseId = args.testCaseId

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
        }

        binding.apply {
            btnDeleteAlertDialogDeleteTestCaseList.setOnClickListener {
                deleteViewModel.deleteTestCase(
                    token, testCaseId
                )

                deleteViewModel.message.observe(viewLifecycleOwner) {
                    Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                    if (it == "Berhasil menghapus test case") {
                        val action =
                            DialogDeleteTestCaseFragmentDirections.actionDialogDeleteTestCaseFragmentToListTestCaseFragment(
                                projectId,
                                versionId
                            )
                        findNavController().navigate(action)
                    } else {
                        dismiss()
                    }
                }

            }

            btnCancelAlertDialogDeleteTestCaseList.setOnClickListener {
                dismiss()
            }

        }

    }

}
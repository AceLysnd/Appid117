package com.qatros.logibug.ui.result.detailresult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.R
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentDetailTestCaseResultBinding
import com.qatros.logibug.ui.testcase.edit_test_case.EditTestCaseViewModel

@AndroidEntryPoint
class DetailTestCaseResultFragment : Fragment() {

    private var bind: FragmentDetailTestCaseResultBinding? = null
    private val binding get() = bind!!
    private val detailtestcaseResultViewModel: DetailTestCaseViewModel by viewModels()
    private val detailTestCaseViewModel: EditTestCaseViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private var status = "Result State"
    private var priority = "Priority"
    private var severity = "Severity"

    private val args : DetailTestCaseResultFragmentArgs by navArgs()

    private var token = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        bind = FragmentDetailTestCaseResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testcase = args.testCaseId
        val projectId = args.projectId
        val versionId = args.versionId
        var resultId: Int = 0
        val editresult = args.isEdited

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
            detailtestcaseResultViewModel.getDetailTestCase(it.token, testcase)
            detailTestCaseViewModel.getTestCaseById(token, testcase)

            detailtestcaseResultViewModel.detailTestCaseResult.observe(viewLifecycleOwner){

                binding.apply {
                    tvDescriptionActuallyInDetailTestCaseResult.text = it.data.actual
                    tvStatusFailInDetailTestCaseResult.text = it.data.status
                    tvStatusMediumInDetailTestCaseResult.text = it.data.priority
                    tvStatusCriticalInDetailTestCaseResult.text = it.data.severity
                    tvDescriptionNoteInDetailTestCaseResult.text = it.data.note
                    resultId = it.data.id
                    Glide.with(requireContext())
                        .load(it.data.imgUrl)
                        .centerCrop()
                        .error(R.drawable.ic_upload)
                        .fitCenter()
                        .into(ivDescriptionAttachmentInDetailTestCaseResult)
                }
            }

            detailTestCaseViewModel.testCase.observe(viewLifecycleOwner){
                binding.apply {
                    tvDescriptionTestCaseInDetailTestCaseResult.text = it.data.testCaseName
                    tvStatusDetailTestCaseResult.text = it.data.testCategory
                    tvDescriptionPreconditionInDetailTestCaseResult.text = it.data.preCondition
                    tvDescriptionExpectationInDetailTestCaseResult.text = it.data.expectation
                    listviewDescriptionTeststepsDetailTestCaseResult.text = it.data.testStep
                    tvDescriptionScenarioInDetailTestCaseResult.text = it.data.scenarioName
                }
            }

            binding.apply {
                ibNavigateBackDetailTestCaseResult.setOnClickListener {
                    findNavController().navigate(R.id.action_detailTestCaseResultFragment_to_listAllVersionFragment)
                }

                ibEditDetailTestCaseResult.setOnClickListener {
                    val action =
                        DetailTestCaseResultFragmentDirections.actionDetailTestCaseResultFragmentToEditResultFragment(
                            projectId,
                            versionId,
                            testcase,
                            resultId,
                            editresult
                        )
                    findNavController().navigate(action)
                }
            }
        }
    }
}
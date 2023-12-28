package com.qatros.logibug.ui.version.list_version

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.R
import com.qatros.logibug.core.data.response.version.AllVersionResponse
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentListAllVersionBinding
import com.qatros.logibug.ui.role.RoleViewModel

@AndroidEntryPoint
class ListAllVersionFragment : Fragment(), VersionListListener {

    private var _binding: FragmentListAllVersionBinding? = null
    private val binding get() = _binding!!

    private val listVersionViewModel: ListVersionViewModel by viewModels()
    private val roleViewModel: RoleViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()
    private lateinit var listVersionAdapter: ListVersionAdapter

    private val args: ListAllVersionFragmentArgs by navArgs()

    private var token = ""

    companion object {
        var role = ""
        var typeTest = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListAllVersionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projectId = args.projectId
        typeTest = args.typeTest

        binding.apply {
            ibNavigateBackMyProject.setOnClickListener {
                findNavController().navigate(R.id.action_listAllVersionFragment_to_homePageFragment)
            }

            fabAddVersionTesting.setOnClickListener {
                addVersion(projectId, typeTest)
            }

        }

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
            listVersionViewModel.getAllVersion(token, projectId)
            roleViewModel.getRole(token, projectId)
        }

        roleViewModel.role.observe(viewLifecycleOwner) {
            role = it.data.role
        }

        listVersionViewModel.listVersion.observe(viewLifecycleOwner) {
            setupRecyclerView(it.data)
        }
        loadingState()

        val ibOptionMember = binding.ibOptionMember
        ibOptionMember.setOnClickListener {
            showMenuMember(ibOptionMember)
        }
    }

    private fun setupRecyclerView(list: List<AllVersionResponse>) {
        listVersionAdapter = ListVersionAdapter(list)
        ListVersionAdapter.listenerVersion = this@ListAllVersionFragment
        binding.rvItemTestingVersion.layoutManager = LinearLayoutManager(activity)
        binding.rvItemTestingVersion.adapter = listVersionAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun deleteVersion(projectId: Int, versionId: Int, versionName: String, typeTest: String) {
        val action =
            ListAllVersionFragmentDirections.actionListAllVersionFragmentToDialogDeleteVersionFragment(
                projectId, versionId, versionName, typeTest
            )
        findNavController().navigate(action)
    }

    override fun editVersion(projectId: Int, versionId: Int, typeTest: String) {
        val action =
            ListAllVersionFragmentDirections.actionListAllVersionFragmentToEditVersionFragment(
                projectId,
                versionId,
                true,
                typeTest
            )
        findNavController().navigate(action)
    }

    override fun onCardVersionClicked(projectId: Int, versionId: Int, nameVersion: String) {

        if(typeTest == "manual"){
            val action =
                ListAllVersionFragmentDirections.actionListAllVersionFragmentToListTestCaseFragment(
                    projectId,
                    versionId
                )
            findNavController().navigate(action)
        }else{
            val action = ListAllVersionFragmentDirections.actionListAllVersionFragmentToUploadFileApiTestingFragment(projectId, versionId)
            findNavController().navigate(action)
        }

    }

    override fun addVersion(id: Int, typeTest: String) {
        val action =
            ListAllVersionFragmentDirections.actionListAllVersionFragmentToCreateTestingVersionFragment(
                id, typeTest
            )
        findNavController().navigate(action)
    }

    override fun cloneVersion(projectId: Int, versionId: Int, versionName: String, typeTest: String) {
        val action =
            ListAllVersionFragmentDirections.actionListAllVersionFragmentToDialogDuplicateVersionFragment(
                projectId, versionId, versionName, typeTest
            )
        findNavController().navigate(action)
    }

    private fun loadingState() {
        listVersionViewModel.loading.observe(viewLifecycleOwner) {
            binding.rvItemTestingVersion.isVisible = !it
            binding.progressBar.isVisible = it
        }
    }

    private fun showMenuMember(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.inflate(R.menu.menu_member)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.invite_member -> {
                    if (role != "po") {
                        Toast.makeText(
                            requireContext(),
                            "Just PO who can invite member",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val action =
                            ListAllVersionFragmentDirections.actionListAllVersionFragmentToInviteMemberFragment(
                                args.projectId
                            )
                        findNavController().navigate(action)
                    }
                    true
                }
                R.id.show_member -> {
                    val action =
                        ListAllVersionFragmentDirections.actionListAllVersionFragmentToListMemberFragment(
                            args.projectId
                        )
                    findNavController().navigate(action)
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

}
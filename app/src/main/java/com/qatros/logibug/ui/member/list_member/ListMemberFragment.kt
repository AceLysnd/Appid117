package com.qatros.logibug.ui.member.list_member

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.core.data.response.member.DetailMember
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentListMemberBinding

@AndroidEntryPoint
class ListMemberFragment : Fragment(), MemberListListener {

    private var _binding: FragmentListMemberBinding? = null
    private val binding get() = _binding!!

    private val listMemberViewModel: ListMemberViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()
    private lateinit var listMemberAdapter: ListMemberAdapter

    private val args: ListMemberFragmentArgs by navArgs()

    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListMemberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projectId = args.projectId

        binding.ibNavigateBackMemberList.setOnClickListener {
            findNavController().popBackStack()
        }

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner){
            token = it.token
            listMemberViewModel.getAllMember(it.token, projectId)
        }

        listMemberViewModel.listMember.observe(viewLifecycleOwner){
            setupRecyclerView(it.data)
        }
        loadingState()

    }

    private fun setupRecyclerView(list: List<DetailMember>){
        listMemberAdapter = ListMemberAdapter(list)
        ListMemberAdapter.listenerMember = this@ListMemberFragment
        binding.rvMemberList.layoutManager = LinearLayoutManager(activity)
        binding.rvMemberList.adapter = listMemberAdapter
    }

    private fun loadingState() {
        listMemberViewModel.loading.observe(viewLifecycleOwner) {
            binding.rvMemberList.isVisible = !it
            binding.progressBar.isVisible = it
        }
    }

    override fun editMember(projectId: Int) {
        //
    }

}
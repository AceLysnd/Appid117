package com.qatros.logibug.ui.member

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.DialogDeleteMemberBinding
import com.qatros.logibug.ui.member.list_member.ListMemberViewModel

class DialogDeleteMember : Fragment() {

    private var _binding: DialogDeleteMemberBinding? = null
    private val binding get() = _binding!!
    private val deletedViewModel: ListMemberViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()


    private var token = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DialogDeleteMemberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val projectId = args.projectId
//        val version = args.versionId
//        val memberName = args.memberName

        binding.tvDescriptionDialogDeleteMember.text = "Are you sure you want to delete $"

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
        }
    }
}
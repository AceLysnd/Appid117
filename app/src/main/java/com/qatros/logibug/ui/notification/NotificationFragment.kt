package com.qatros.logibug.ui.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.core.data.response.notification.AddAllNotificationData
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentNotificationBinding

@AndroidEntryPoint
class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    private lateinit var listNotifiationAdapter: ListAdapterNotification
    private val preferenceViewModel: PreferenceViewModel by viewModels()
    private val listNotificationViewModel: AllNotificationViewModel by viewModels()

    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
                token = it.token
                listNotificationViewModel.getAllNotifications(token)
            }

            listNotificationViewModel.notification.observe(viewLifecycleOwner){
                setupRecyclerView(it.data)
            }
        }
    }

    private fun setupRecyclerView(list: List<AddAllNotificationData>) {
        listNotifiationAdapter = ListAdapterNotification(list)
        binding.rvNotification.layoutManager = LinearLayoutManager(activity)
        binding.rvNotification.adapter = listNotifiationAdapter
    }
}
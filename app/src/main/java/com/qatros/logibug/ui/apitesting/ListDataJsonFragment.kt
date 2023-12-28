package com.qatros.logibug.ui.apitesting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.core.data.response.api_testing.DetailDataJson
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentListDataJsonBinding

@AndroidEntryPoint
class ListDataJsonFragment : Fragment() {

    private var _binding: FragmentListDataJsonBinding? = null
    private val binding get() = _binding!!

    private val listDataJsonViewModel: ListDataJsonViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private lateinit var listDataJsonAdapter: ListDataJsonAdapter

    private var token = ""

    private var panjangData = 0

    private val args: ListDataJsonFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListDataJsonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projectId = args.projectId
        val versionId = args.versionId

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner){
            token = it.token
            listDataJsonViewModel.getListDataJson(token, versionId)
        }

        listDataJsonViewModel.listDataJson.observe(viewLifecycleOwner){
            setupRecyclerView(it.data)
            panjangData = it.data.size
        }
        loadingState()

        binding.ibRunDataJson.setOnClickListener {
            for (i in 1 until panjangData){
                listDataJsonViewModel.runDataJson(token, versionId, i)
                Log.d("test12345", "onViewCreated: value I = $i")
            }
        }

    }

    private fun setupRecyclerView(list: List<DetailDataJson>){
        listDataJsonAdapter = ListDataJsonAdapter(list)
        binding.rvDataJson.layoutManager = LinearLayoutManager(activity)
        binding.rvDataJson.adapter = listDataJsonAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loadingState() {
        listDataJsonViewModel.loading.observe(viewLifecycleOwner) {
            binding.rvDataJson.isVisible = !it
            binding.progressBar.isVisible = it
        }
    }

}
package com.qatros.logibug.ui.homepage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.logibug.R
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.core.data.response.project.ProjectResponse
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentHomePageBinding
import com.qatros.logibug.ui.account.GetProfilesViewModel
import com.qatros.logibug.ui.homepage.list_project.ListProjectAdapter
import com.qatros.logibug.ui.homepage.list_project.ListProjectViewModel
import com.qatros.logibug.ui.homepage.list_project.ProjectListListener

@AndroidEntryPoint
class HomePageFragment : Fragment(), ProjectListListener {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!

    private val listProjectViewModel: ListProjectViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()
    private val addName: GetProfilesViewModel by viewModels()
    private lateinit var listProjectAdapter: ListProjectAdapter

    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
            binding.tvUserName.text = it.name
            listProjectViewModel.getAllProject(it.token)
            listProjectViewModel.getAchievement(token, it.idUser)
        }
        addName.profiles.observe(viewLifecycleOwner) {
            binding.tvUserName.text = it.data.email
        }

        listProjectViewModel.achievementData.observe(viewLifecycleOwner) {
            var nextLevel = ""
            with(binding) {
                tvStatusUser.text = it.rank.nameRank
                when (it.rank.nameRank) {
                    "Beginner" -> {
                        tvStatusUser.setBackgroundResource(R.drawable.bg_beginner_tag)
                        nextLevel = "Intermediate level"
                    }

                    "Intermediate" -> {
                        tvStatusUser.setBackgroundResource(R.drawable.bg_intermediate_tag)
                        nextLevel = "Advanced level"
                    }

                    "Advanced" -> {
                        tvStatusUser.setBackgroundResource(R.drawable.bg_advanced_tag)
                        nextLevel = "Mastery level"
                    }

                    "Mastery" -> {
                        tvStatusUser.setBackgroundResource(R.drawable.bg_mastery_tag)
                    }
                }
                tvProgress.text =
                    it.testcaseCount.toString() + " Issues have been tested. complete " +
                            it.rank.rankDifference + " more issues to reach " + nextLevel
                if (it.rank.nameRank == "Mastery") {
                    tvProgress.text = "You're at maximum level!"
                }
            }
        }

        listProjectViewModel.listProject.observe(viewLifecycleOwner) {
            setupRecyclerView(it.data)
        }
        loadingState()

    }

    private fun setupRecyclerView(list: List<ProjectResponse>) {
        with(binding) {
            if (list.isEmpty()) {
                rvItemProject.visibility = View.GONE
                tvDescriptionEmptyProjectHomepage.visibility = View.VISIBLE
                ivEmptyProjectHomepage.visibility = View.VISIBLE
            } else {
                rvItemProject.visibility = View.VISIBLE
                tvDescriptionEmptyProjectHomepage.visibility = View.GONE
                ivEmptyProjectHomepage.visibility = View.GONE

                listProjectAdapter = ListProjectAdapter(list)
                ListProjectAdapter.listenerProject = this@HomePageFragment
                rvItemProject.layoutManager = LinearLayoutManager(activity)
                rvItemProject.adapter = listProjectAdapter
            }
        }
    }

    private fun loadingState() {
        listProjectViewModel.loading.observe(viewLifecycleOwner) {
            with(binding) {
                rvItemProject.isVisible = !it

                tvStatusUser.isVisible = !it
                tvProgress.isVisible = !it

                progressBar.isVisible = it
                progressBar2.isVisible = it
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun deleteProject(id: Int, projectName: String) {
        val action =
            HomePageFragmentDirections.actionHomePageFragmentToDialogDeleteProjectFragment(
                id,
                projectName
            )
        findNavController().navigate(action)
    }

    override fun editProject(id: Int) {
        val action =
            HomePageFragmentDirections.actionHomePageFragmentToEditProjectFragment(id, true)
        findNavController().navigate(action)
    }

    override fun onCardProjectClicked(projectId: Int, typeTest: String) {
        val action = HomePageFragmentDirections.actionHomePageFragmentToTestingVersionFragment(
            typeTest,
            projectId
        )
        Log.d("test nilai", "onCardProjectClicked: $projectId")
        findNavController().navigate(action)
    }
}
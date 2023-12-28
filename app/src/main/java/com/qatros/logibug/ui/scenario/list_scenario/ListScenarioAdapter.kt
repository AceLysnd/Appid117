package com.qatros.logibug.ui.scenario.list_scenario

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qatros.logibug.core.data.response.scenario.ScenarioDetail
import com.qatros.logibug.databinding.ItemScenarioBinding
import com.qatros.logibug.ui.version.list_version.ListAllVersionFragment

class ListScenarioAdapter(private val listScenario: List<ScenarioDetail>) :
    RecyclerView.Adapter<ListScenarioAdapter.ScenarioListViewHolder>() {
    class ScenarioListViewHolder(private val binding: ItemScenarioBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(scenario: ScenarioDetail) {
            with(binding) {
                tvScenarioName.text = scenario.name
                val role = ListAllVersionFragment.role
                if (role == "dev") {
                    ibMenuScenario.visibility = View.GONE
                } else {
                    ibMenuScenario.visibility = View.VISIBLE
                }
                ibMenuScenario.setOnClickListener {
                    listenerScenario?.editScenario(scenario.projectId, 0, scenario.scenarioId)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScenarioListViewHolder {
        val binding =
            ItemScenarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScenarioListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScenarioListViewHolder, position: Int) {
        holder.bind(listScenario[position])
    }

    override fun getItemCount(): Int {
        return listScenario.size
    }

    companion object {
        var listenerScenario: ScenarioListListener? = null
    }


}

interface ScenarioListListener {
    fun deleteScenario(projectId: Int, versionId: Int, scenarioId: Int, scenarioName: String)
    fun editScenario(projectId: Int, versionId: Int, scenarioId: Int)
}
package com.qatros.logibug.ui.apitesting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qatros.logibug.core.data.response.api_testing.DetailDataJson
import com.qatros.logibug.databinding.ItemDataJsonBinding

class ListDataJsonAdapter(private val listDataJson: List<DetailDataJson>) :
    RecyclerView.Adapter<ListDataJsonAdapter.DataJsonListViewHolder>() {

    class DataJsonListViewHolder(private val binding: ItemDataJsonBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(dataJson: DetailDataJson){
                with(binding){
                    tvMethodDataJson.text = dataJson.method
                    tvFolderName.text = dataJson.folderName
                    tvRequestName.text = dataJson.requestName
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataJsonListViewHolder {
        val binding = ItemDataJsonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataJsonListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataJsonListViewHolder, position: Int) {
        holder.bind(listDataJson[position])
    }

    override fun getItemCount(): Int {
        return listDataJson.size
    }
}
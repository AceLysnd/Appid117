package com.qatros.logibug.ui.apitesting

import android.app.PendingIntent.getActivity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.qatros.logibug.R
import com.qatros.logibug.core.data.response.api_testing.DetailDataJson
import com.qatros.logibug.databinding.ItemDataJsonBinding

class ListDataJsonAdapter(private val listDataJson: List<DetailDataJson>) :
    RecyclerView.Adapter<ListDataJsonAdapter.DataJsonListViewHolder>() {

    class DataJsonListViewHolder(private val binding: ItemDataJsonBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(dataJson: DetailDataJson){
                with(binding){
                    tvMethodDataJson.text = dataJson.method
                    when (dataJson.method) {
                        "POST" -> tvMethodDataJson.setBackgroundResource(R.drawable.corner_radius_green)
                        "DELETE" -> tvMethodDataJson.setBackgroundResource(R.drawable.corner_radius_red)
                        "PUT" -> tvMethodDataJson.setBackgroundResource(R.drawable.corner_radius_yellow)
                        "GET" -> tvMethodDataJson.setBackgroundResource(R.drawable.corner_radius_blue)
                        else -> {

                        }
                    }
                    tvFolderName.text = dataJson.folderName
                    tvRequestName.text = dataJson.requestName

                    tvStatusCode.text = dataJson.statusCode
                    when(dataJson.statusCode) {
                        "200" -> tvStatusCode.setTextColor(Color.parseColor("#22B814"))
                        "201" -> tvStatusCode.setTextColor(Color.parseColor("#22B814"))
                        "500" -> tvStatusCode.setTextColor(Color.parseColor("#FF0000"))
                    }

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
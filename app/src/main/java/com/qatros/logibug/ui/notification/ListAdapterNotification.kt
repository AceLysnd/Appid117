package com.qatros.logibug.ui.notification

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qatros.logibug.core.data.response.notification.AddAllNotificationData
import com.qatros.logibug.databinding.CvItemNotificationBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ListAdapterNotification(private val listNotification: List<AddAllNotificationData>):
RecyclerView.Adapter<ListAdapterNotification.ListNotificationViewHolder>(){

    class ListNotificationViewHolder(private val binding: CvItemNotificationBinding):
    RecyclerView.ViewHolder(binding.root){

        fun  bind(notification: AddAllNotificationData){
            with(binding){
                tvDescriptionNotification.text = notification.params.result.message
                if (notification.read_at == "null"){
                    cvNotif.setCardBackgroundColor(Color.parseColor("#EBEBFE"))
                } else {
                    cvNotif.setCardBackgroundColor(Color.WHITE)
                }

                val dateTime = notification.created_at
                val dateTimeParts = dateTime.split("T")
                val date =  dateTimeParts[0]
                val time = dateTimeParts[1].substring(0, 5)

                val inputFormat = SimpleDateFormat("yyyy-MM-dd")
                val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("com", "ID"))
                val formattedDate = inputFormat.parse(date)?.let { outputFormat.format(it) }

                tvTime.text= time
                tvCurrentDate.text = formattedDate
                tvNameProjectNotification.text = notification.params.result.project_name
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListNotificationViewHolder {
        val bindiing = CvItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListNotificationViewHolder(bindiing)
    }

    override fun onBindViewHolder(
        holder: ListAdapterNotification.ListNotificationViewHolder,
        position: Int
    ) {
        holder.bind(listNotification[position])
    }

    override fun getItemCount(): Int {
        return listNotification.size
    }


}
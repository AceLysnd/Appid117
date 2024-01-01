package com.qatros.logibug.ui.notification

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.qatros.logibug.R
import com.qatros.logibug.core.data.response.notification.AddAllNotificationData
import com.qatros.logibug.databinding.CvItemNotificationBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Random

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

                val random = Random()
                val randomNumber = random.nextInt(4) + 1
                when (randomNumber) {
                    1 -> tvProfileUserInNotification.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#554AF0"))
                    2 -> tvProfileUserInNotification.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#1A8A0F"))
                    3 -> tvProfileUserInNotification.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F99B13"))
                    4 -> tvProfileUserInNotification.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#DD00D2"))
                }

                val firstLetters = notification.params.result.message.split(" ")
                    .filter { it.isNotBlank() }  // Remove empty words
                    .take(2)  // Take the first two words
                    .map { it.take(1) }  // Take the first letter of each word

                tvProfileUserInNotification.text = firstLetters[0] + firstLetters[1]
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
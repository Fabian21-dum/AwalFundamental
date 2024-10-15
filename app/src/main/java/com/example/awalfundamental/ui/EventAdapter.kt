package com.example.awalfundamental.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.awalfundamental.R
import com.example.awalfundamental.data.response.ListEventsItem
import com.example.awalfundamental.databinding.ItemEventBinding

class EventAdapter(private var listEvents: List<ListEventsItem> = listOf()) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    // ViewHolder untuk RecyclerView
    inner class EventViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.tvItemName.text = event.name
            binding.tvItemDescription.text = event.summary
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.imgItemPhoto)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): EventViewHolder {
    val binding =ItemEventBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
    return EventViewHolder(binding)
}

    override fun onBindViewHolder(viewHolder: EventViewHolder, position: Int) {
    viewHolder.bind (listEvents[position])
}

    override fun getItemCount(): Int = listEvents.size

    fun updateData(newEvents: List<ListEventsItem>) {
        listEvents = newEvents
        notifyDataSetChanged()
    }
}

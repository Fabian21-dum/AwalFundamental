package com.example.awalfundamental.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.awalfundamental.data.response.Event
import com.example.awalfundamental.databinding.ItemEventBinding

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.EventViewHolder>() {

    private var listData = ArrayList<Event>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(newListData: List<Event>) {
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner class EventViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.tvItemName.text = event.name
            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(event.id)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(eventId: String)
    }
}


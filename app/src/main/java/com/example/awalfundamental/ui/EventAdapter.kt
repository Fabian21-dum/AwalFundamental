package com.example.awalfundamental.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.awalfundamental.R
import com.example.awalfundamental.data.response.ListEventsItem
import com.example.awalfundamental.databinding.FragmentHomeBinding
import com.example.awalfundamental.databinding.ItemEventBinding

class EventAdapter(private var listEvents: List<ListEventsItem> = listOf()) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    inner class EventViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.tvItemName.text = event.name
            binding.tvItemDescription.text = event.summary
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.imgItemPhoto)
            binding.cardView.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("EVENT_ID", event.id)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): EventViewHolder {
        val binding =
            ItemEventBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: EventViewHolder, position: Int) {

        viewHolder.bind(listEvents[position])
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun getItemCount(): Int = listEvents.size

    fun updateData(newEvents: List<ListEventsItem>) {
        listEvents = newEvents
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(eventId: String)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }

    }
}

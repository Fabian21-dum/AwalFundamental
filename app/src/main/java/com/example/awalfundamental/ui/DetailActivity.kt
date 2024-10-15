package com.example.awalfundamental.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.awalfundamental.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        val eventId = intent.getIntExtra("EVENT_ID", 0)
        if (eventId != 0){
            detailViewModel.fetchDetailEvent(eventId.toString())
            observeEventDetail()
        }else{
            Toast.makeText(this, "Invalid Event ID", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnRegis.setOnClickListener{
            val regUrl = detailViewModel.eventDetail.value?.event?.link
            if(!regUrl.isNullOrEmpty()){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(regUrl))
                startActivity(intent)
            }else{
                Toast.makeText(this, "Registration Link not Available", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun observeEventDetail() {
        detailViewModel.eventDetail.observe(this, Observer { detailResponse ->
            detailResponse?.let{
                val event = it.event

                binding.tvTitle.text = event.name
                binding.tvSummary.text = event.summary
                binding.tvDescription.text = HtmlCompat.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
                Glide.with(this)
                    .load(event.imageLogo)
                    .into(binding.ivPicture)

                binding.tvCity.text = event.cityName
                binding.tvOwner.text = event.ownerName
                binding.tvCat.text = event.category
                binding.tvQuota.text = "Quota: ${event.quota}"
                binding.tvRegistrans.text = "Registrans: ${event.registrants}"
                binding.tvTime.text =  "${event.beginTime} - ${event.endTime}"

            }?:run{
                Log.e("DetailActivity", "Event detail is null")
                Toast.makeText(this, "Failed to load event details", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
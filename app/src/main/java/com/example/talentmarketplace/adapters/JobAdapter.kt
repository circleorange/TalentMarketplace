package com.example.talentmarketplace.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.talentmarketplace.databinding.CardJobBinding
import com.example.talentmarketplace.models.MarketplaceModel

interface JobListener {
    fun onJobClick(job: MarketplaceModel)
}

class JobAdapter constructor(private var jobs: List<MarketplaceModel>):
    RecyclerView.Adapter<JobAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardJobBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding) }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val job = jobs[holder.adapterPosition]
        holder.bind(job) }

    override fun getItemCount(): Int = jobs.size

    class MainHolder(private val binding: CardJobBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(job: MarketplaceModel) {
            binding.jobTitle.text = job.title
            binding.description.text = job.description } }
}
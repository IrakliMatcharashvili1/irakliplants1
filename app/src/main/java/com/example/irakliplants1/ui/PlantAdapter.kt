package com.example.irakliplants1.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.irakliplants1.data.Plant
import com.example.irakliplants1.databinding.ItemPlantBinding

class PlantAdapter(private val onItemClick: (Plant) -> Unit) :
    ListAdapter<Plant, PlantAdapter.PlantViewHolder>(PlantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val binding = ItemPlantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PlantViewHolder(private val binding: ItemPlantBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(plant: Plant) {
            binding.tvPlantName.text = plant.name
            binding.tvLatinName.text = plant.latinName ?: ""

            if (!plant.photoUri.isNullOrEmpty()) {
                Glide.with(binding.ivPlantPhoto.context)
                    .load(plant.photoUri)
                    .centerCrop()
                    .into(binding.ivPlantPhoto)
            } else {
                binding.ivPlantPhoto.setImageResource(com.example.irakliplants1.R.drawable.ic_plant)
            }

            binding.root.setOnClickListener { onItemClick(plant) }
        }
    }

    class PlantDiffCallback : DiffUtil.ItemCallback<Plant>() {
        override fun areItemsTheSame(oldItem: Plant, newItem: Plant) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Plant, newItem: Plant) = oldItem == newItem
    }
}

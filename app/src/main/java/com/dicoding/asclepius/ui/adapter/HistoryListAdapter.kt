package com.dicoding.asclepius.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.data.local.entity.HistoryEntity
import com.dicoding.asclepius.databinding.HistoryListBinding

class HistoryListAdapter(private val application: Context, private val onItemClicked: (HistoryEntity) -> Unit) :
    ListAdapter<HistoryEntity, HistoryListAdapter.HistoryViewHolder>
        (DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = getItem(position)
        holder.bind(history,onItemClicked)
    }

   inner class HistoryViewHolder(private var binding: HistoryListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: HistoryEntity, onItemClicked: (HistoryEntity) -> Unit) {
            binding.result.text = history.result
            Glide.with(application)
                .load(history.image)
                .into(binding.image)
            binding.root.setOnClickListener{
                onItemClicked(history)
            }

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryEntity>() {
            override fun areItemsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}
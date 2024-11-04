package com.dicoding.asclepius.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.data.remote.response.Information
import com.dicoding.asclepius.databinding.InformationListBinding

class InformationListAdapter(private val application: Context, private val onItemClicked: (Information) -> Unit)  :
    ListAdapter<Information, InformationListAdapter.NewsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = InformationListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val detailInformation = getItem(position)
        holder.bind(detailInformation,onItemClicked)
    }

    inner class NewsViewHolder(private var binding : InformationListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Information, onItemClicked: (Information) -> Unit) {
            binding.tvTitle.text = article.title
            binding.tvDesc.text = article.description
            Glide.with(application)
                .load(article.image)
                .into(binding.imgView)
            binding.root.setOnClickListener{
                onItemClicked(article)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Information>() {
            override fun areItemsTheSame(oldItem: Information, newItem: Information): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Information, newItem: Information): Boolean {
                return oldItem == newItem
            }
        }
    }
}
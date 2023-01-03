package com.kudos.savemyquotesapp.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kudos.savemyquotesapp.databinding.RowItemQuotesBinding
import com.kudos.savemyquotesapp.db.entity.RandomQuoteDB

class RandomQuotesAdapter(private val onItemClick: (RandomQuoteDB) -> Unit) :
    ListAdapter<RandomQuoteDB, RandomQuotesAdapter.ViewHolder>(callback) {

    companion object {
        val callback = object : DiffUtil.ItemCallback<RandomQuoteDB>() {
            override fun areItemsTheSame(
                oldItem: RandomQuoteDB,
                newItem: RandomQuoteDB
            ) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: RandomQuoteDB,
                newItem: RandomQuoteDB
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(val rowItemQuotesBinding: RowItemQuotesBinding) :
        RecyclerView.ViewHolder(rowItemQuotesBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowItemQuotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            rowItemQuotesBinding.apply {
                val item = getItem(position)
                quote = item
                itemView.setOnClickListener {
                    onItemClick(item)
                }

            }
        }
    }

}
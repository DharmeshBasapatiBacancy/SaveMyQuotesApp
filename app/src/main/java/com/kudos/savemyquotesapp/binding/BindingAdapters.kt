package com.kudos.savemyquotesapp.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kudos.savemyquotesapp.db.entity.RandomQuoteDB
import com.kudos.savemyquotesapp.views.adapters.RandomQuotesAdapter

@BindingAdapter("quotesList")
fun bindQuotesRecyclerView(recyclerView: RecyclerView, data: List<RandomQuoteDB>?) {
    val adapter = recyclerView.adapter as RandomQuotesAdapter
    adapter.submitList(data)
    recyclerView.smoothScrollToPosition(0)
}
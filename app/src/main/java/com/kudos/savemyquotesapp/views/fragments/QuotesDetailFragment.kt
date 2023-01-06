package com.kudos.savemyquotesapp.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.kudos.savemyquotesapp.databinding.FragmentQuotesDetailBinding
import com.kudos.savemyquotesapp.utils.CommonUtils.shareText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuotesDetailFragment : Fragment() {

    private lateinit var binding: FragmentQuotesDetailBinding

    private val args: QuotesDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuotesDetailBinding.inflate(layoutInflater)
        with(binding) {
            quote = args.quote
            shareQuoteButton.setOnClickListener {
                requireContext().shareText(args.quote.quoteContent)
            }
        }

        return binding.root
    }
}
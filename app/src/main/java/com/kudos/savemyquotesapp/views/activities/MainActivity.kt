package com.kudos.savemyquotesapp.views.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.kudos.savemyquotesapp.databinding.ActivityMainBinding
import com.kudos.savemyquotesapp.viewmodels.QuotesViewModel
import com.kudos.savemyquotesapp.views.adapters.RandomQuotesAdapter
import com.kudos.savemyquotesapp.worker.RandomQuoteFetchingWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var randomQuotesAdapter: RandomQuotesAdapter
    private lateinit var binding: ActivityMainBinding

    private val quotesViewModel: QuotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupQuotesList()
        startRandomQuoteFetchingWorker()
        observeQuotes()
    }

    private fun startRandomQuoteFetchingWorker() {
        val workManager = WorkManager.getInstance(this)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val myTestWorkerRequest =
            PeriodicWorkRequestBuilder<RandomQuoteFetchingWorker>(15, TimeUnit.MINUTES)
                .setInputData(workDataOf("myKey" to "myValue"))
                .setConstraints(constraints)
                .build()

        workManager.enqueue(myTestWorkerRequest)

        workManager.getWorkInfoByIdLiveData(myTestWorkerRequest.id).observe(this) { workInfo ->
            Log.d(TAG, "IN ACTIVITY: ${workInfo.state}")
            if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                val workerResult = workInfo.outputData.getString("workerKey")
                Log.d(TAG, "IN ACTIVITY: $workerResult")
            }
        }
    }

    private fun observeQuotes() {
        lifecycleScope.launch {
            quotesViewModel.randomQuotesList.observe(this@MainActivity) {
                randomQuotesAdapter.submitList(it)
                binding.quotesRecyclerView.smoothScrollToPosition(0)
            }
        }
    }

    private fun setupQuotesList() {
        randomQuotesAdapter = RandomQuotesAdapter {}
        binding.quotesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = randomQuotesAdapter
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
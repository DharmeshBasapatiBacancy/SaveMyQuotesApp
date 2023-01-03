package com.kudos.savemyquotesapp.views.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.kudos.savemyquotesapp.databinding.ActivityMainBinding
import com.kudos.savemyquotesapp.viewmodels.QuotesViewModel
import com.kudos.savemyquotesapp.views.adapters.RandomQuotesAdapter
import com.kudos.savemyquotesapp.worker.RandomQuoteFetchingWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quotesViewModel: QuotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.quotesViewModel = quotesViewModel
        binding.quotesRecyclerView.adapter = RandomQuotesAdapter {}
        startRandomQuoteFetchingWorker()
    }

    private fun startRandomQuoteFetchingWorker() {
        val workManager = WorkManager.getInstance(this)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val myTestWorkerRequest =
            PeriodicWorkRequestBuilder<RandomQuoteFetchingWorker>(
                PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MINUTES
            )
                //to send some data in worker class
                .setInputData(workDataOf("myKey" to "myValue"))
                //to set some constraints in worker class
                .setConstraints(constraints)
                //in case of retry
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()

        workManager.enqueueUniquePeriodicWork(
            "RandomQuoteFetcher",
            ExistingPeriodicWorkPolicy.REPLACE,
            myTestWorkerRequest
        )

        workManager.getWorkInfoByIdLiveData(myTestWorkerRequest.id).observe(this) { workInfo ->
            Log.d(TAG, "IN ACTIVITY: ${workInfo.state}")
            if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                //to get returned result or data from worker class
                val workerResult = workInfo.outputData.getString("workerKey")
                Log.d(TAG, "IN ACTIVITY: $workerResult")
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
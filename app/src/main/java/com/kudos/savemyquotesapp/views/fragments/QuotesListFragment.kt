package com.kudos.savemyquotesapp.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.work.*
import com.kudos.savemyquotesapp.databinding.FragmentQuotesListBinding
import com.kudos.savemyquotesapp.viewmodels.QuotesViewModel
import com.kudos.savemyquotesapp.views.adapters.RandomQuotesAdapter
import com.kudos.savemyquotesapp.worker.RandomQuoteFetchingWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class QuotesListFragment : Fragment() {

    private lateinit var binding: FragmentQuotesListBinding

    private val quotesViewModel: QuotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuotesListBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.quotesViewModel = quotesViewModel
        binding.quotesRecyclerView.adapter = RandomQuotesAdapter {
            findNavController().navigate(
                QuotesListFragmentDirections.actionQuotesListFragmentToQuotesDetailFragment(
                    it
                )
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startRandomQuoteFetchingWorker()
    }

    private fun startRandomQuoteFetchingWorker() {
        val workManager = WorkManager.getInstance(requireContext())

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

        workManager.getWorkInfoByIdLiveData(myTestWorkerRequest.id)
            .observe(requireActivity()) { workInfo ->
                workInfo?.let {
                    Log.d(TAG, "IN ACTIVITY: ${workInfo.state}")
                    if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                        //to get returned result or data from worker class
                        val workerResult = workInfo.outputData.getString("workerKey")
                        Log.d(TAG, "IN ACTIVITY: $workerResult")
                    }
                }
            }
    }

    companion object {
        private const val TAG = "QuotesListFragment"
    }
}
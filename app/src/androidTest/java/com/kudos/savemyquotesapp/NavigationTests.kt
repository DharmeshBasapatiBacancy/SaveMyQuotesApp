package com.kudos.savemyquotesapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.kudos.savemyquotesapp.views.fragments.QuotesListFragment
import dagger.hilt.android.AndroidEntryPoint
import org.junit.Assert.assertEquals
import org.junit.Test

class NavigationTests {

    @Test
    fun navigate_to_words_nav_component() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val letterListScenario = launchFragmentInContainer<QuotesListFragment>(
            themeResId =
            R.style.Theme_SaveMyQuotesApp
        )

        letterListScenario.onFragment { fragment ->

            navController.setGraph(R.navigation.quotes_nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.quotesRecyclerView))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click())
            )

        assertEquals(navController.currentDestination?.id, R.id.quotesDetailFragment)
    }

}
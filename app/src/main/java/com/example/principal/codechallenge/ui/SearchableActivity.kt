package com.example.principal.codechallenge.ui

import android.app.SearchManager
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.principal.codechallenge.dependencyInjection.Injectable
import com.example.principal.codechallenge.dependencyInjection.ViewModelFactory
import com.example.principal.codechallenge.viewmodels.UserViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject


class SearchableActivity: AppCompatActivity(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: UserViewModel

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)


        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)

            showResults(query)
        }
    }

    private fun showResults(username: String) {
        // Query your data set and show results

        val intent = Intent(this, ChallengesActivity::class.java)

        viewModel.getUser(username)

        intent.putExtra("username", username)
        startActivity(intent)
        finish()
    }
}
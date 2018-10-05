package com.example.principal.codechallenge.ui

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import com.example.principal.codechallenge.R
import com.example.principal.codechallenge.adapters.UserAdapter
import com.example.principal.codechallenge.callbacks.UserCallback
import com.example.principal.codechallenge.dependencyInjection.Injectable
import com.example.principal.codechallenge.dependencyInjection.ViewModelFactory
import com.example.principal.codechallenge.viewmodels.UserViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), Injectable, UserCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)

        recyclerview.layoutManager = LinearLayoutManager(applicationContext)
        recyclerview.adapter = UserAdapter(applicationContext, this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)

        viewModel.getLastSearches().observe(this,  Observer {

            (recyclerview.adapter as UserAdapter).setDataset(it)
        })
    }

    override fun onResume() {
        super.onResume()

        viewModel.getLastSearches()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(componentName))

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){

            R.id.action_sort -> {
                (recyclerview.adapter as UserAdapter).sortbyRank()
                return true
            }


            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onUserItemClick(username: String) {

        val intent = Intent(this, ChallengesActivity::class.java)

        intent.putExtra("username", username)
        startActivity(intent)
    }
}

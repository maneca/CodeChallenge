package com.example.principal.codechallenge.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.principal.codechallenge.dependencyInjection.Injectable
import com.example.principal.codechallenge.dependencyInjection.ViewModelFactory
import com.example.principal.codechallenge.R
import com.example.principal.codechallenge.adapters.UserAdapter
import com.example.principal.codechallenge.viewmodels.UserViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*

import javax.inject.Inject

class MainActivity : AppCompatActivity(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)

        recyclerview.layoutManager = LinearLayoutManager(applicationContext)
        recyclerview.adapter = UserAdapter(applicationContext)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)

        viewModel.getUser()
        viewModel.getLastSearches().observe(this,  Observer { it ->

            Log.d("JMF-a", it!!.size.toString())

            (recyclerview.adapter as UserAdapter).setDataset(it)
        })

    }
}

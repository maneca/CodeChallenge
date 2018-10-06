package com.example.principal.codechallenge.ui

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.principal.codechallenge.R
import com.example.principal.codechallenge.adapters.CompleteChallengePagedAdapter
import com.example.principal.codechallenge.callbacks.ChallengeCallback
import com.example.principal.codechallenge.dependencyInjection.Injectable
import com.example.principal.codechallenge.dependencyInjection.ViewModelFactory
import com.example.principal.codechallenge.viewmodels.ChallengesViewModel
import kotlinx.android.synthetic.main.recyclerview_layout.*
import javax.inject.Inject


class CompletedChallengesFragment: Fragment(), Injectable, ChallengeCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ChallengesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {



        return inflater.inflate(R.layout.recyclerview_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val username = arguments!!.getString("username")

        challenge_recyclerview.layoutManager = LinearLayoutManager(context)
        challenge_recyclerview.adapter = CompleteChallengePagedAdapter(context, this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChallengesViewModel::class.java)
        viewModel.initCompletedChallenge(username!!)
        viewModel.getCompletedChallenges().observe(this, Observer {

            (challenge_recyclerview.adapter as CompleteChallengePagedAdapter).submitList(it)
        })

        viewModel.getNetworkState().observe(this, Observer {

            val aDialog = AlertDialog.Builder(context).setMessage(it!!.errorMessage).setTitle("Error")
                    .setNeutralButton("Close", null).create()
            aDialog.show()
        })

    }

    override fun onChallengeClick(id: String) {

        val intent = Intent(context, ChallengeDetailsActivity::class.java)

        intent.putExtra("id", id)
        startActivity(intent)
    }
}
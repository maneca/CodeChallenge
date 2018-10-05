package com.example.principal.codechallenge.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.principal.codechallenge.R
import com.example.principal.codechallenge.adapters.ChallengePagedAdapter
import com.example.principal.codechallenge.dependencyInjection.Injectable
import com.example.principal.codechallenge.dependencyInjection.ViewModelFactory
import com.example.principal.codechallenge.viewmodels.ChallengesViewModel
import kotlinx.android.synthetic.main.recyclerview_layout.*
import javax.inject.Inject

class CompletedChallengesFragment: Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ChallengesViewModel
    private lateinit var username: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        username = arguments!!.getString("username")

        return inflater.inflate(R.layout.recyclerview_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        challenge_recyclerview.layoutManager = LinearLayoutManager(context)
        challenge_recyclerview.adapter = ChallengePagedAdapter(context)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChallengesViewModel::class.java)
        viewModel.init(username)
        viewModel.getCompletedChallenges().observe(this, Observer {

            (challenge_recyclerview.adapter as ChallengePagedAdapter).submitList(it)
        })

    }
}
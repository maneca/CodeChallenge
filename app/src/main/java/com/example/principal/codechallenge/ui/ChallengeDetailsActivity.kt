package com.example.principal.codechallenge.ui

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.principal.codechallenge.R
import com.example.principal.codechallenge.dependencyInjection.Injectable
import com.example.principal.codechallenge.dependencyInjection.ViewModelFactory
import com.example.principal.codechallenge.viewmodels.ChallengeDetailsViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.challenge_details_layout.*
import javax.inject.Inject

class ChallengeDetailsActivity: AppCompatActivity(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ChallengeDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.challenge_details_layout)
        AndroidInjection.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChallengeDetailsViewModel::class.java)
        viewModel.getChallengeDetailsTest(intent.getStringExtra("id"))
        viewModel.getDetails().observe(this, Observer{

            details_name.text = it!!.name
            category.text = it.category
            url.text = it.url
            created_by.text = if(it.createdBy == null) "" else  it.createdBy!!.get("username").asString
            description.text = it.description
            total_attempts.text = it.totalAttempts.toString()
            total_completed.text = it.totalCompleted.toString()
            total_stars.text = it.totalStars.toString()
            vote_score.text = it.voteScore.toString()
        })

        viewModel.getNetworkState().observe(this, Observer{
            if(it!!.state == "ERROR" || it.state == "FAILED") {

                val aDialog = AlertDialog.Builder(this).setMessage(it.errorMessage).setTitle(it.state)
                        .setNeutralButton("Close") { _, _ ->
                            finish()
                        }
                aDialog.create()
                aDialog.show()
            }
        })


    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.challenge_details_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){

            R.id.action_back -> {

                finish()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

}
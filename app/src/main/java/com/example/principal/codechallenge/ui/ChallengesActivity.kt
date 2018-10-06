package com.example.principal.codechallenge.ui

import android.app.AlertDialog
import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.principal.codechallenge.R
import com.example.principal.codechallenge.dependencyInjection.Injectable
import com.example.principal.codechallenge.dependencyInjection.ViewModelFactory
import com.example.principal.codechallenge.viewmodels.UserViewModel
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.member_layout.*
import javax.inject.Inject


class ChallengesActivity: AppCompatActivity(), Injectable, BottomNavigationView.OnNavigationItemSelectedListener, HasSupportFragmentInjector {

    private val TAG_FRAGMENT_COMPLETED = "tag_frag_completed"
    private val TAG_FRAGMENT_AUTHORED = "tag_frag_authored"
    private lateinit var bundle: Bundle

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: UserViewModel

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.member_layout)
        AndroidInjection.inject(this)

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val username = intent.getStringExtra(SearchManager.QUERY)

            viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
            viewModel.getUser(username)

            viewModel.getNetworkState().observe(this, Observer {

                if(it!!.state == "ERROR" || it.state == "FAILED"){

                    val aDialog = AlertDialog.Builder(this@ChallengesActivity).setMessage(it!!.errorMessage).setTitle(it.state)
                            .setNeutralButton("Close"){ _, _ ->
                                finish()
                            }
                    aDialog.create()
                    aDialog.show()
                }else{
                    val completedChallengesFragment = CompletedChallengesFragment()
                    bundle = Bundle()
                    bundle.putString("username", username)

                    completedChallengesFragment.arguments = bundle

                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_fragmentholder, completedChallengesFragment, TAG_FRAGMENT_COMPLETED)
                            .commit()

                    bottomNavigationView.setOnNavigationItemSelectedListener(this)
                }
            })

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.bottombaritem_completed -> {

                val completedChallengesFragment = CompletedChallengesFragment()
                completedChallengesFragment.arguments = bundle

                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_fragmentholder, completedChallengesFragment, TAG_FRAGMENT_COMPLETED)
                        .commit()
                return true
            }

            R.id.bottombaritem_authored -> {

                val authoredChallengesFragment = AuthoredChallengesFragment()
                authoredChallengesFragment.arguments = bundle

                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_fragmentholder, authoredChallengesFragment, TAG_FRAGMENT_AUTHORED)
                        .commit()
                return true
            }
        }

        return false
    }

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }
}
package com.example.principal.codechallenge.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.principal.codechallenge.R
import com.example.principal.codechallenge.dependencyInjection.Injectable
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.member_layout)
        AndroidInjection.inject(this)

        val completedChallengesFragment = CompletedChallengesFragment()
        bundle = Bundle()
        bundle.putString("username", intent.getStringExtra("username"))
        completedChallengesFragment.arguments = bundle

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_fragmentholder, completedChallengesFragment, TAG_FRAGMENT_COMPLETED)
                .commit()

        bottomNavigationView.setOnNavigationItemSelectedListener(this)
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
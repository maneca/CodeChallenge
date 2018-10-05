package com.example.principal.codechallenge.adapters

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.principal.codechallenge.AuthoredChallenge
import com.example.principal.codechallenge.R
import kotlinx.android.synthetic.main.challenge_item_list.view.*

class AuthoredChallengePagedAdapter(private val context: Context?): PagedListAdapter<AuthoredChallenge, AuthoredChallengePagedAdapter.ChallengeViewHolder>(AuthoredChallengeDiffCallback()) {
    override fun onBindViewHolder(challengeHolder: ChallengeViewHolder, position: Int) {
        val challenge = getItem(position)

        if (challenge == null) {
            challengeHolder.clear()
        } else {
            challengeHolder.bind(challenge)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {

        return ChallengeViewHolder(LayoutInflater.from(context).inflate(R.layout.challenge_item_list,
                parent, false))
    }


    class ChallengeViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        private val name= view.challenge_name
        private val languages = view.languages

        fun bind(challenge: AuthoredChallenge) {
            name.text = challenge.name
            languages.text = challenge.languages.toString()
        }

        fun clear() {
            name.text = null
            languages.text = null
        }

    }
}
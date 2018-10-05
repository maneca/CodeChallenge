package com.example.principal.codechallenge.adapters

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.principal.codechallenge.Challenge
import com.example.principal.codechallenge.R
import kotlinx.android.synthetic.main.challenge_item_list.view.*

class ChallengePagedAdapter(private val context: Context?): PagedListAdapter<Challenge, ChallengePagedAdapter.ChallengeViewHolder>(ChallengeDiffCallback()) {

    override fun onBindViewHolder(holderPerson: ChallengeViewHolder, position: Int) {
        val person = getItem(position)

        if (person == null) {
            holderPerson.clear()
        } else {
            holderPerson.bind(person)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {

        return ChallengeViewHolder(LayoutInflater.from(context).inflate(R.layout.challenge_item_list,
                parent, false))
    }


    class ChallengeViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        private val name= view.challenge_name
        private val languages = view.languages

        fun bind(challenge: Challenge) {
            name.text = challenge.name
            languages.text = challenge.completedLanguages.toString()
        }

        fun clear() {
            name.text = null
        }

    }

}
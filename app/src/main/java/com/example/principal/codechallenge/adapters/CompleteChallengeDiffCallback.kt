package com.example.principal.codechallenge.adapters

import android.support.v7.util.DiffUtil
import com.example.principal.codechallenge.Challenge

class CompleteChallengeDiffCallback: DiffUtil.ItemCallback<Challenge>() {
    override fun areItemsTheSame(p0: Challenge, p1: Challenge): Boolean {
        return p0.id == p1.id
    }

    override fun areContentsTheSame(p0: Challenge, p1: Challenge): Boolean {
        return p0 == p1
    }
}
package com.example.principal.codechallenge.adapters

import android.support.v7.util.DiffUtil
import com.example.principal.codechallenge.AuthoredChallenge

class AuthoredChallengeDiffCallback: DiffUtil.ItemCallback<AuthoredChallenge>() {
    override fun areItemsTheSame(p0: AuthoredChallenge, p1: AuthoredChallenge): Boolean {
        return p0.id == p1.id
    }

    override fun areContentsTheSame(p0: AuthoredChallenge, p1: AuthoredChallenge): Boolean {
        return p0 == p1
    }
}
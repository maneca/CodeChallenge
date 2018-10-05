package com.example.principal.codechallenge

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.google.gson.JsonObject


data class User(var username: String, var name: String, var honor: Int, var clan: String, var leaderboardPosition: Int, var skills: List<String>, var ranks: JsonObject, var codeChallenages: JsonObject)

data class Challenges(var id: String, var name: String, var slug: String, var completedLanguages: List<String>, var completedAt: String)

data class CompletedChallanges(var totalPages: Int, var totalItems: Int, var data: List<Challenges>)

// ROOM ENTITIES

@Entity(tableName="user")
data class UserDatabase(@NonNull @PrimaryKey var username: String, var name: String?, var rank: Int, var language: String, var points: Int, var time: Long)

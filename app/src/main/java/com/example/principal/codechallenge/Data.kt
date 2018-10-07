package com.example.principal.codechallenge

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.google.gson.JsonObject
import org.json.JSONObject


data class User(var username: String, var name: String, var honor: Int, var clan: String, var leaderboardPosition: Int, var skills: List<String>, var ranks: JsonObject, var codeChallenages: JsonObject)

// COMPLETED CHALLENGE
data class Challenge(var id: String, var name: String, var slug: String, var completedLanguages: List<String>, var completedAt: String)

data class CompletedChallanges(var totalPages: Int, var totalItems: Int, var data: List<Challenge>)

// AUTHORED CHALLENGE
@Entity(tableName="authoredchallenge")
data class AuthoredChallenge(@PrimaryKey var id: String, var name: String, var description: String, var rank: Int?, var rankName: String?, var tags: List<String>, var languages: List<String>)

data class ApiResponse(var data: List<AuthoredChallenge>)

// CHALLENGE DETAILS

data class ChallengeDetails(var id: String, var name: String, var slug: String, var category: String, var publishedAt: String, var approvedAt: String,
                            var languages: List<String>, var url: String, var rank: JSONObject, var createdAt: String, var createdBy: JsonObject?,
                            var approvedBy: JsonObject, var description: String, var totalAttempts: Int, var totalCompleted: Int, var totalStars: Int,
                            var voteScore: Int, var tags: List<String>, var contributorsWanted: Boolean, var unresolved: JsonObject)


// NETWORK STATE

data class NetworkState(var state: String, var errorMessage: String)


// ROOM ENTITIES

@Entity(tableName="user")
data class UserDatabase(@NonNull @PrimaryKey var username: String, var name: String?, var rank: Int, var language: String, var points: Int, var time: Long)

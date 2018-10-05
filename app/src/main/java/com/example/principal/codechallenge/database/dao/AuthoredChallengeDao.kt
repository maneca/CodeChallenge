package com.example.principal.codechallenge.database.dao

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.principal.codechallenge.AuthoredChallenge

@Dao
interface AuthoredChallengeDao {

    @Query("SELECT * FROM authoredchallenge")
    fun getAuthoredChallenges(): DataSource.Factory<Int, AuthoredChallenge>

    @Insert
    fun insertAllChallenge(list: List<AuthoredChallenge>)

    @Query("SELECT COUNT(*) FROM authoredchallenge")
    fun getNrChallenges(): Int

    @Query("DELETE FROM authoredchallenge")
    fun deleteAll()

}
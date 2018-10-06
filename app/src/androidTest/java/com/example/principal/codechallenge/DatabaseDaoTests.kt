package com.example.principal.codechallenge

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.example.principal.codechallenge.database.AppDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseDaoTests {

    private lateinit var database : AppDatabase

    @Before
    fun initDB(){
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase::class.java).build()
    }

    @After
    fun closeDB(){
        database.close()
    }

    @Test
    fun insertUser(){

        val user = UserDatabase("a", null, 1, "Java", 1000, 12220)

        database.userDao().insertUser(user)

        assert(database.userDao().getNumber()>0)
    }

    @Test
    fun getLastFive(){

        val user1 = UserDatabase("a", null, 1, "Java", 1000, 12220)
        val user2 = UserDatabase("b", null, 4, "Kotlin", 1000, 990)
        val user3 = UserDatabase("c", null, 5, "C", 1000, 12)
        val user4 = UserDatabase("d", null, 12, "Haskell", 1000, 45555)
        val user5 = UserDatabase("f", null, 3, "Lisp", 1000, 889)
        val user6 = UserDatabase("g", null, 4, "Javascript", 1000, 11111)

        database.userDao().insertUser(user1)
        database.userDao().insertUser(user2)
        database.userDao().insertUser(user3)
        database.userDao().insertUser(user4)
        database.userDao().insertUser(user5)
        database.userDao().insertUser(user6)

        val users = database.userDao().getLastFive()

        var sortedlist = listOf(user1, user2, user3, user4, user5, user6)
        sortedlist = sortedlist.sortedBy { it.time }

        assert(sortedlist == users.value)
    }

    @Test
    fun testAuthoredChallenges(){

        val challenge1 = AuthoredChallenge("a", "a", "a", null, null, listOf("a"), listOf("a"))
        val challenge2 = AuthoredChallenge("b", "b", "b", 2, "2", listOf("a"), listOf("a"))
        val challenge3 = AuthoredChallenge("c", "c", "c", 556, "556", listOf("a"), listOf("a"))
        val challenge4 = AuthoredChallenge("d", "d", "d", 3, "3", listOf("a"), listOf("a"))
        val challenge5 = AuthoredChallenge("e", "e", "e", 34, "34", listOf("a"), listOf("a"))

        val challenges = listOf(challenge1, challenge2, challenge3, challenge4, challenge5)

        database.authoredChallengesDao().insertAllChallenge(challenges)

        assert(database.authoredChallengesDao().getNrChallenges() == 5)

        database.authoredChallengesDao().deleteAll()

        assert(database.authoredChallengesDao().getNrChallenges() == 0)
    }



}
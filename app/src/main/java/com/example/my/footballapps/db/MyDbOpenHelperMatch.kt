package com.example.my.footballapps.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDbOpenHelperMatch(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteMatch.db", null, 1) {

    companion object {
        private var instance: MyDbOpenHelperMatch? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDbOpenHelperMatch {
            if (instance == null) {
                instance = MyDbOpenHelperMatch(ctx.applicationContext)
            }
            return instance as MyDbOpenHelperMatch
        }
    }
    override fun onCreate(db: SQLiteDatabase?) {

        db?.createTable(FavoriteMatch.TABLE_FAVORITE_MATCH, true,
            FavoriteMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteMatch.ID_EVENT to TEXT + UNIQUE,
            FavoriteMatch.HOME_TEAM to TEXT,
            FavoriteMatch.HOME_SCORE to TEXT,
            FavoriteMatch.AWAY_TEAM to TEXT,
            FavoriteMatch.AWAY_SCORE to TEXT,
            FavoriteMatch.DATE to TEXT,
            FavoriteMatch.TIME to TEXT,
            FavoriteMatch.HOME_ID to TEXT,
            FavoriteMatch.AWAY_ID to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(FavoriteMatch.TABLE_FAVORITE_MATCH, true)
    }
}
val Context.database: MyDbOpenHelperMatch
    get() = MyDbOpenHelperMatch.getInstance(applicationContext)
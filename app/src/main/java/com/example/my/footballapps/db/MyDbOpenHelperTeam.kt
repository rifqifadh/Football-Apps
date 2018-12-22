package com.example.my.footballapps.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDbOpenHelperTeam(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteTeam.db", null, 1) {

    companion object {
        private var instance: MyDbOpenHelperTeam? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDbOpenHelperTeam {
            if (instance == null) {
                instance = MyDbOpenHelperTeam(ctx.applicationContext)
            }
            return instance as MyDbOpenHelperTeam
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {

        db?.createTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true,
            FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
            FavoriteTeam.TEAM_NAME to TEXT,
            FavoriteTeam.TEAM_BADGE to TEXT,
            FavoriteTeam.TEAM_DESCRIPTION to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true)
    }
}
val Context.databaseTeam: MyDbOpenHelperTeam
    get() = MyDbOpenHelperTeam.getInstance(applicationContext)
package com.codewithfk.newsapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codewithfk.newsapp.data.dao.NewsDao
import com.codewithfk.newsapp.data.model.News

@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {
        const val DATABASE_NAME = "news_db"

        @JvmStatic
        fun getDatabase(context: Context): NewsDatabase {
            return Room.databaseBuilder(context, NewsDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
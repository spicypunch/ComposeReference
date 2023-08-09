package com.example.composetodolist.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composetodolist.domain.model.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}
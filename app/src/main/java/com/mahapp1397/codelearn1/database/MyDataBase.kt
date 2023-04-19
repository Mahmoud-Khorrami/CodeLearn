package com.mahapp1397.codelearn1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mahapp1397.codelearn1.models.Sale

@Database(version = 1, entities = [Sale::class])
abstract class MyDataBase : RoomDatabase()
{
    abstract val myDAO: MyDAO

    companion object
    {
        private var INSTANCE: MyDataBase? = null

        fun getInstance(context: Context): MyDataBase
        {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null)
                {

                    instance = Room
                        .databaseBuilder(context.applicationContext, MyDataBase::class.java, "my_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}
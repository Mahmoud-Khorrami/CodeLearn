package com.mahapp1397.codelearn1.hilt

import android.content.Context
import android.content.SharedPreferences
import com.mahapp1397.codelearn1.database.MyDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.text.DecimalFormat
import java.util.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule
{

    @Provides
    @Singleton
    fun provideSharePreference(@ApplicationContext context: Context): SharedPreferences
    {
        return context.getSharedPreferences("MySharePreferences",Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharePreferenceEditor(sharedPreferences: SharedPreferences): SharedPreferences.Editor
    {
        return sharedPreferences.edit()
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context) = MyDataBase.getInstance(context)

    @Singleton
    @Provides
    fun provideDAO(db: MyDataBase) = db.myDAO

    @Provides
    fun provideCalender(): Calendar
    {
        return Calendar.getInstance()
    }

    @Provides
    @Singleton
    fun provideDecimalFormat(): DecimalFormat
    {
        return  DecimalFormat ( "#,###" )
    }
}
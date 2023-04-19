package com.mahapp1397.codelearn1.general

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import saman.zamani.persiandate.PersianDate
import java.util.*
import javax.inject.Inject

class AppUtils @Inject constructor()
{
    @Inject
    lateinit var calendar: Calendar

    fun hideKeyboard(activity: Activity)
    {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val currentFocusedView = activity.currentFocus
        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    fun miladiToPersian(date: String): PersianDate
    {
        val dt = date.split("-")
        calendar.set(dt[0].toInt(), dt[1].toInt() - 1, dt[2].toInt())
        return PersianDate(calendar.time)
    }
}
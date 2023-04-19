package com.mahapp1397.codelearn1.general

import android.util.SparseIntArray
import androidx.fragment.app.FragmentManager
import com.aminography.primecalendar.persian.PersianCalendar
import com.aminography.primedatepicker.common.BackgroundShapeType
import com.aminography.primedatepicker.picker.PrimeDatePicker
import com.aminography.primedatepicker.picker.callback.SingleDayPickCallback
import com.aminography.primedatepicker.picker.theme.LightThemeFactory
import com.mahapp1397.codelearn1.R
import java.util.*
import javax.inject.Inject

class DatePicker @Inject constructor()
{

    @Inject
    lateinit var appUtils: AppUtils

    fun getDate(callback: SingleDayPickCallback, manager: FragmentManager, miladiDate: String)
    {
        val calendar: PersianCalendar

        if(miladiDate.isEmpty())
            calendar = PersianCalendar()

        else
        {
            val persianDate = appUtils.miladiToPersian(miladiDate)
            val month = persianDate.shMonth
            val year = persianDate.shYear

            calendar = PersianCalendar().also {
                it.year = year
                it.month = month-1
            }
        }

        val datPicker = PrimeDatePicker.bottomSheetWith(calendar)
            .pickSingleDay(callback)
            .applyTheme(lightThemeFactory())
            .build()

        datPicker.show(manager,"")
    }

    private fun lightThemeFactory(): LightThemeFactory
    {
        val themeFactory = object : LightThemeFactory() {

            override val typefacePath: String
                get() = "Roboto-Regular.ttf"

            override val dialogBackgroundColor: Int
                get() = getColor(R.color.white)

            override val calendarViewBackgroundColor: Int
                get() = getColor(R.color.white)

            override val pickedDayBackgroundShapeType: BackgroundShapeType
                get() = BackgroundShapeType.ROUND_SQUARE

            override val calendarViewPickedDayBackgroundColor: Int
                get() = getColor(R.color.primaryDarkColor1)

            override val calendarViewPickedDayInRangeBackgroundColor: Int
                get() = getColor(R.color.primaryDarkColor1)

            override val calendarViewPickedDayInRangeLabelTextColor: Int
                get() = getColor(R.color.gray7)

            override val calendarViewTodayLabelTextColor: Int
                get() = getColor(R.color.green1)

            override val calendarViewWeekLabelTextColors: SparseIntArray
                get() = SparseIntArray(7).apply {
                    val primaryDarkColor = getColor(R.color.primaryDarkColor)
                    put(Calendar.SATURDAY, primaryDarkColor)
                    put(Calendar.SUNDAY, primaryDarkColor)
                    put(Calendar.MONDAY, primaryDarkColor)
                    put(Calendar.TUESDAY, primaryDarkColor)
                    put(Calendar.WEDNESDAY, primaryDarkColor)
                    put(Calendar.THURSDAY, primaryDarkColor)
                    put(Calendar.FRIDAY, primaryDarkColor)
                }

            override val calendarViewShowAdjacentMonthDays: Boolean
                get() = true

            override val selectionBarBackgroundColor: Int
                get() = getColor(R.color.primaryColor)

            override val selectionBarRangeDaysItemBackgroundColor: Int
                get() = getColor(R.color.primaryColor)

            override val calendarViewWeekLabelTextSize: Int
                get() = 60

            override val calendarViewDayLabelTextSize: Int
                get() = 40

            override val selectionBarSingleDayItemTopLabelTextSize: Int
                get() = 45

            override val selectionBarSingleDayItemBottomLabelTextSize: Int
                get() = 40
        }

        return themeFactory
    }
}
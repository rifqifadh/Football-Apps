package com.example.my.footballapps.Utils

import android.annotation.SuppressLint
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.View
import android.view.ViewManager
import com.example.my.footballapps.R
import org.jetbrains.anko.appcompat.v7.themedToolbar
import org.jetbrains.anko.dip
import java.text.SimpleDateFormat
import java.util.*

fun ViewManager.myToolbar() = themedToolbar (R.style.ThemeOverlay_AppCompat_Dark) {
    id = R.id.toolbar
    title = resources.getString(R.string.app_name)
    contentInsetStartWithNavigation = dip(0)
    }

    fun View.visible() {
        visibility = View.VISIBLE
    }

    fun View.invisible() {
        visibility = View.INVISIBLE
    }

    @SuppressLint("SimpleDateFormat")
    fun changeFormatDate(date: Date?): String? = with(date ?: Date()) {
        SimpleDateFormat("EEE, dd MMM yyy", Locale.ENGLISH).format(this)
    }

    @SuppressLint("SimpleDateForamt")
    fun toGMTFormat(date: String?, time: String?): Date? {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val dateTime = "$date $time"
        return formatter.parse(dateTime)
    }

    fun String.dateTimeToFormat(format: String = "yyyy-MM-dd HH:mm:ss"): Long {

        val formatter = SimpleDateFormat(format, Locale.ENGLISH)
        val date = formatter.parse(this)

        return date.time
    }

    @SuppressLint("SimpleDateFormat")
    fun strTodate(strDate: String?, pattern: String = "yyyy-MM-dd"): Date {
        val format = SimpleDateFormat(pattern)
        val date = format.parse(strDate)

        return date
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun ripple(view: View, type: Int) {
        view.apply {
            val rippleValue = TypedValue()

            context.theme.resolveAttribute(
                android.R.attr.selectableItemBackground
                , rippleValue, true
            )

            when (type) {
                0 -> foreground = ContextCompat.getDrawable(context, rippleValue.resourceId)
                1 -> background = ContextCompat.getDrawable(context, rippleValue.resourceId)
            }
        }
    }
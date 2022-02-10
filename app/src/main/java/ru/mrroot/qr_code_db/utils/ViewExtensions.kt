package ru.mrroot.qr_code_db.utils

import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun Calendar.toFormattedDisplay(): String {
    val simpleDateFormat = SimpleDateFormat("dd-mm-yyyy hh:mm a", Locale.US)
    return simpleDateFormat.format(this.time)
}
package com.app.climby.util

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment(val listener: (day: Int, month: Int, year: Int) -> Unit, private val selectedDate: String) : DialogFragment(), OnDateSetListener {

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        listener(day, month, year)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var picker = DatePickerDialog(activity as Context, this, year, month, day)
        if (selectedDate != "") {
            val year = selectedDate.split("-")[0].toInt()
            val month = selectedDate.split("-")[1].toInt()-1
            val day = selectedDate.split("-")[2].split(" ")[0].toInt()
            picker = DatePickerDialog(activity as Context, this, year, month, day)
        }
        picker.datePicker.minDate = c.timeInMillis

        return picker
    }
}
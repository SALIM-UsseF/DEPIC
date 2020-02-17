package com.android.application.applicationmobiledepic.Util

import android.text.InputFilter
import android.text.Spanned
import java.lang.NumberFormatException

class InputFilterMinMax(private var min : Int, private var max : Int) : InputFilter {

    fun InputFilterMinMax(min : Int, max : Int){
        if(min <= max) {
            this.max = max
            this.min = min
        } else {
            this.max = min
            this.min = max
        }

    }

    override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
        try {
            val input = Integer.parseInt(dest.toString() + source.toString())
            if(IsInRange(min, max, input)) {
                return null
            }
        } catch (nfe : NumberFormatException) {
            return ""
        }
        return ""
    }


    private fun IsInRange(min: Int, max: Int, input: Int): Boolean {
        return (input <= max && input >= min)
    }
}
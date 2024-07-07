package com.projects.thestoricgame.utils.extensions

import com.google.android.material.textfield.TextInputEditText

inline fun TextInputEditText.checkEmpty(s : String) {
    if(s.isEmpty()) {
        this.error = "Field is Required"
    }
    return
}
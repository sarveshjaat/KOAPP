package com.nictko.services.api

import android.content.Context
import android.widget.Toast

class Extensions {


    fun Context.showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
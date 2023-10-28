package com.morav.firebaseapp.util

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.analytics.FirebaseAnalytics
import com.morav.firebaseapp.R

fun Activity.logEvent(msg: String) {
    val analytics = FirebaseAnalytics.getInstance(this)
    val bundle = Bundle()
    bundle.putString(getString(R.string.info_event), msg)
    analytics.logEvent(this.localClassName, bundle)
}

fun Activity.logErrorEvent(msg: String) {
    val analytics = FirebaseAnalytics.getInstance(this)
    val bundle = Bundle()
    bundle.putString(getString(R.string.error_event), msg)
    analytics.logEvent(this.localClassName, bundle)
}

fun Activity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

fun AppCompatDelegate.changeNightMode(isDark: Boolean) {
    if (isDark) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
    this.applyDayNight()
}

fun Activity.isDark(): Boolean {
    return this.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}
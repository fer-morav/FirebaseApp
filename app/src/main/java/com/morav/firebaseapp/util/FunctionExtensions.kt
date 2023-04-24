package com.morav.firebaseapp.util

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
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

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
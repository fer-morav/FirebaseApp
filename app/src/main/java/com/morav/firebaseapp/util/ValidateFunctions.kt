package com.morav.firebaseapp.util

import android.util.Patterns

fun String.isValidEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this.trim()).matches()

fun String.isValidPassword(): Boolean = this.length > 5
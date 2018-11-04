package io.android.todarch.core.util

import android.util.Patterns

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 4.11.2018.
 */
fun String?.isValidEmail(): Boolean {
    return (this != null) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
/*
 * Copyright 2018 Todarch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.android.todarch.core.util

import android.content.Context
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 4.11.2018.
 */
fun String?.isValidEmail(): Boolean =
        (this != null) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun Context?.dpToPx(dpi: Int): Int {
    if (this == null) return 0
    val scale = resources.displayMetrics.density
    return (dpi * scale + 0.5f).toInt()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(windowToken, 0)
}
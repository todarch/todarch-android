/*
 * Copyright 2019 Todarch
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
package io.android.todarch.core.data

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 16.12.2018.
 */
@Singleton
class Session @Inject constructor(private val prefs: SharedPreferences) {

    private var _token: String? = prefs.getString(KEY_USER_TOKEN, null)
    private var _email: String? = prefs.getString(KEY_USER_EMAIL, null)
    private var _password: String? = prefs.getString(KEY_USER_PASSWORD, null)

    val isLoggedIn: Boolean
        get() = token != null || email != null || password != null

    /**
     * token used for "is login checks"
     */
    var token: String? = _token
        set(value) {
            prefs.edit { putString(KEY_USER_TOKEN, value) }
            field = value
        }

    var email: String? = _email
        set(value) {
            prefs.edit { putString(KEY_USER_EMAIL, value) }
            field = value
        }

    var password: String? = _password
        set(value) {
            prefs.edit { putString(KEY_USER_PASSWORD, value) }
            field = value
        }

    fun clear() {
        token = null
        email = null
        password = null
    }

    companion object {
        private const val KEY_USER_TOKEN = "KEY_USER_TOKEN"
        private const val KEY_USER_EMAIL = "KEY_USER_EMAIL"
        private const val KEY_USER_PASSWORD = "KEY_USER_PASSWORD"
    }
}
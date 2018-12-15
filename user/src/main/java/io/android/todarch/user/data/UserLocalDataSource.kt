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
package io.android.todarch.user.data

import android.content.SharedPreferences
import androidx.core.content.edit
import io.android.todarch.core.data.model.User
import io.android.todarch.user.data.database.UserDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 19.11.2018.
 */
@Singleton
class UserLocalDataSource @Inject constructor(
    private val prefs: SharedPreferences,
    private val userDao: UserDao
) {

    private var _token: String? = prefs.getString(KEY_USER_TOKEN, null)

    /**
     * token used for "is login checks"
     */
    var token: String? = _token
        set(value) {
            prefs.edit { putString(KEY_USER_TOKEN, value) }
            field = value
        }

    /**
     * Get the logged in user. If missing, null is returned
     */
    suspend fun getUser(): User? {
        val loggedInUser = userDao.getLoggedInUser()
        token = loggedInUser?.token
        return loggedInUser
    }

    suspend fun setUser(user: User) {
        prefs.edit { putString(KEY_USER_TOKEN, user.token) }
        userDao.setLoggedInUser(user)
    }

    /**
     * Clear all data related to this user
     */
    suspend fun logout() {
        prefs.edit { KEY_USER_TOKEN to null }
        token = null
        userDao.deleteLoggedInUser()
    }

    companion object {
        private const val KEY_USER_TOKEN = "KEY_USER_TOKEN"
    }
}
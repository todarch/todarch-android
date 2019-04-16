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

import io.android.todarch.core.data.Session
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
    private val session: Session,
    private val userDao: UserDao
) {
    /**
     * Get the logged in user. If missing, null is returned
     */
    suspend fun getUser(): User? = userDao.getLoggedInUser()

    suspend fun setUser(user: User) {
        user.apply {
            session.token = token
            session.email = email
            session.password = password

            userDao.setLoggedInUser(this)
        }
    }

    /**
     * Clear all data related to this user
     */
    suspend fun logout() {
        session.clear()
        userDao.deleteLoggedInUser()
    }
}
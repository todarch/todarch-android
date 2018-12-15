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
package io.android.todarch.user.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Insert
import io.android.todarch.core.data.model.User

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 23.11.2018.
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE email IN (:emails)")
    fun loadAllByEmails(emails: Array<String>): List<User>

    @Query("SELECT * FROM users LIMIT 1")
    fun getLoggedInUser(): User?

    /**
     * Sets the [User]. This method guarantees that only one
     * User is ever in the table by first deleting all table
     * data before inserting the User.
     *
     * This method should be used instead of [insertLoggedInUser].
     */
    @Transaction
    fun setLoggedInUser(user: User): Long {
        deleteLoggedInUser()
        return insertLoggedInUser(user)
    }

    @Query("DELETE FROM users")
    fun deleteLoggedInUser()

    /**
     * This method should not be used.  Instead, use [setLoggedInUser],
     * as that method guarantees only a single [User] will reside
     * in the table.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLoggedInUser(user: User): Long
}
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
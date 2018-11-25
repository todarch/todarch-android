package io.android.todarch.user.data

import android.content.SharedPreferences
import android.util.Log
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

    private var _userId: String? = prefs.getString(KEY_USER_ID, null)

    /**
     * User Id used for "is login checks"
     */
    var userId: String? = _userId
        set(value) {
            prefs.edit { putString(KEY_USER_ID, value) }
            field = value
        }

    /**
     * Get the logged in user. If missing, null is returned
     */
    suspend fun getUser(): User? {
        val loggedInUser = userDao.getLoggedInUser()
        userId = loggedInUser?.email
        return loggedInUser
    }

    suspend fun setUser(user: User) {
        prefs.edit { putString(KEY_USER_ID, user.email) }
        val a = userDao.setLoggedInUser(user)
        Log.d("deneme", a.toString())
    }

    /**
     * Clear all data related to this user
     */
    suspend fun logout() {
        prefs.edit { KEY_USER_ID to null }
        userId = null
        userDao.deleteLoggedInUser()
    }

    companion object {
        private const val KEY_USER_ID = "KEY_USER_ID"
    }
}
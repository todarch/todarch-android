package io.android.todarch.user

import android.content.Context
import androidx.lifecycle.ViewModel
import javax.inject.Inject

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 3.11.2018.
 */
class UserViewModel @Inject constructor(val context: Context) : ViewModel() {

    fun login(email: String, password: String) {
        // TODO call UserRepository for login process
    }
}
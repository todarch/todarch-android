package io.android.todarch.user.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.android.todarch.core.base.BaseActivity
import io.android.todarch.user.R

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 2.11.2018.
 */
class UserManagementActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_management)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, UserManagementActivity::class.java)
        }
    }
}
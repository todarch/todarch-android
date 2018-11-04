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
package io.android.todarch.ui.todo

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import dagger.android.support.DaggerAppCompatActivity
import io.android.todarch.R
import io.android.todarch.databinding.ActivityTodoBinding
import io.android.todarch.user.UserManagementActivity

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 13.10.2018.
 */
class TodoActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTodoBinding = DataBindingUtil.setContentView(this, R.layout.activity_todo)
        val navController = Navigation.findNavController(this, R.id.todo_nav_fragment)

        // Set up ActionBar
        setSupportActionBar(binding.toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController)

        if (savedInstanceState == null) {
            // TODO savedInstanceState check is for testing purpose, remove it and add user logged in check
            startActivityForResult(UserManagementActivity.newIntent(this), REQUEST_CODE_LOGIN)
            overridePendingTransition(0, 0) // Disable activity animation
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_LOGIN) {
            if (resultCode == RESULT_OK) {
                // TODO trigger user related ui info update
            }
        }
    }

    companion object {
        const val REQUEST_CODE_LOGIN = 0x1
    }
}
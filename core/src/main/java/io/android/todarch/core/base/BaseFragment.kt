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
package io.android.todarch.core.base

import androidx.annotation.CallSuper
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import dagger.android.support.DaggerFragment
import io.android.todarch.core.util.ContextAware

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 15.10.2018.
 */
abstract class BaseFragment : DaggerFragment(), ContextAware {
    private var dialogFragment: DialogFragment? = null
    private var dialogTag: String? = null
    private var checkOpenDialog: Boolean = false

    @CallSuper
    override fun onStart() {
        super.onStart()
        if (checkOpenDialog) {
            openDialog()
        }
    }

    protected fun showFragmentDialog(dialogFragment: DialogFragment, tag: String) {
        this.dialogFragment = dialogFragment
        this.dialogTag = tag
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            openDialog()
        } else {
            checkOpenDialog = true
        }
    }

    private fun openDialog() {
        val activity = activity ?: return
        checkOpenDialog = false
        activity.supportFragmentManager.apply {
            val fragmentTransaction = beginTransaction()
            val fragment = findFragmentByTag(dialogTag)
            if (fragment != null) {
                fragmentTransaction.remove(fragment)
            }
            fragmentTransaction.addToBackStack(null)
            dialogFragment?.show(fragmentTransaction, dialogTag)
        }
    }
}
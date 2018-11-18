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
package io.android.todarch.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import io.android.todarch.core.base.BaseFragment
import io.android.todarch.core.util.isValidEmail
import io.android.todarch.core.util.strings
import io.android.todarch.user.R
import io.android.todarch.user.databinding.FragmentRegisterBinding

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 10.11.2018.
 */
class RegisterFragment : BaseFragment(), View.OnClickListener {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.register.setOnClickListener(this)
        binding.toolbar.toolbarBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val navController = this@RegisterFragment.findNavController()
        when (v?.id) {
            R.id.register -> {
                val email = binding.email.editText?.text?.trim()?.toString()
                val password = binding.password.editText?.text?.toString()
                if (validateInputs(email, password)) {
                    // TODO register user
                    navController.navigateUp()
                }
            }
            R.id.toolbar_back -> {
                navController.navigateUp()
            }
        }
    }

    // TODO ask the validation business
    private fun validateInputs(email: String?, password: String?): Boolean {
        binding.email.isErrorEnabled = false
        binding.password.isErrorEnabled = false

        if (!email.isValidEmail()) {
            binding.email.error = strings?.get(R.string.error_invalid_email)
            binding.email.isErrorEnabled = true
            return false
        }
        if (password.isNullOrEmpty()) {
            binding.password.error = strings?.get(R.string.error_invalid_password)
            binding.password.isErrorEnabled = true
            return false
        }
        return true
    }
}
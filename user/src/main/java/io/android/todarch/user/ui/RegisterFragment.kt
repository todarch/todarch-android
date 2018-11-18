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
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import io.android.todarch.core.base.BaseFragment
import io.android.todarch.core.util.isValidEmail
import io.android.todarch.core.util.strings
import io.android.todarch.user.R
import io.android.todarch.user.databinding.FragmentRegisterBinding
import javax.inject.Inject

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 10.11.2018.
 */
class RegisterFragment : BaseFragment(), View.OnClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        registerViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(RegisterViewModel::class.java)

        registerViewModel.uIState.observe(this, Observer { registerUIModel ->
            val uIModel = registerUIModel ?: return@Observer

            if (uIModel.showProgress) {
                binding.register.isEnabled = false
                binding.progressBar.visibility = View.VISIBLE
                binding.progressBar.show()
            } else {
                binding.register.isEnabled = true
                binding.progressBar.hide()
                binding.progressBar.visibility = View.GONE
            }

            if ((uIModel.showError != null) && !uIModel.showError.consumed) {
                uIModel.showError.consume()?.let { showRegisterFailed(it) }
            }

            if ((uIModel.showSuccess != null) && !uIModel.showSuccess.consumed) {
                uIModel.showSuccess.consume()?.let {
                    this@RegisterFragment.findNavController().navigateUp()
                }
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.password.editText?.apply {
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    register()
                }
                false
            }
        }
        binding.register.setOnClickListener(this)
        binding.toolbar.toolbarBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.register -> {
                register()
            }
            R.id.toolbar_back -> {
                this@RegisterFragment.findNavController().navigateUp()
            }
        }
    }

    private fun register() {
        val email = binding.email.editText?.text?.trim()?.toString()
        val password = binding.password.editText?.text?.toString()
        if (validateInputs(email, password)) {
            registerViewModel.register(email!!, password!!)
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

    private fun showRegisterFailed(@StringRes errorString: Int) {
        Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show()
    }
}
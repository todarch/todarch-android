package io.android.todarch.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import io.android.todarch.core.base.BaseFragment
import io.android.todarch.core.util.isValidEmail
import io.android.todarch.user.databinding.FragmentLoginBinding
import javax.inject.Inject

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 3.11.2018.
 */
class LoginFragment : BaseFragment(), View.OnClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentLoginBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(UserViewModel::class.java)

        binding.login.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login -> {
                val email = binding.email.editText?.text?.trim()?.toString()
                val password = binding.password.editText?.text?.toString()
                if (validateInputs(email, password)) {
                    userViewModel.login(email!!, password!!)
                }
            }
        }
    }

    // TODO ask the validation business
    private fun validateInputs(email: String?, password: String?): Boolean {
        binding.email.isErrorEnabled = false
        binding.password.isErrorEnabled = false

        if (!email.isValidEmail()) {
            binding.email.error = context?.resources?.getString(R.string.error_invalid_email)
            binding.email.isErrorEnabled = true
            return false
        }
        if (password.isNullOrEmpty()) {
            binding.password.error = context?.resources?.getString(R.string.error_invalid_password)
            binding.password.isErrorEnabled = true
            return false
        }
        return true
    }
}
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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.android.todarch.R
import io.android.todarch.databinding.FragmentAddTodoBottomSheetBinding

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 15.10.2018.
 */
class AddTodoFragmentBottomSheet : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var binding: FragmentAddTodoBottomSheetBinding

    override fun onStart() {
        super.onStart()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_todo_bottom_sheet, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.save.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.save -> {
                // TODO save todo
            }
        }
        dismiss()
    }

    companion object {
        @JvmField
        val TAG: String = AddTodoFragmentBottomSheet::class.java.simpleName

        @JvmStatic
        fun newInstance(): AddTodoFragmentBottomSheet {
            return AddTodoFragmentBottomSheet()
        }
    }
}
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

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import io.android.todarch.R
import io.android.todarch.core.util.dpToPx
import io.android.todarch.core.util.hideKeyboard
import io.android.todarch.databinding.FragmentAddTodoBottomSheetBinding
import javax.inject.Inject

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 15.10.2018.
 */
class AddTodoFragmentBottomSheet : BottomSheetDialogFragment(), View.OnClickListener, HasSupportFragmentInjector {
    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentAddTodoBottomSheetBinding
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = childFragmentInjector

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        bottomSheetDialog.setOnShowListener {
            val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)

            bottomSheetBehavior.peekHeight = context.dpToPx(150)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(p0: View, p1: Float) {
                    // no-op
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    animateViewWhenStateChanged(newState)
                }
            })
        }

        return bottomSheetDialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_todo_bottom_sheet, container, false)
        todoViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(TodoViewModel::class.java)

        makeBottomSheetFullSize()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.save.setOnClickListener(this)
        binding.up.setOnClickListener(this)

        binding.tags.editText?.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val txtVal = v.text
                if (!txtVal.isNullOrEmpty()) {
                    addChipToGroup(txtVal.toString())
                    binding.tags.editText?.setText("")
                }

                return@OnEditorActionListener true
            }
            false
        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.save -> {
                addTask()
            }
            R.id.up -> {
                bottomSheetBehavior.state = when (bottomSheetBehavior.state) {
                    BottomSheetBehavior.STATE_EXPANDED -> BottomSheetBehavior.STATE_COLLAPSED
                    BottomSheetBehavior.STATE_COLLAPSED -> BottomSheetBehavior.STATE_EXPANDED
                    else -> return
                }
            }
        }
    }

    private fun addTask() {
        val title = binding.title.editText?.text?.trim().toString()
        val description = binding.description.editText?.text?.trim().toString()
        val tags = getListOfTags()

        if (validateInputs(title)) {
            todoViewModel.saveTask(title, description, tags)
            dismiss()
        }
    }

    private fun getListOfTags(): List<String> {
        val size = binding.chipGroup.childCount
        val tags = ArrayList<String>()
        for (i in 0 until size) {
            val chip = binding.chipGroup.getChildAt(i) as Chip
            tags.add(chip.text.toString())
            Log.d("melo", chip.text.toString())
        }
        return tags
    }

    private fun validateInputs(title: String?): Boolean {
        if (title.isNullOrEmpty()) {
            Toast.makeText(context, R.string.error_invalid_task_title, Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun addChipToGroup(tagName: String) {
        val chip = Chip(context).apply {
            text = tagName
            isCloseIconVisible = true
            isChipIconVisible = false
            isCheckable = false
            isClickable = false
            setOnCloseIconClickListener { binding.chipGroup.removeView(this) }
        }
        binding.chipGroup.addView(chip)
    }

    private fun animateViewWhenStateChanged(newState: Int) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.bottomSheetConstraintLayout)

        val rotation = when (newState) {
            BottomSheetBehavior.STATE_EXPANDED -> {
                constraintSet.setVisibility(R.id.groupMore, View.VISIBLE)

                constraintSet.clear(R.id.save, ConstraintSet.TOP)
                constraintSet.connect(R.id.save,
                        ConstraintSet.BOTTOM,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.BOTTOM,
                        0)
                constraintSet.connect(R.id.save,
                        ConstraintSet.BOTTOM,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.BOTTOM,
                        context?.resources?.getDimensionPixelSize(R.dimen.normal_size)
                                ?: 0)
                constraintSet.connect(R.id.chipGroup,
                        ConstraintSet.BOTTOM,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.BOTTOM,
                        context.dpToPx(100))

                180f
            }
            BottomSheetBehavior.STATE_COLLAPSED -> {
                binding.root.hideKeyboard()

                constraintSet.setVisibility(R.id.groupMore, View.GONE)

                constraintSet.clear(R.id.save, ConstraintSet.BOTTOM)
                constraintSet.connect(R.id.save, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0)

                0f
            }
            BottomSheetBehavior.STATE_HIDDEN -> {
                dismiss()
                0f
            }
            else -> return
        }

        constraintSet.applyTo(binding.bottomSheetConstraintLayout)

        binding.up.animate().rotationX(rotation).start()
    }

    private fun makeBottomSheetFullSize() {
        val params = binding.bottomSheetConstraintLayout.layoutParams as LinearLayout.LayoutParams
        params.height = Resources.getSystem().displayMetrics.heightPixels
        binding.bottomSheetConstraintLayout.layoutParams = params
    }

    companion object {
        @JvmField
        val TAG: String = AddTodoFragmentBottomSheet::class.java.simpleName

        @JvmStatic
        fun newInstance(): AddTodoFragmentBottomSheet = AddTodoFragmentBottomSheet()
    }
}
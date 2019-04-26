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
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.android.todarch.R
import io.android.todarch.core.base.BaseFragment
import io.android.todarch.core.data.model.Task
import io.android.todarch.databinding.FragmentTodoListBinding
import javax.inject.Inject

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 14.10.2018.
 */
class TodoListFragment : BaseFragment(), View.OnClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentTodoListBinding
    private lateinit var bottomSheetFragmentBottomSheet: AddTodoFragmentBottomSheet
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var todoListAdapter: TodoListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTodoListBinding.inflate(inflater, container, false)
        todoViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(TodoViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoViewModel.uIState.observe(this, Observer { todoUIModel ->
            val uIModel = todoUIModel ?: return@Observer

            if (uIModel.showProgress) {
                // TODO show progress
            } else {
                // TODO hide progress
            }

            if ((uIModel.showError != null) && !uIModel.showError.consumed) {
                uIModel.showError.consume()?.let {
                    // TODO show error
                }
            }

            if ((uIModel.showEmpty != null) && !uIModel.showEmpty.consumed) {
                uIModel.showEmpty.consume()?.let {
                    // TODO show empty view
                }
            }

            if ((uIModel.showSuccess != null) && !uIModel.showSuccess.consumed) {
                uIModel.showSuccess.consume()?.apply {
                    todoListAdapter.updateTasks(this)
                }
            }

            if ((uIModel.taskUpdated != null) && !uIModel.taskUpdated.consumed) {
                uIModel.taskUpdated.consume()?.let {
                    // TODO update task
                }
            }
        })

        todoListAdapter = TodoListAdapter(object : TodoListAdapter.TodoItemListener {
            override fun onTaskDelete(item: Task) {
                // TODO remove task
            }

            override fun onItemClick(item: Task) {
                // TODO open task detail
            }
        })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)

            val drawable = ContextCompat.getDrawable(context, R.drawable.bg_divider)
            if (drawable != null) {
                val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                itemDecorator.setDrawable(drawable)
                addItemDecoration(itemDecorator)
            }

            setHasFixedSize(false)
            adapter = todoListAdapter
        }

        bottomSheetFragmentBottomSheet = AddTodoFragmentBottomSheet.newInstance()
        binding.addTodo.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.add_todo -> {
                if (bottomSheetFragmentBottomSheet.showsDialog) {
                    showFragmentDialog(
                            bottomSheetFragmentBottomSheet,
                            AddTodoFragmentBottomSheet.TAG
                    )
                } else {
                    bottomSheetFragmentBottomSheet.dismiss()
                }
            }
        }
    }
}
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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.android.todarch.R
import io.android.todarch.core.data.model.Task
import io.android.todarch.core.ui.ItemClickListener
import io.android.todarch.databinding.ItemTodoBinding

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 1.11.2018.
 */
class TodoListAdapter(private val listener: TodoItemListener) : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
    private val items = mutableListOf<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder =
            TodoViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_todo, parent, false))

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val task = items[position]
        task.run {
            holder.binding.title.text = if (title.isNotEmpty()) {
                title
            } else {
                description
            }

            holder.binding.root.setOnClickListener {
                listener.onItemClick(this)
            }
        }
    }

    override fun getItemCount() = items.size

    fun updateTasks(tasks: List<Task>?) {
        items.clear()
        tasks?.let {
            items.addAll(it)
        }
        notifyDataSetChanged()
    }

    class TodoViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

    interface TodoItemListener : ItemClickListener<Task> {
        fun onTaskDelete(item: Task)
    }
}
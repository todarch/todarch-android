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
package io.android.todarch.core.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 10.11.2018.
 */
@Entity(tableName = "tasks")
data class Task @JvmOverloads constructor(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: String,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String = "",

    @ColumnInfo(name = "description")
    @SerializedName("description")
    var description: String = "",

    @ColumnInfo(name = "completed")
    var completed: Boolean = false,

    @ColumnInfo(name = "priority")
    @SerializedName("priority")
    var priority: Int = 0,

    @ColumnInfo(name = "status")
    @SerializedName("status")
    var status: String = "",

    @ColumnInfo(name = "tags")
    @SerializedName("tags")
    var tags: List<String> = ArrayList(),

    @ColumnInfo(name = "time_needed_in_min")
    @SerializedName("timeNeededInMin")
    var timeNeededInMin: Long = 0L,

    @ColumnInfo(name = "created_date_in_epoch")
    @SerializedName("createdAtEpoch")
    var createdDateInEpoch: Long = 0L,

    @ColumnInfo(name = "done_date_in_epoch")
    @SerializedName("doneDateEpoch")
    var doneDateInEpoch: Long = 0L
)
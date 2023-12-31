package com.example.ebrain.feature_task.domain.use_cases

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.ebrain.feature_task.domain.model.InvalidTaskException
import com.example.ebrain.feature_task.domain.model.Task
import com.example.ebrain.feature_task.domain.repository.TaskRepository
import java.time.LocalDateTime

class AddTask(
    private val repository: TaskRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    @Throws(InvalidTaskException::class)
    suspend operator fun invoke(task: Task){
        if (task.title.isBlank()){
            throw InvalidTaskException("Title Can't be empty ...")
        }
        if (task.date <= LocalDateTime.now()){
            throw InvalidTaskException("Past Date is not acceptable ...")
        }
        repository.insertTask(task)
    }
}
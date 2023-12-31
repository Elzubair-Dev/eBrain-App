package com.example.ebrain.feature_task.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.ebrain.feature_task.data.data_source.TaskDatabase
import com.example.ebrain.feature_task.data.repository.TaskRepoImp
import com.example.ebrain.feature_task.domain.repository.TaskRepository
import com.example.ebrain.feature_task.domain.use_cases.AddTask
import com.example.ebrain.feature_task.domain.use_cases.DeleteTask
import com.example.ebrain.feature_task.domain.use_cases.GetPublicTaskByTitle
import com.example.ebrain.feature_task.domain.use_cases.GetPublicTasks
import com.example.ebrain.feature_task.domain.use_cases.GetTask
import com.example.ebrain.feature_task.domain.use_cases.GetTasks
import com.example.ebrain.feature_task.domain.use_cases.GetTasksByTitle
import com.example.ebrain.feature_task.domain.use_cases.TaskUseCases
import com.example.ebrain.feature_task.domain.util.notification_alarm.TaskAlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Singleton

@DelicateCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object TaskModule {
    @Provides
    @Singleton
    fun provideTaskDatabase(app: Application): TaskDatabase {
        return Room.databaseBuilder(
            app,
            TaskDatabase::class.java,
            TaskDatabase.DATABASE_NAME
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(db: TaskDatabase): TaskRepository {
        return TaskRepoImp(db.taskDao)
    }

    @Provides
    @Singleton
    fun provideTaskUseCases(repository: TaskRepository): TaskUseCases {
        return TaskUseCases(
            getTasks = GetTasks(repository),
            deleteTask = DeleteTask(repository),
            addTask = AddTask(repository),
            getTask = GetTask(repository),
            getPublicTasks = GetPublicTasks(repository),
            getTasksByTitle = GetTasksByTitle(repository),
            getPublicTasksByTitle = GetPublicTaskByTitle(repository)
        )
    }

    @Provides
    @Singleton
    fun provideTaskAlarmScheduler(@ApplicationContext context: Context): TaskAlarmScheduler {
        return TaskAlarmScheduler(context)
    }
}
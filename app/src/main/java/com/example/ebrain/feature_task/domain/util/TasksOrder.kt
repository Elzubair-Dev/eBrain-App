package com.example.ebrain.feature_task.domain.util

import com.example.ebrain.core.domain.util.OrderType

sealed class TasksOrder(
    val order: OrderType
){
    class Title(orderType: OrderType):TasksOrder(orderType)
    class Date(orderType: OrderType):TasksOrder(orderType)
    class Priority(orderType: OrderType):TasksOrder(orderType)

    fun copy(order: OrderType): TasksOrder{
        return when(this){
            is Title -> Title(order)
            is Date -> Date(order)
            is Priority -> Priority(order)
        }
    }
}

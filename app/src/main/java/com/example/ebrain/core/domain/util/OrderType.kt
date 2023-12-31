package com.example.ebrain.core.domain.util

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}

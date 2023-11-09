package com.shpp.application.level_4.presentation.interfaces

interface MultiSelectState<T>  {

    fun isChecked(item: T): Boolean

    val totalCheckedCount: Int

}
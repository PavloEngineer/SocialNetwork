package com.shpp.application.level_4.presentation.interfaces

import com.shpp.application.level_4.data.model.User
import com.shpp.application.level_4.presentation.fragments.my_contacts.multiselect.ContactMultiSelect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface MultiSelectHandler<T : Any> : MultiSelectState<T> {
    /**
     * Set the list flow which will be observed by the handler in order
     * to keep the inner state up-to-date. Usually call this method in the init
     * block of your view-model.
     */
    fun setItems(items: List<T>)

    fun getItems(): List<T>

    fun setOnChangedListener(listener: ContactMultiSelect.OnChangedListener)

    /**
     * Invert selection for the specified item.
     */
    fun toggle(item: T)

    /**
     * Check all items.
     */
    fun selectAll()

    /**
     * Uncheck all items.
     */
    fun clearAll()

    /**
     * Check the specified item in the list
     */
    fun check(item: T)

    /**
     * Uncheck the specified item in the list.
     */
    fun clear(item: T)
}
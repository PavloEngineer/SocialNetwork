 package com.shpp.application.level_4.presentation.fragments.my_contacts.multiselect

import com.shpp.application.level_4.data.model.User
import com.shpp.application.level_4.presentation.interfaces.MultiSelectHandler
import java.util.UUID

class ContactMultiSelect : MultiSelectHandler<User> {
    private val checkedIds = HashSet<UUID>()
    private var items: List<User> = emptyList()
    private var onChangedListener: OnChangedListener? = null

    override fun setItems(users: List<User>) {
        this.items = users
        removeDeletedUsers(users)
        onChangedListener?.onChanged()
    }

    override fun getItems(): List<User> {
        return items
    }

    override fun setOnChangedListener(listener: OnChangedListener) {
        onChangedListener = listener
    }


    override fun isChecked(item: User): Boolean {
        return checkedIds.contains(item.id)
    }

    override fun toggle(item: User) {
        if (isChecked(item)) {
            clear(item)
        } else {
            check(item)
        }
    }

    override fun check(item: User) {
        if (!exists(item)) return

        checkedIds.add(item.id)
        onChangedListener?.onChanged()
    }

    override fun clear(item: User) {
        if (!exists(item)) return

        checkedIds.remove(item.id)
        onChangedListener?.onChanged()
    }

    override fun selectAll() {
        checkedIds.addAll(items.map { it.id })
        onChangedListener?.onChanged()
    }

    override fun clearAll() {
        checkedIds.clear()
        onChangedListener?.onChanged()
    }

    override val totalCheckedCount: Int
        get() = checkedIds.size

    private fun exists(item: User): Boolean {
        return items.any { it.id == item.id }
    }

    private fun removeDeletedUsers(users: List<User>) {
        val existingIds = HashSet(users.map { it.id })
        val iterator = checkedIds.iterator()
        while (iterator.hasNext()) {
            val id = iterator.next()
            if (!existingIds.contains(id)) {
                iterator.remove()
            }
        }
    }

    interface OnChangedListener {
        fun onChanged()
    }
}

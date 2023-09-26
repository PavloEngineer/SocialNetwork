package com.shpp.application.level_2.presentation.callBacks

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.shpp.application.level_2.presentation.my_contacts.adapter.UsersAdapter

class SwipeToDeleteCallback(
    private val onSwiped: (Int) -> Unit = {}
) : ItemTouchHelper.SimpleCallback(
    0,
    ItemTouchHelper.LEFT
) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        when(direction) {
            ItemTouchHelper.LEFT -> {
                onSwiped(position)
            }
        }
    }
}

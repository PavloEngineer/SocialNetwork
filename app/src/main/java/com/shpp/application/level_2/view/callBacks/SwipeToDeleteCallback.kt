package com.shpp.application.level_2.view.callBacks

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.shpp.application.level_2.view.UsersAdapter

class SwipeToDeleteCallback(private val adapter: UsersAdapter) : ItemTouchHelper.SimpleCallback(
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
        val user = adapter.users.get(position)
        when(direction) {
            ItemTouchHelper.LEFT -> {
                adapter.myContactsViewModel.deleteUser(user)
                adapter.undoDelete(user, position)
            }
        }
    }
}

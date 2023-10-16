package com.shpp.application.level_3.presentation.callBacks

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

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
        onSwiped(position)
    }
}

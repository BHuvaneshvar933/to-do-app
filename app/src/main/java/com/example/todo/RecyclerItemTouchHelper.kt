package com.example.todo

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.Adapter.ToDoAdapter
import androidx.core.content.ContextCompat

class RecyclerItemTouchHelper(private val adapter: ToDoAdapter) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
       return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if(direction == ItemTouchHelper.LEFT){
            val builder = AlertDialog.Builder(adapter.getContext())
            builder.setTitle("Delete task")
            builder.setMessage("Are you sure you want to delete this task")
            builder.setPositiveButton("Confirm") {_,_ ->
                adapter.deleteItem(position)

            }
            builder.setNegativeButton(android.R.string.cancel) {dialog,_ ->
                adapter.notifyItemChanged(viewHolder.adapterPosition)
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        } else {
            adapter.editItem(position)
        }

    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val icon: Drawable?
        val background: ColorDrawable?
        val backgroundCornerOffset = 20
        val itemView: View = viewHolder.itemView

        if (dX > 0) {
            // Swipe right — edit
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.baseline_edit)
            background = ColorDrawable(ContextCompat.getColor(adapter.getContext(), R.color.colorPrimaryDark))
        } else {
            // Swipe left — delete
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.baseline_delete)
            background = ColorDrawable(Color.RED)
        }

        icon?.let {
            val iconMargin = (itemView.height - it.intrinsicHeight) / 2
            val iconTop = itemView.top + iconMargin
            val iconBottom = iconTop + it.intrinsicHeight

            if (dX > 0) {
                val iconLeft = itemView.left + iconMargin
                val iconRight = iconLeft + it.intrinsicWidth
                it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                background?.setBounds(
                    itemView.left,
                    itemView.top,
                    itemView.left + dX.toInt() + backgroundCornerOffset,
                    itemView.bottom
                )
            } else if (dX < 0) {
                val iconRight = itemView.right - iconMargin
                val iconLeft = iconRight - it.intrinsicWidth
                it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                background?.setBounds(
                    itemView.right + dX.toInt() - backgroundCornerOffset,
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
            } else {
                background?.setBounds(0, 0, 0, 0)
            }

            background?.draw(c)
            it.draw(c)
        }
    }

}
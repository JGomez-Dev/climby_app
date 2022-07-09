package com.app.climby.util.extension

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BoundsOffsetDecoration: RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemPosition = parent.getChildAdapterPosition(view)
        // It is crucial to refer to layoutParams.width (view.width is 0 at this time)!
        //val itemWidth = view.layoutParams.height
        val offset = (parent.height - view.height) / 2
        if (itemPosition == 0) {
            outRect.top = offset
        } else if (itemPosition == state.itemCount - 1) {
            outRect.bottom = offset
        }
    }
}
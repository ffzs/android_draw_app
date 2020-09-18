package com.ffzs.draw.myCanvas

import android.graphics.Path
import android.graphics.RectF
import kotlin.math.max
import kotlin.math.min

/**
 * @author: ffzs
 * @Date: 20-9-18 上午9:54
 */
class Bound {
    private val bound: RectF = RectF(Float.MAX_VALUE, Float.MAX_VALUE, 0.0f, 0.0f)

    fun add(path: Path) {
        val bound = RectF()
        path.computeBounds(bound, true)
        add(bound)
    }

    private fun add(rect: RectF) {
        bound.left = min(bound.left, rect.left)
        bound.right = max(bound.right, rect.right)
        bound.top = min(bound.top, rect.top)
        bound.bottom = max(bound.bottom, rect.bottom)
    }
}

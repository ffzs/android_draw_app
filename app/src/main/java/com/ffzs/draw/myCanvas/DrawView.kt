package com.ffzs.draw.myCanvas

/**
 * @author: ffzs
 * @Date: 20-9-18 上午9:50
 */
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.Paint.Join.ROUND
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import com.ffzs.draw.R
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.math.abs


@AndroidEntryPoint
class DrawView (context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var xx = 0f;
    private var yy = 0f;
    private var path: Path? = null
    private val paths = ArrayList<Path>()
    private var bitmap: Bitmap? = null
    private var canvas: Canvas? = null
    private val paint: Paint = Paint()
    private val bitmapPaint = Paint(Paint.DITHER_FLAG)
    private var maxBound: Bound? = null
    private val brushSize = 15f
    private val touchGas = 4f
    private var canvasPaintColor = Color.WHITE
    private var canvanBgColor = Color.BLACK

    init {
        canvanBgColor = resources.getColor(R.color.canvasBgColor)
        canvasPaintColor = resources.getColor(R.color.canvasPaintColor)
        paint.isAntiAlias = true
        paint.isDither = true
        paint.color = canvanBgColor
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.alpha = 0xff
    }


    fun init(metrics: DisplayMetrics) {
        val width = metrics.widthPixels
        val height = metrics.heightPixels / 10 * 9
        maxBound = Bound()
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap!!)
    }

    fun clear() {
        paths.clear()
        maxBound = Bound()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        this.canvas!!.drawColor(canvanBgColor)
        for (path in paths) {
            paint.color = canvasPaintColor
            paint.strokeWidth = brushSize
            this.canvas!!.drawPath(path, paint)
        }
        canvas.drawBitmap(bitmap!!, 0f, 0f, bitmapPaint)
        canvas.restore()
    }

    private fun touchStart(x: Float, y: Float) {
        if (x < 0 || x > canvas!!.width || y < 0 || y > canvas!!.height) {
            return
        }
        path = Path()
        paths.add(path!!)
        path!!.reset()
        path!!.moveTo(x, y)
        xx = x
        yy = y
    }

    private fun touchMove(x: Float, y: Float) {
        if (x < 0 || x > canvas!!.width || y < 0 || y > canvas!!.height) {
            return
        }

        val dx = abs(x - xx)
        val dy = abs(y - yy)
        if (dx >= touchGas || dy >= touchGas) {
            path!!.quadTo(xx, yy, (x + xx) / 2, (y + yy) / 2)
            xx = x
            yy = y
        }
    }

    private fun touchUp() {
        path!!.lineTo(xx, yy)
        maxBound!!.add(Path(path))
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
        }
        return true
    }
}
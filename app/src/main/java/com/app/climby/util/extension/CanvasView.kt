package com.app.climby.util.extension

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class CanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val paint:Paint = Paint()
        /*paint.color = Color.WHITE
        paint.strokeWidth = 3f
        canvas?.drawLine(0f,0f,700f,1000f, paint)

        canvas?.drawCircle(100f,100f,80f, paint)*/
        /*val numberTriangle = Random.nextInt(1,3)
        for(i in 1..numberTriangle){*/
        createRandomTriangle(paint, canvas)
        createRandomTriangle1(paint, canvas)
        createRandomTriangle2(paint, canvas)
        /*}*/

    }

    private fun createRandomTriangle(paint: Paint, canvas: Canvas?) {
        val minX = 10.0f
        val maxX = 50.0f
        val primero = Random.nextFloat() * (0 - 120) + 120
        val segundo = Random.nextFloat() * (490 - 150) + 150
        val tercero = Random.nextFloat() * (0 + 290) - 290
        val cuarto = Random.nextFloat() * (300 - 100) + 100
        //setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        val path1: Path = Path()
        path1.moveTo(0f,tercero)
        //path1.lineTo(primero, segundo)
        path1.lineTo(0f, segundo) // - Esquina abajo Izquierda
        //path1.lineTo(tercero, cuarto)
        path1.lineTo(primero, primero) //Largo base Triangulo - Esquina abajo derecha
        path1.close()
        paint.color = Color.WHITE
        paint.strokeWidth = 5f
        paint.style = Paint.Style.FILL
        canvas?.drawPath(path1, paint)
    }
    private fun createRandomTriangle1(paint: Paint, canvas: Canvas?) {
        val minX = 10.0f
        val maxX = 50.0f
        val primero = Random.nextFloat() * (200 - 100) + 100
        val segundo = Random.nextFloat() * (490 - 150) + 150
        val tercero = Random.nextFloat() * (360 + 50) - 50
        val cuarto = Random.nextFloat() * (300 - 100) + 100
        //setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        val path1: Path = Path()
        path1.moveTo(0f,primero)
        //path1.lineTo(primero, segundo)
        path1.lineTo(0f, 300f) // - Esquina abajo Izquierda
        //path1.lineTo(tercero, cuarto)
        path1.lineTo(tercero, 300f) //Largo base Triangulo - Esquina abajo derecha
        path1.close()
        paint.color = Color.WHITE
        paint.strokeWidth = 5f
        paint.style = Paint.Style.FILL
        canvas?.drawPath(path1, paint)
    }
    private fun createRandomTriangle2(paint: Paint, canvas: Canvas?) {
        val minX = 10.0f
        val maxX = 50.0f
        val primero = Random.nextFloat() * (150 - 0) + 0
        val segundo = Random.nextFloat() * (190 - 360) + 360
        val tercero = Random.nextFloat() * (360 + 50) - 50
        val cuarto = Random.nextFloat() * (300 - 100) + 100
        //setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        val path1: Path = Path()
        path1.moveTo(500f,segundo)
        //path1.lineTo(primero, segundo)
        path1.lineTo(primero, 300f) // - Esquina abajo Izquierda
        //path1.lineTo(tercero, cuarto)
        path1.lineTo(370f, 300f) //Largo base Triangulo - Esquina abajo derecha
        path1.close()
        paint.color = Color.WHITE
        paint.strokeWidth = 5f
        paint.style = Paint.Style.FILL
        canvas?.drawPath(path1, paint)
    }
}
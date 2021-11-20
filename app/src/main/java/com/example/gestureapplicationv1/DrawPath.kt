package com.example.gestureapplicationv1

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.GestureApplicationV1_test.CirclePosition
import kotlin.math.abs
import kotlin.math.roundToInt


class DrawPath @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr)
{
//    private var paint : Paint ?= null
//    init{
//        paint = Paint()
//        paint!!.color= Color.RED
//        paint!!.strokeWidth=10f
//        paint!!.style= Paint.Style.STROKE
//        paint!!.isAntiAlias= true
//    }
//
//    override fun onDraw(canvas:Canvas?){
//        canvas.drawPath()
//    }
//}

   // private var paint: Paint? = null
    private var path: Path? = null
    private var mPath: Path?= null
    private var pathList = ArrayList<paintPath>()
    private var undonePathList = ArrayList<paintPath>()
    private var mX:Float?=null
    private var mY:Float?=null
    private var touchTolerance:Float?=4f
    private var flag:Int=0
    private var xPos:Float=0f
    private var yPos:Float=0f

    private var maxX: Float = 0f
    private var maxY: Float = 0f
    private var minX: Float = 0f
    private var minY: Float = 0f

    var editModeOn: Boolean = false
    var selectedColorCode: String = "#E74C3C"

    private var fluctuatingPoint: Point? = null

    private var intialX: Float = 0f
    private var intialY: Float = 0f

   // private var circle: Unit? =null
   var circlepoints = ArrayList<CirclePosition>()

    //Initialisations fot paint
    init {
//        paint = Paint()
        path= Path()
//        paint!!.color = Color.parseColor(selectedColorCode)
//        paint!!.strokeWidth = 10f
//        paint!!.style = Paint.Style.STROKE
//        paint!!.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        print("\n")
        print("widthMeasureSpec -> ");
        print(widthMeasureSpec)
        print(", heightMeasureSpec")
        print(heightMeasureSpec)
        print("\n")
    }

    //private var mCustomImage: ImageView? = null

    //to daw the painting and shapes
    override fun onDraw(canvas: Canvas?) {
        // draw shape
        for(shape in circlepoints) {
            // check shape type
            if (shape.type == 1) {
                // draw circle
                canvas?.drawCircle(shape.x, shape.y + shape.radius, shape.radius, shape.paint)

                val imageBounds = canvas!!.clipBounds

                val mCustomImage = context.getResources().getDrawable(R.drawable.color_circle);
                mCustomImage.setBounds(shape.x.toInt(), (shape.y+shape.radius).toInt(), 100, 100)

                mCustomImage.setBounds(imageBounds)
               // mCustomImage?.draw(canvas)


               // drawShape((shape.radius*2).roundToInt(), (shape.radius*2).roundToInt(), shape)
            } else if (shape.type == 2) {
                // draw line
                canvas?.drawLine(shape.x, shape.y, shape.maxX, shape.maxY, shape.paint)
            } else if (shape.type == 3) {
                if(shape.minX ==shape.x){
                    //Working for a equi triangle Right to left
                    path?.moveTo(shape.x, shape.maxY)
                    path?.lineTo(shape.maxX, shape.y)

                    path?.lineTo(shape.minX, shape.minY)

                    path?.lineTo(shape.x, shape.maxY)

                    canvas?.drawPath(path!!,shape.paint)
                    path?.close()
                    path?.reset()
                }
                else if(shape.minY ==shape.y){
                    //Working for a equi triangle left to right
                    val secY=shape.maxY+shape.minY
                    path?.moveTo(shape.minX, shape.maxY)
                    path?.lineTo(shape.maxX, secY/2)

                    path?.lineTo(shape.minX, shape.minY)

                    path?.lineTo(shape.minX, shape.maxY)

                    canvas?.drawPath(path!!,shape.paint)
                    path?.close()
                    path?.reset()

                }
                else{
                    path?.moveTo(shape.minX, shape.minY)
                    path?.lineTo(shape.x, shape.y)

                    path?.lineTo(shape.maxX, shape.maxY)

                    path?.lineTo(shape.minX, shape.minY)

                    canvas?.drawPath(path!!,shape.paint)
                    path?.close()
                    path?.reset()
                }

            } else if (shape.type == 4) {
                // draw rectangle
                val rectangle = Rect(shape.x.toInt(), shape.y.toInt(), shape.maxX.toInt(), shape.maxY.toInt())

                canvas?.drawRect(rectangle, shape.paint)
            }

            invalidate()
        }


        // draw new lines

        val paint = Paint().apply {
            color = Color.parseColor(selectedColorCode)
            style = Paint.Style.STROKE
            strokeWidth = 10f
        }

        if(pathList.size>0) {
            for(path in pathList)
            {
                canvas!!.drawPath(path.path, paint!!)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (editModeOn) {

            // Delete Mode

            val xPos = event!!.x
            val yPos = event!!.y

            // start stracing from last
            circlepoints.reverse()

            for ( i in 0..(circlepoints.count()-1)) {

                val shape = circlepoints[i]

                //circle
                if (shape.type == 1) {
                    val itemX = shape.x - shape.radius
                    val itemMaxX = (itemX+ shape.radius*2)
                    val itemY = shape.y
                    val itemMaxY = (shape.y+ shape.radius*2)

                    if((itemX<= xPos &&  itemMaxX >= xPos) && ( itemY <= yPos && itemMaxY >= yPos)) {
                        circlepoints.remove(shape)
                        // move to original
                        circlepoints.reverse()
                        return false
                    }
                } else if (shape.type == 2) {
                    // line
                    val itemX = shape.minX
                    val itemMaxX = shape.maxX
                    val itemY = shape.minY
                    val itemMaxY = shape.maxY

                    if((itemX<= xPos && itemMaxX >= xPos) && (itemY <= yPos && itemMaxY >= yPos)) {
                        circlepoints.remove(shape)

                        // move to original
                        circlepoints.reverse()
                        return false
                    }

                } else if (shape.type == 3) {
                    // triangle

                } else if (shape.type == 4) {
                    // rectangular

                    val itemX = shape.minX
                    val itemMaxX = shape.maxX
                    val itemY = shape.minY
                    val itemMaxY = shape.maxY

                    if((itemX<= xPos && itemMaxX >= xPos) && (itemY <= yPos && itemMaxY >= yPos)) {
                        circlepoints.remove(shape)

                        // move to original
                        circlepoints.reverse()
                        return false
                    }

                }

            }

            return false
        }



        // Draw Mode

        xPos = event!!.x
        yPos = event!!.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(xPos,yPos)
                //path!!.moveTo(xPos, yPos)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                //touchMove(xPos,yPos)
                //path!!.lineTo(xPos,yPos)
                touchMove(xPos,yPos)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                //touchUp()
                touchUp()
                invalidate()

            }
            else -> {

            }
        }
        invalidate()
        return true
    }

    private fun touchStart(xPos: Float, yPos: Float) {

        mPath=Path()

        val paintPath= paintPath(mPath!!)
        pathList.add(paintPath)

        mPath!!.reset()
        mPath!!.moveTo(xPos,yPos)

        mX=xPos
        mY=yPos

        // store max x and max y values upto where user draws circle
        maxX = xPos
        maxY = yPos

        minX = xPos
        minY = yPos

        // store initial position from where drag started
        intialX = xPos
        intialY = yPos

    }

    private fun touchMove(xPos: Float, yPos: Float) {
        val dX:Float= abs(xPos-mX!!)
        val dY:Float= abs(xPos-mY!!)
        if(dX>= this!!.touchTolerance!! ||dY>= this!!.touchTolerance!!){
            mPath!!.quadTo(mX!!,mY!!,(xPos+mX!!)/2,(yPos+mY!!)/2)
            mX= xPos
            mY= yPos

            print("\n")
            // store max x value
            if (xPos > maxX) {
                maxX = xPos
                print("maxX -> ")
                print(maxX)
                print("\n")
            }
            // store max y value
            if (yPos > maxY) {
                maxY = yPos

                print("maxY -> ")
                print(maxY)
                print("\n")
            }
            // store minimum x value
            if (xPos < minX) {
                minX = xPos

                print("minX -> ")
                print(minX)
                print("\n")
            }
            // store minimum y value
            if (yPos < minY) {
                minY = yPos

                print("minY -> ")
                print(minY)
                print("\n")
            }
        }
    }

    private fun touchUp() {
        mPath!!.lineTo(mX!!,mY!!)
    }

    //For changing the Canvas on Button Click
    fun test(type: Int, isFilled: Boolean)
    {
        val size = pathList.size
        if(size>0){
            undonePathList.add(pathList[size-1])
            pathList.removeAt(size - 1)

            var paint = Paint()
            // set paint
            if (isFilled) {
                paint.setColor(Color.parseColor(selectedColorCode))
            } else {
                paint = Paint().apply {
                    color = Color.parseColor(selectedColorCode)
                    style = Paint.Style.STROKE
                    strokeWidth = 10f
                }
            }

            // store share dimension
            if (type == 1) {
                // get distance between start and end points
                val disX = maxX - intialX
                val disY = maxY - intialY

                // get radius out of max distance
                var radius: Float = 2f
                if (disX > disY) {
                    radius =  disX/2
                } else {
                    radius = disY/2
                }

                // for circle
                circlepoints.add(CirclePosition(x = intialX, y= intialY, radius= radius, isFilled = isFilled, paint = paint, type = type))

            } else if (type == 2) {
                // thick the line
                if (isFilled) {
                    paint.strokeWidth = 20f
                }
                // for line
                circlepoints.add(CirclePosition(x = intialX, y= intialY, maxX= xPos, maxY = yPos,  isFilled = isFilled, paint = paint, type = type))

            } else if (type == 3) {
                // for triangle
                circlepoints.add(CirclePosition(x = intialX, y = intialY, maxX= maxX, maxY = maxY, minX = minX, minY= minY,  isFilled = isFilled, paint = paint, type = type))

            } else if (type == 4) {
                // for rectangle
                circlepoints.add(CirclePosition(x = minX, y= minY, maxX= maxX, maxY = maxY,  isFilled = isFilled, paint = paint, type = type))
            }


            print("\n")
            print("----------------------------------")
            print("\n")
            print("max positions -> ")
            print(maxX)
            print(", ")
            print(maxY)
            print("\n")
            print("----------------------------------")
            print("\n")

            invalidate()
            //requestLayout()
        }
    }

    //To delete on button click
//    fun del()
//    {
////        path?.reset()
////        Canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
////        val size = pathList.size
////        if(size>0){
////            undonePathList.add(pathList[size-1])
////            pathList.removeAt(size - 1)
////            //flag=1
////            invalidate()
////            //requestLayout()
//
////        val intent = Intent(this@MainActivity,
////                MainActivity::class.java)
////
////        startActivity(intent)
//
//        Canvas.
//    }


//    fun touchAction(){
//        test = findViewById<ImageView>(R.id.Circle)
//
//        test?.setOnClickListener{
//
//
//        }
//    }
}
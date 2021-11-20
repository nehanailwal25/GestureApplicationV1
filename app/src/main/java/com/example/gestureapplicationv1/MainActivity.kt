package com.example.gestureapplicationv1

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.os.Bundle
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.view.MotionEvent
import android.widget.TextView
import androidx.core.view.marginLeft


class MainActivity : AppCompatActivity() {

    var colorView: View? = null
    var isOpen = false
    var paintarea: DrawPath? = null

    var circleFilled: ImageView? = null
    var circle: ImageView? = null
    var line: ImageView? = null
    var lineFilled: ImageView? = null
    var square: ImageView? = null
    var squareFilled: ImageView? = null
    var triangle: ImageView? = null
    var triangleFilled: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar: ActionBar?
        actionBar = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#E74C3C")) //6DA0E5
        actionBar?.setTitle("Assignment #2")
        // Set BackgroundDrawable
        actionBar?.setBackgroundDrawable(colorDrawable)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#E74C3C")))
        supportActionBar?.setDisplayShowTitleEnabled(true)


        circleFilled = findViewById<ImageView>(R.id.Circlefilled)
        circle = findViewById<ImageView>(R.id.Circle)
        line = findViewById<ImageView>(R.id.line)
        lineFilled = findViewById<ImageView>(R.id.linefilled)
        square = findViewById<ImageView>(R.id.Sqr)
        squareFilled = findViewById<ImageView>(R.id.Sqarefilled)
        triangle = findViewById<ImageView>(R.id.triangle)
        triangleFilled = findViewById<ImageView>(R.id.trianglefilled)
        paintarea = findViewById<DrawPath>(R.id.paintArea)


        var delete = findViewById<ImageView>(R.id.delete)

        delete.setBackgroundResource(R.drawable.delete_black)

        // colors view
        setColorPaletListners()

        setupColorPalet()

        setSelectedColor("#E74C3C")

        circleFilled?.setOnClickListener {
            paintarea?.test(type = 1, isFilled = true)
        }

        circle?.setOnClickListener {
            paintarea?.test(type = 1, isFilled = false)
        }

        lineFilled?.setOnClickListener {
            paintarea?.test(type = 2, isFilled = true)
        }

        line?.setOnClickListener {
            paintarea?.test(type = 2, isFilled = false)
        }

        triangleFilled?.setOnClickListener {
            paintarea?.test(type = 3, isFilled = true)
        }

        triangle?.setOnClickListener {
            paintarea?.test(type = 3, isFilled = false)
        }

        squareFilled?.setOnClickListener {
            paintarea?.test(type = 4, isFilled = true)
        }

        square?.setOnClickListener {
            paintarea?.test(type = 4, isFilled = false)
        }

        delete.setOnClickListener {

            if (paintarea == null) {
                return@setOnClickListener
            }

            // update edit mode
            paintarea?.editModeOn = !paintarea!!.editModeOn
            // update icon
            if (paintarea!!.editModeOn) {
                delete.setBackgroundResource(R.drawable.save_changes)
            } else {
                delete.setBackgroundResource(R.drawable.delete_black)
            }
        }

        findViewById<ImageView>(R.id.colorButton).setOnClickListener {
            showColorPalet(!isOpen)
        }
    }

    fun setColorPaletListners() {
        findViewById<ImageView>(R.id.cE74C3C).setOnClickListener {
            setSelectedColor("#E74C3C")
        }

        findViewById<ImageView>(R.id.c117A65).setOnClickListener {
            setSelectedColor("#117A65")
        }

        findViewById<ImageView>(R.id.c1ABC9C).setOnClickListener {
            setSelectedColor("#1ABC9C")
        }

        findViewById<ImageView>(R.id.c2980B9).setOnClickListener {
            setSelectedColor("#2980B9")
        }

        findViewById<ImageView>(R.id.c34495E).setOnClickListener {
            setSelectedColor("#34495E")
        }

        findViewById<ImageView>(R.id.c5DADE2).setOnClickListener {
            setSelectedColor("#5DADE2")
        }

        findViewById<ImageView>(R.id.c9B59B6).setOnClickListener {
            setSelectedColor("#9B59B6")
        }

        findViewById<ImageView>(R.id.cDC7633).setOnClickListener {
            setSelectedColor("#DC7633")
        }

        findViewById<ImageView>(R.id.cF39C12).setOnClickListener {
            setSelectedColor("#F39C12")
        }

        findViewById<ImageView>(R.id.cF4D03F).setOnClickListener {
            setSelectedColor("#F4D03F")
        }
    }

    fun setSelectedColor(colorHex: String) {
        if (paintarea == null) {
            return
        }
        paintarea!!.selectedColorCode = colorHex

        square?.setColorFilter(Color.parseColor(colorHex), PorterDuff.Mode.SRC_ATOP)
        squareFilled?.setColorFilter(Color.parseColor(colorHex), PorterDuff.Mode.SRC_ATOP)
        circle?.setColorFilter(Color.parseColor(colorHex), PorterDuff.Mode.SRC_ATOP)
        circleFilled?.setColorFilter(Color.parseColor(colorHex), PorterDuff.Mode.SRC_ATOP)
        triangle?.setColorFilter(Color.parseColor(colorHex), PorterDuff.Mode.SRC_ATOP)
        triangleFilled?.setColorFilter(Color.parseColor(colorHex), PorterDuff.Mode.SRC_ATOP)
        line?.setColorFilter(Color.parseColor(colorHex), PorterDuff.Mode.SRC_ATOP)
        lineFilled?.setColorFilter(Color.parseColor(colorHex), PorterDuff.Mode.SRC_ATOP)

        // hide color palet
        showColorPalet(false)
    }

    fun setupColorPalet() {
        colorView = findViewById(R.id.color_palet);
        // initialize as invisible (could also do in xml)
        colorView?.setVisibility(View.INVISIBLE);

        // hide color palet intial
        showColorPalet(false)
    }

    fun showColorPalet(flag: Boolean) {
        // validate view
        if (colorView == null) {
            return
        }
        // check current position
        if (flag) {
            if (!isOpen) {
                slideRight(colorView!!)
            }
        } else {
            if (isOpen) {
                slideLeft(colorView!!)
            }
        }
        // update flag
        isOpen = flag
    }

    /// slide the view from hidden/left to the right/visible
    fun slideRight(view: View) {
        view.setVisibility(View.VISIBLE)
        val animate = TranslateAnimation(
                -view.width.toFloat(),  // fromXDelta
                0f,  // toXDelta
                0f,  // fromYDelta
                0f) // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    /// slide the view from right/visible to hidden/left
    fun slideLeft(view: View) {
        val animate = TranslateAnimation(
                0f,  // fromXDelta
                -view.width.toFloat(),  // toXDelta
                0f,  // fromYDelta
                0f) // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }
    


//    private fun drawShape(width: Int, height: Int, shape: CirclePosition) {
//
//        val bitmap = Bitmap.createBitmap(
//                width,  // Width
//                height,  // Height
//                Bitmap.Config.ARGB_8888 // Config
//        )
//        val canvas = Canvas(bitmap)
////        val paint = Paint()
////        paint.style = Paint.Style.FILL
////        paint.color = Color.RED
////        paint.isAntiAlias = true
////        val radius = Math.min(canvas.width, canvas.height / 2)
////        val padding = 5
////        canvas.drawCircle(
////                (canvas.width / 2).toFloat(),  // cx
////                (canvas.height / 2).toFloat(),  // cy
////                (radius - padding).toFloat(),  // Radius
////                paint // Paint
////        )
//
//        addViews(width, height, bitmap, shape)
//    }


//    private fun addViews(width: Int, height: Int, bitmap: Bitmap, shape: CirclePosition) {
////        val min = 20
////        val max = 80
//        val d = getResources().getDrawable(R.mipmap.ic_launcher_round)
////        val w = d.getIntrinsicWidth()
////        val random = Random().nextInt((max - min) + 1) + min
//
//
//        val imageView = ImageView(this)
//
//        val params = RelativeLayout.LayoutParams(width, height)
//        //params.setMargins(Random().nextInt((width - 0) + 1), Random().nextInt((height - 0) + 1), 10, 10)
//
//        imageView.setLayoutParams(params)
//        // Display the newly created bitmap on app interface
//        imageView.setImageBitmap(bitmap);
//
//        imageView.setBackgroundColor(Color.red(1))
//
////      val finalImageView: ImageView? = imageView
////        imageView.setOnClickListener(OnClickListener {
////
////       })
//
//
//        val relative4 = findViewById<RelativeLayout>(R.id.paintRelativeArea)
//       // val width = getMeasuredWidth()
//       // val height = getMeasuredHeight()
//        if (imageView == null)
//        {
//            val imageView = ImageView(this) //  ImageView(this)
//        }
////        val params = RelativeLayout.LayoutParams(100, 100)
////        params.setMargins(Random().nextInt((width - 0) + 1), Random().nextInt((height - 0) + 1), 10, 10)
////        imageView.setLayoutParams(params)
////        imageView.setImageBitmap(bitmap)
////        if (imageView != null)
////        {
////            val parent = imageView.getParent() as ViewGroup
////            if (parent != null)
////            {
////                parent.removeView(imageView)
////            }
////        }
//        relative4.addView(imageView)
//        // val finalImageView = imageView
//
//        imageView.setOnClickListener {
//
//            print("delete pressed")
//
//        }
//
////        imageView.setOnClickListener(object:View.OnClickListener() {
////
////            onC
////
////            fun onClick(view:View) {
////                when (value) {
////                    1 -> {
////                        drawCircle(finalImageView)
//////                        mSuareCount--
//////                        mCircleCount++
////                    }
////                    2 -> {
////                        drawTriangle(finalImageView)
//////                        mCircleCount--
//////                        mTriangelCount++
////                    }
////                    3 -> {
////                        drawSquare(finalImageView)
//////                        mTriangelCount--
//////                        mSuareCount++
////                    }
////                }
////            }
////        })
////        imageView.setOnLongClickListener(object:View.OnLongClickListener() {
////            fun onLongClick(v:View):Boolean {
////                when (value) {
////                    1 -> {
////                        relative4.removeView(finalImageView)
////                        mSquareCount--
////                    }
////                    2 -> {
////                        relative4.removeView(finalImageView)
////                        mCircleCount--
////                    }
////                    3 -> {
////                        relative4.removeView(finalImageView)
////                        mTriangleCount--
////                    }
////                }
////                return true
////            }
////        })
//    }


}

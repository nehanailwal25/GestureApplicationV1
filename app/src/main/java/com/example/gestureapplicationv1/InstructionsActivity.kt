package com.example.gestureapplicationv1

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBar

class InstructionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions)

//        var instructions= findViewById<TextView>(R.id.instructions)
//        var startButton= findViewById<Button>(R.id.startButton)
//        //Scroll bar in instructions
//        instructions.movementMethod= ScrollingMovementMethod.getInstance()


        val actionBar: ActionBar?
        actionBar = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#E74C3C")) //6DA0E5
        actionBar?.setTitle("INSTRUCTIONS")
        // Set BackgroundDrawable
        actionBar?.setBackgroundDrawable(colorDrawable)


        findViewById<Button>(R.id.startButton).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
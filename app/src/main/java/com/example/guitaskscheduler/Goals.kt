package com.example.guitaskscheduler
import com.example.guitaskscheduler.Objects
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Goals : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_goals)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var mainreceived = intent.getParcelableExtra<Objects.Display>("mainDisplay")


        val goal_name = findViewById<TextView>(R.id.goalname)
        val goal_description = findViewById<TextView>(R.id.goalname)
        val goal_date = findViewById<CalendarView>(R.id.calendarView)
        val goal_btn = findViewById<Button>(R.id.submit_goal)
        var myDate = Objects.Date(1,0 + 1,2000)
        goal_date.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // month is 0-based, so add 1
            myDate = Objects.Date(dayOfMonth,month + 1,year)
        }

        goal_btn.setOnClickListener{



            if (mainreceived != null){
                val myTask = Objects.Task(goal_name.text.toString(),goal_description.text.toString(),myDate)
                mainreceived?.addtask(myTask)


            }
            val gotomain = Intent(this, MainActivity::class.java)
            gotomain.putExtra("mainDisplay", mainreceived)

            startActivity(gotomain)

        }
    }
}
package com.example.guitaskscheduler
import android.os.Parcelable
import java.time.LocalDate
import java.time.Period
import java.io.File
import kotlinx.parcelize.Parcelize




class Objects
{




    @Parcelize
    class Display(var tasks: MutableList<Task> = mutableListOf()): Parcelable
    {




        fun addtask(task: Task)
        {
            tasks.add(task)

        }


    }


    @Parcelize
    class Task(val name: String, val description: String, val date: Date, var done: Boolean = false): Parcelable
    {

        fun formatted(): String {
            return "$name - ${date.dateFormatted()}"
        }


    }
    @Parcelize
    class Date(val day: Int, val month: Int, val year: Int) : Parcelable
    {

        fun dateFormatted(): String{
            return "${month}/${day}/${year}"
        }
    }
}
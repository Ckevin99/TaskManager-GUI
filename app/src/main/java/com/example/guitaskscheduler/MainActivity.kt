package com.example.guitaskscheduler
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson;



@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val gson = Gson()
        var mainDisplay = Objects.Display()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
        val mPrefs = getPreferences(MODE_PRIVATE)
        val json = mPrefs.getString("mainDisplay", "")
        if (json != null){
            mainDisplay = gson.fromJson(json, Objects.Display::class.java)
        }





        var received = intent.getParcelableExtra<Objects.Display>("mainDisplay")


        val changetogoals = findViewById<Button>(R.id.button2)
        val result = findViewById<TextView>(R.id.textView)



        val listView = findViewById<ListView>(R.id.list)
        if (received != null)
        {
            mainDisplay = received
//            result.text = mainDisplay.tasks.size.toString()
            save(gson, mainDisplay)





        }
        val listAdapter = CustomAdapter(this, mainDisplay.tasks, mainDisplay, mPrefs)
        listView.adapter = listAdapter
        changetogoals.setOnClickListener {
            val gotogoals = Intent(this, Goals::class.java)
            gotogoals.putExtra("mainDisplay", mainDisplay)
            startActivity(gotogoals)

        }


    }
    fun save(gson: Gson, mainDisplay: Objects.Display){
        val save = gson.toJson(mainDisplay)
        val mPrefs = getPreferences(MODE_PRIVATE)
        val prefsEditor = mPrefs.edit()
        prefsEditor.putString("mainDisplay", save)
        prefsEditor.apply()
    }

    class CustomAdapter(var context: Context, private var itemList: List<Objects.Task>, var mainDisplay: Objects.Display, var mPrefs: SharedPreferences) : BaseAdapter(){

        override fun getCount(): Int = itemList.size

        override fun getItem(position: Int): Any = itemList[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.listlayout, parent, false)
            val checkBox: CheckBox = view.findViewById(R.id.checkBox)
            val textView: TextView = view.findViewById(R.id.textViewlist)
            val delbutton: Button = view.findViewById(R.id.buttonDel)

            val item = itemList[position]
            checkBox.isChecked = item.done
            textView.text = item.formatted()

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                item.done = isChecked
                save(mainDisplay, mPrefs)
            }
            delbutton.setOnClickListener{
                mainDisplay.tasks.removeAt(position)
                notifyDataSetChanged()
                save(mainDisplay, mPrefs)
            }

            return view
        }

        private fun save(mainDisplay: Objects.Display,mPrefs : SharedPreferences) {
            val gson = Gson()
            val save = gson.toJson(mainDisplay)
            val prefsEditor = mPrefs.edit()
            prefsEditor.putString("mainDisplay", save)
            prefsEditor.apply()
        }
    }

}
package com.example.androidtest

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.androidtest.SuccessActivity
import com.example.androidtest.data.User
import com.example.androidtest.data.UserViewModel
import java.time.LocalTime
import java.util.Locale

class MainActivity : AppCompatActivity() {
    var press = 0
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Toast.makeText(applicationContext, "onCreate", Toast.LENGTH_SHORT).show()
        //Log.i("MyLog", "onCreate")

        val preferences = getPreferences(MODE_PRIVATE)
        counter = preferences.getInt("counter", 0)

        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt("counter", 0)
        }
        findViewById<TextView>(R.id.textViewCounter).text = counter.toString()

        val buttonUp = findViewById<Button>(R.id.buttonUp)
        val ime = findViewById<EditText>(R.id.plainTextName).text.toString()
        buttonUp.setOnClickListener{
            onButtonUp()
            if(counter == 10){
                counter ++
                val intent = Intent(this, SuccessActivity::class.java)
                intent.putExtra("ime", ime)
                startActivity(intent)
                counter = 0
            }
        }
        setSupportActionBar(findViewById(R.id.my_toolbar))

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {



        when (item.itemId) {
            R.id.restore_counter -> {
                counter = 0;
                findViewById<TextView>(R.id.textViewCounter).text = counter.toString()
                true
            }
            R.id.croatian-> {
                changeLanguage(this, "hr")
                recreate()
                true
            }
            R.id.english-> {
                changeLanguage(this, "en")
                recreate()
                true
            }
            R.id.analytics ->{
                val intent = Intent(this, AnalyticsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDatabase() {
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val name = findViewById<TextView>(R.id.plainTextName).text.toString()
        val time = LocalTime.now()
        val timeString = time.toString() // this stores the value of time
        if(TextUtils.isEmpty(name)){
            Log.e("Database", "User didn't enter its name")
            Toast.makeText(applicationContext, "fmksfse", Toast.LENGTH_SHORT).show()
        } else {
            if (press < 10) {
                Log.i("press","Pressed $press")
            } else {
                press -= 10
                val user = User(0, name, timeString, counter)
                mUserViewModel.addUser(user)
            }
        }
    }



    fun onButtonUp() {
        counter++
        findViewById<TextView>(R.id.textViewCounter).text = counter.toString()
        insertDataToDatabase()
        val ime = findViewById<EditText>(R.id.plainTextName).text.toString()
        if(counter == 10){
            val intent = Intent(this, SuccessActivity::class.java)
            intent.putExtra("ime", ime)
            startActivity(intent)
            counter = 0
            findViewById<TextView>(R.id.textViewCounter).text = counter.toString()
        }
    }
    override fun onStart() {
        super.onStart()
        //Toast.makeText(applicationContext, "onStart", Toast.LENGTH_SHORT).show()
        //Log.v("MyLog", "onStart")
    }
    override fun onResume() {
        super.onResume()
        //Toast.makeText(applicationContext, "onResume", Toast.LENGTH_SHORT).show()
        //Log.d("MyLog", "onResume")

    }
    override fun onPause() {
        super.onPause()
        //Toast.makeText(applicationContext, "onPause", Toast.LENGTH_SHORT).show()
        //Log.w("MyLog", "onPause")

        val preferences = getPreferences(MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putInt("counter", counter)
        editor.apply()
    }
    override fun onStop() {
        super.onStop()
        //Toast.makeText(applicationContext, "onStop", Toast.LENGTH_SHORT).show()
        //Log.e("MyLog", "onStop")
    }
    override fun onDestroy() {
        super.onDestroy()
        //Toast.makeText(applicationContext, "onDestroy", Toast.LENGTH_SHORT).show()
        //Log.i("MyLog", "onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("counter", counter)

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        counter = savedInstanceState.getInt("counter", 0)

        findViewById<TextView>(R.id.textViewCounter).text = counter.toString()
    }

    var counter = 0
    fun onButtonDown(view: View) {
        if(counter > 0)
            counter --
        findViewById<TextView>(R.id.textViewCounter).text = counter.toString()

    }
    fun onButtonUp(view: View) {
        counter ++
        findViewById<TextView>(R.id.textViewCounter).text = counter.toString()
    }

    fun onReset(view: View){
        counter = 0;
        findViewById<TextView>(R.id.textViewCounter).text = counter.toString()
    }

    @Suppress("DEPRECATION")
    fun changeLanguage(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res = context.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)
        context.createConfigurationContext(config)
        res.updateConfiguration(config, res.displayMetrics)
    }
}

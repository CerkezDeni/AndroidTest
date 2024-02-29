package com.example.androidtest

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.Locale

class MainActivity : AppCompatActivity() {

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
                val intent = Intent(this, SuccesActivity::class.java)
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
        }
        return super.onOptionsItemSelected(item)
    }
    fun onButtonUp() {
        counter++
        findViewById<TextView>(R.id.textViewCounter).text = counter.toString()

        val ime = findViewById<EditText>(R.id.plainTextName).text.toString()
        if(counter == 10){
            val intent = Intent(this, SuccesActivity::class.java)
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

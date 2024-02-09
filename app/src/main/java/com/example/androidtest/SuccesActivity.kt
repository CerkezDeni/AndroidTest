package com.example.androidtest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class SuccesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_succes)

        val ime = intent.getStringExtra("ime")

        val message1 = "$ime uspješno ste došli do 10 koraka."
        Toast.makeText(this, message1, Toast.LENGTH_LONG).show()

        val SMSbutton = findViewById<Button>(R.id.SMSbutton)

        SMSbutton.setOnClickListener{
            val phoneNumber = "0911209205"

            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("smsto:$phoneNumber")
            intent.putExtra("sms_body", "Nigger")
            startActivity(intent)
        }

    }

}
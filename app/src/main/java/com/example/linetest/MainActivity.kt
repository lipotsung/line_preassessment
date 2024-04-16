package com.example.linetest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        var btn_send:Button=findViewById<Button> (R.id.btn_send)
        var btn_getdata:Button=findViewById<Button> (R.id.btn_getdata)

        database = FirebaseDatabase.getInstance();
        //val database = FirebaseDatabase.getInstance("https://myfirstfirebase-a6715-default-rtdb.firebaseio.com/")
        reference = database.getReference("Users")
        btn_send.setOnClickListener {
            sendData()
            startActivity(Intent(applicationContext,Getdata::class.java))
        }
        btn_getdata.setOnClickListener {
            startActivity(Intent(applicationContext,Getdata::class.java))
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
    }
    private fun sendData()
    {
        var et_name:EditText=findViewById(R.id.et_name)
        var et_email: EditText =findViewById(R.id.et_email)
        var name = et_name.text.toString().trim()
        var email = et_email.text.toString().trim()
        if(name.isNotEmpty()&& email.isNotEmpty())
        {
            var model= DatabaseModel(name,email)
            var id = reference.push().key
            //Here we send the data to firebase
            reference.child(id!!).setValue(model)
            et_name.setText("")
            et_email.setText("")
        }
        else{
            Toast.makeText(applicationContext,"All Field Required",Toast.LENGTH_LONG).show()
        }
    }

}
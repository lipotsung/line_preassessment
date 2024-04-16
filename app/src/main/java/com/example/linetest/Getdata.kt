package com.example.linetest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.DataAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Getdata : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_getdata)
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Users")
        sendData()
        getdata()
        var send: Button =findViewById<Button> (R.id.send)
        send.setOnClickListener {
            sendData()
            startActivity(Intent(applicationContext,Getdata::class.java))
        }

        var back: Button =findViewById<Button> (R.id.back)
        back.setOnClickListener {
            startActivity(Intent(applicationContext,MainActivity::class.java))
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun sendData()
    {
        var et_name2: EditText =findViewById(R.id.et_name2)
        var et_email2: EditText =findViewById(R.id.et_email2)
        var name2 = et_name2.text.toString().trim()
        var email2 = et_email2.text.toString().trim()
        if(name2.isNotEmpty()&& email2.isNotEmpty())
        {
            var model= DatabaseModel(name2,email2)
            var id = reference.push().key
            //Here we send the data to firebase
            reference.child(id!!).setValue(model)
            et_name2.setText("")
            et_email2.setText("")
        }
        else{
        }
    }

    private fun getdata(){
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(DataSnapshot: DataSnapshot) {
                val list= ArrayList<DatabaseModel>()
                for (data in DataSnapshot.children)
                {
                    val model = data.getValue(DatabaseModel::class.java)
                    list.add(model as DatabaseModel)
                }
                if(list.size>0)
                {
                    val adapter= DataAdapter(list)
                    val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                    //recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                    recyclerView.adapter = adapter
                }

            }
            override fun onCancelled(DataSnapshot: DatabaseError) {
                Log.e("Cancel",DataSnapshot.toString())
            }


        })
    }
}
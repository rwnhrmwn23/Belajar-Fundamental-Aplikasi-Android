package com.onedev.dicoding.myintentapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.onedev.dicoding.myintentapp.R
import com.onedev.dicoding.myintentapp.model.Person

class MoveWithObjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move_with_object)

        val tvObject: TextView = findViewById(R.id.tv_object_received)

        val person = intent.getParcelableExtra<Person>(EXTRA_PERSON)
        val text = "Name : ${person?.name},\n" +
                "Age : ${person?.age},\n" +
                "Email : ${person?.email},\n" +
                "City : ${person?.city},"

        tvObject.text = text
    }

    companion object {
        const val EXTRA_PERSON = "extra_person"
    }
}
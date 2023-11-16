package com.example.assurance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        btnLogin.setOnClickListener{
            val intent= Intent(this,AuthentificationActivity::class.java)
            startActivity(intent)
        }
        questions.setOnClickListener{
            val intent= Intent(this,QuestionsAssuranceActivity ::class.java)
            startActivity(intent)
        }


    }
}
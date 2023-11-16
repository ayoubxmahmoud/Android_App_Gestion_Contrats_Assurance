package com.example.assurance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_accueil_admin.*
import kotlinx.android.synthetic.main.row.*

class AccueilAdmin : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil_admin)


        Profil.setOnClickListener(this)
        lesContrats.setOnClickListener(this)
        Deconnexion.setOnClickListener(this)
        Dashboard.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.Profil -> {
                val email:String? = intent.getStringExtra("email_agent")

                val intent = Intent(applicationContext, ProfilAdmin::class.java)
                intent.putExtra("email_agent", email)
                startActivity(intent)
            }
            R.id.Deconnexion -> {
                Toast.makeText(this, "Déconnexion en cours", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, AuthentificationActivity::class.java)
                startActivity(intent)
            }
            R.id.lesContrats->{
                val intent = Intent(applicationContext, GestionDeContrats::class.java)
                startActivity(intent)
            }
            R.id.Dashboard->{
                val intent = Intent(applicationContext, DashboardAgent::class.java)
                startActivity(intent)
            }

        }

    }
}
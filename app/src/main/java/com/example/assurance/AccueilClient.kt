package com.example.assurance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_accueil_client.*


class AccueilClient : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil_client)

        profilclient.setOnClickListener(this)
        contrats.setOnClickListener(this)
        Deconnexion.setOnClickListener(this)
        dashboard_client.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.profilclient -> {
                // If the user chooses the profile, redirect to the profile with a key containing the id
                val cs: String? = intent.getStringExtra("cin")
                val ns: String? = intent.getStringExtra("nom")
                val ps: String? = intent.getStringExtra("prenom")
                val ads: String? = intent.getStringExtra("adresse")
                val ts: String? = intent.getStringExtra("tel")
                val es: String? = intent.getStringExtra("email")

                val ls: String? = intent.getStringExtra("login") //récupération de login email

                val intent = Intent(this, ProfilClient::class.java)

                intent.putExtra("login1", ls) //Envoi de login email vers l'activité suivante
                intent.putExtra("cin1", cs)
                intent.putExtra("adresse1", ads)
                intent.putExtra("email1", es)
                intent.putExtra("nom1", ns)
                intent.putExtra("prenom1", ps)
                intent.putExtra("tel1", ts)
                startActivity(intent) // Start the next activity
            }

            R.id.Deconnexion -> {
                Toast.makeText(this, "Déconnexion en cours", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
            }
            R.id.contrats ->{
                val intent = Intent(applicationContext,ContratActivity::class.java)
                startActivity(intent)
            }
            R.id.dashboard_client ->{
                val cs: String? = intent.getStringExtra("cin")
                val ls: String? = intent.getStringExtra("login") //récupération de login email
                val intent = Intent(this,DashboardClient::class.java)
                intent.putExtra("cin1", cs)
                intent.putExtra("login1", ls) //Envoi de login email vers l'activité suivante
                startActivity(intent)
            }

        }
    }

    override fun onBackPressed() {
        // Disable back button
    }
}

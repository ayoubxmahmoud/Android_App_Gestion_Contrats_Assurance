package com.example.assurance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_profil_admin.*
import kotlinx.android.synthetic.main.activity_profil_client.*
import kotlinx.android.synthetic.main.activity_profil_client.profil_adresse
import kotlinx.android.synthetic.main.activity_profil_client.profil_email
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.text.SimpleDateFormat
import java.util.*

class ProfilAdmin : AppCompatActivity() {
    private lateinit var db: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_admin)
        db = DataBaseHelper()
        val email = intent.getStringExtra("email_agent") //récupération de login email

        // Get the client's information from the database
        db.showDataAgent(email.toString()) { listinf ->
            // Set the fields in the "activity_profil_client" view with the retrieved data
            agent_id.text = listinf[0]
            agent_nom.text = listinf[1]
            agent_prénom.text = listinf[2]
            agent_adresse.text = listinf[3]
            agent_mobile.text = listinf[4]
            agent_email.text = listinf[5]
        }


    }
}
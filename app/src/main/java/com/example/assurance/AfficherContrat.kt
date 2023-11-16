package com.example.assurance

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_display_contract.*

class AfficherContrat:AppCompatActivity() {
    private lateinit var db: DataBaseHelper
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_contract)

        db = DataBaseHelper()
        val cin:String? = intent.getStringExtra("cin")
        val branche:String? = intent.getStringExtra("branche")

        db.getClient(cin.toString()){ client->
            nomC.text = client[1]
            prenomC.text = client[2]
            addrC.text = client[3]
        }

        db.getAgent { agent ->
            nomA.text = agent[1]
            prenomA.text = agent[2]
            addrA.text = agent[3]
        }

        db.getContrat(cin.toString(),branche.toString()){ contrat ->
            numContrat_out.text = contrat[0]
            date_debut.text = contrat[4]
            date_fin.text = contrat[5]
            valeur_assure_out.text = contrat[6]
        }
        returnBtn.setOnClickListener{
            val intent = Intent(this, ContratActivity::class.java)
            startActivity(intent)
        }




    }

}

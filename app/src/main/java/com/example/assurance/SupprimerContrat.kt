package com.example.assurance

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_delete_contract.*

class SupprimerContrat:AppCompatActivity() {
    private lateinit var db: DataBaseHelper
    //private lateinit var notification:NotificationByEmail
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_contract)

        db = DataBaseHelper()
        //notification = NotificationByEmail()
        cin0_out.text = intent.getStringExtra("cin")
        numC_out.text = intent.getStringExtra("numContrat")
        branche_out.text = intent.getStringExtra("branche")
        var clientEmail:String = ""
        db.getClient(cin0_out.text.toString()){ infos ->
            clientEmail = infos[4]
        }
        deleteBtn.setOnClickListener{
            val builder = AlertDialog.Builder(this)

            builder.setMessage("Voulez-vous vraiment supprimer le contrat ?")
                .setTitle("Supprimer le contrat")
            builder.setPositiveButton("Oui"){ _, _ ->
                db.deleteContrat(cin0_out.text.toString(),branche_out.text.toString())
                //notification.sendNotificationEmail(clientEmail,"La suppression de contrat d'assurance","Votre contrat d'assurance a été supprimé avec succès")
                Toast.makeText(this, "votre contrat à été supprimer avec succès", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ContratActivity::class.java)
                startActivity(intent)
            }

            builder.setNegativeButton("Non"){ _, _ ->
                val intent = Intent(this, ContratActivity::class.java)
                startActivity(intent)
            }
            // Show the dialog to the user
            val dialog = builder.create()
            dialog.show()

        }

    }

}

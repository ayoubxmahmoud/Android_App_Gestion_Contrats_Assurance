package com.example.assurance


import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.assurance.DataBaseHelper
import java.text.SimpleDateFormat
import kotlinx.android.synthetic.main.activity_profil_client.*
import java.util.*

class ProfilClient : AppCompatActivity() {
    private  lateinit var db: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_client)
        db= DataBaseHelper()

        val ls:String? = intent.getStringExtra("login1")

        if(ls != null && ls.isNotEmpty()) {
            handleViewingLogin(ls)
        }else{
            handleViewingRegister()
        }
    }

    private fun handleViewingLogin(email: String) {
        // Get the current date
        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val currentDate = simpleDateFormat.format(Date())

        // Get the client's information from the database
        db.showDataClient(email) { listinf ->
            // Set the fields in the "activity_profil_client" view with the retrieved data
            client_id.text = listinf[0]
            profil_nom.text = listinf[1]
            profil_prénom.text = listinf[2]
            profil_adresse.text = listinf[3]
            profil_tel.text = listinf[4]
            profil_email.text = listinf[5]
            profil_date.text = currentDate
        }
    }

    private fun handleViewingRegister() {
        val es:String? = intent.getStringExtra("email1")
        val ns:String? = intent.getStringExtra("nom1")//Récupération de l'id
        val ps:String? = intent.getStringExtra("prenom1")
        val cs:String? = intent.getStringExtra("cin1")
        val ads:String? = intent.getStringExtra("adresse1")
        val ts:String? = intent.getStringExtra("tel1")

        //Appel de la date en cours
        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date = simpleDateFormat.format(Date())


        //On fait rentrer les champs de la vue "activity_profil_client" contenant les infos de la BD
        client_id.text = cs
        profil_nom.text = ns
        profil_prénom.text = ps
        profil_email.text = es
        profil_tel.text = ts
        profil_adresse.text = ads
        profil_date.text = date

    }

}
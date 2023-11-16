package com.example.assurance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    private lateinit var handler: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        handler = DataBaseHelper()

        /**definition du bouton de direction vers login interface"activity_sign_in"**/
        register_login.setOnClickListener{
            val intent= Intent(this,AuthentificationActivity::class.java)
            startActivity(intent)

        }
        /**definition du bouton d'inscription**/
        btnRegister.setOnClickListener {
            if (validate()) {
                //Insertion des données du client à la base de données dans la table 'client' avec la fonction 'insertClient'

                handler.insertClient(cin.text.toString(),nom.text.toString(), prenom.text.toString(), adresseRegister.text.toString(), telC.text.toString(), emailRegister.text.toString(), mdpRegister.text.toString())
                val intent= Intent(this,AccueilClient::class.java)
                intent.putExtra("cin", cin.text.toString())
                intent.putExtra("adresse", adresseRegister.text.toString())
                intent.putExtra("email", emailRegister.text.toString())
                intent.putExtra("nom", nom.text.toString())
                intent.putExtra("prenom", prenom.text.toString())
                intent.putExtra("tel", telC.text.toString())

                startActivity(intent)
                clearEditText()
            }
        }
    }
    //fonction de validation des infos d'un client
    private fun validate():Boolean{
        if(nom.text.toString().trim().isEmpty()){
            nom.error = "Veuillez entrer le nom"
            return false
        }
        if(prenom.text.toString().trim().isEmpty()){
            prenom.error = "Veuillez entrer le prénom"
            return false
        }
        if(cin.text.toString().trim().isEmpty()){
            cin.error = "Veuillez entrer votre cin"
            return false
        }
        if(adresseRegister.text.toString().trim().isEmpty()){
            adresseRegister.error = "Veuillez entrer l'adresse"
            return false
        }
        if(emailRegister.text.toString().trim().isEmpty()){
            emailRegister.error = "Veuillez entrer l'e-mail"
            return false
        }
        if(mdpRegister.text.toString().trim().isEmpty()){
            mdpCRegister.error = "Veuillez entrer le mot de passe"
            return false
        }
        if(mdpCRegister.text.toString().trim().isEmpty()){
            mdpCRegister.error = "Veuillez rentrer le mot de passe"
            return false
        }
        if(telC.text.toString().trim().isEmpty()){
            telC.error = "Veuillez entrer le numéro de téléphone"
            return false
        }
        if(!isValidEmail(emailRegister.text.toString().trim())){
            emailRegister.error = "Veuillez entrer un email valide"
            return false
        }
        if(mdpRegister.text.toString().trim().count()<8){
            mdpRegister.error = "Veuillez entrer au moins 8 caractères"
            return false
        }
        if(mdpCRegister.text.toString().trim().count()<8){
            mdpCRegister.error = "Veuillez entrer au moins 8 caractères"
            return false
        }
        if(mdpCRegister.text.toString().trim() != mdpRegister.text.toString().trim() ){
            mdpCRegister.error = "Veuillez entrer deux mots de passe identiques"
            return false
        }
        if(telC.text.toString().trim().count()!=10 ){
            telC.error = "Veuillez entrer un numéro de téléphone valide: 06XX"
            return false
        }
        if(!agreement_checkbox.isChecked){
            Toast.makeText(this, "Veuillez accepter les termes et conditions", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun isValidEmail(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

    private fun clearEditText() {
        nom.setText("")
        prenom.setText("")
        cin.setText("")
        adresseRegister.setText("")
        emailRegister.setText("")
        mdpRegister.setText("")
        mdpCRegister.setText("")
        telC.setText("")
    }



}


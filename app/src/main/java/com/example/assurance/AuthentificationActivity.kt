package com.example.assurance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sign_in.*
import android.os.Bundle
import android.widget.Toast
class AuthentificationActivity : AppCompatActivity() {
    private lateinit var db: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        db= DataBaseHelper()

        btnLogin.setOnClickListener {
            if (validate()){
                //pour admin
                if (emailLogin.text.toString() == "admin@gmail.com" && mdpLogin.text.toString() == "admin") {
                    val intent = Intent(this, AccueilAdmin::class.java)
                    intent.putExtra("email_agent", emailLogin.text.toString())
                    startActivity(intent)
                    clearEditText()
                }
                else {
                    //pour client
                    //vérification des données de client
                    db.checkClientInfo(emailLogin.text.toString(), mdpLogin.text.toString()) { isValid->
                        if (isValid) {
                            val intent = Intent(this, AccueilClient::class.java)
                            intent.putExtra("login", emailLogin.text.toString())
                            startActivity(intent)
                            clearEditText()
                        }
                        else {
                            Toast.makeText(this, "Email ou mot de passe est incorrecte", Toast.LENGTH_SHORT).show()
                        }
                    }


                }
            }
        }


        loginRegister.setOnClickListener{
            val intent= Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    //fonction de validation des champs de login
    private fun validate():Boolean{
        if(emailLogin.text.toString().trim().isEmpty()){
            emailLogin.error = "Veuillez entrer l'e-mail ou le numéro de téléphone"
            return false
        }
        if(mdpLogin.text.toString().trim().isEmpty()){
            mdpLogin.error = "Veuillez entrer le mot de passe"
            return false
        }
        return true
    }
    private fun clearEditText() {
        emailLogin.setText("")
        mdpLogin.setText("")
    }

}

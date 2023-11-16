package com.example.assurance

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_modify_contract.*
import kotlinx.android.synthetic.main.activity_modify_contract.spinner_type

class ModifierContrat : AppCompatActivity(){
    private lateinit var db: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_contract)
        db = DataBaseHelper()

        val cin0: String? = intent.getStringExtra("numCIN")
        val branche0: String? = intent.getStringExtra("branche")

        //numCIN.setText(cin)

        modifyBtn.setOnClickListener{
            val branche:String = spinner_type.selectedItem.toString()
            if(validate()){
                if ((cin0 != null) && cin0.isNotEmpty()) {
                    if ((branche0 != null) && branche0.isNotEmpty()) {
                        db.deleteContrat(cin0,branche0)
                    }
                }
                else{
                    Toast.makeText(this, "cin null", Toast.LENGTH_SHORT).show()
                }
                //vérification de client
                db.checkNumClient(numCIN.text.toString()) { isValid->
                    if (isValid) {
                        db.insertContratTemporaire(numCIN.text.toString(),branche)
                        Toast.makeText(this, "nous avons envoyé un message à votre email", Toast.LENGTH_SHORT).show()
                        clearEditText()
                        val intent = Intent(this, ContratActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(this, "nous n'avons pas un compte avec ce numéro de CIN", Toast.LENGTH_SHORT).show()
                    }

                }

            }

        }

    }



    //fonction de validation des champs de activity_add
    private fun validate():Boolean{
        if(numCIN.text.toString().trim().isEmpty()){
            numCIN.error = "Veuillez entrer le numéro de CIN"
            return false
        }
        if (spinner_type.selectedItem.equals("branche")){
            Toast.makeText(this, "choisir un branche", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
    private fun clearEditText(){
        numCIN.setText("")
        spinner_type.requestFocus()
    }
}
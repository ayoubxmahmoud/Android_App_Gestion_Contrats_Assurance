package com.example.assurance

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_contract_by_client.*

class AddContractByClient : AppCompatActivity(){
    private lateinit var db: DataBaseHelper
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contract_by_client)

        db = DataBaseHelper()
        addBtnByC.setOnClickListener{
            val branche:String = spinner_type.selectedItem.toString()
            if(validate()){
                //vérification de client
                db.checkNumClient(cin.text.toString()) { isValid->
                    if (isValid) {
                        db.insertContratTemporaire(cin.text.toString(),branche)
                        Toast.makeText(this, "nous avons envoyé un message de confirmation à votre email", Toast.LENGTH_SHORT).show()

                        clearEditText()
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
        if(cin.text.toString().trim().isEmpty()){
            cin.error = "Veuillez entrer le numéro de CIN"
            return false
        }
        if (spinner_type.selectedItem.equals("branche")){
            Toast.makeText(this, "choisir un branche", Toast.LENGTH_SHORT).show()
            return false
        }

         return true
    }
    private fun clearEditText(){
        cin.setText("")
        spinner_type.requestFocus()
    }

}
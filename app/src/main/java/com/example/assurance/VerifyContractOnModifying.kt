package com.example.assurance

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_on_modifying_contract.*

class VerifyContractOnModifying : AppCompatActivity(){
    private lateinit var db: DataBaseHelper
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_modifying_contract)

        db = DataBaseHelper()
        verifyBtn.setOnClickListener{
            val branche:String = spinner_type.selectedItem.toString()
            if(validate()){
                //vérification de client
                db.checkContrat(cin.text.toString(),branche) { isValid->
                    if (isValid) {

                        val intent = Intent(this, ModifierContrat::class.java)
                        intent.putExtra("numCIN", cin.text.toString())
                        intent.putExtra("branche", branche)
                        startActivity(intent)

                    }
                    else {
                        Toast.makeText(this, "nous n'avons pas un compte avec ce numéro de CIN ou branche", Toast.LENGTH_SHORT).show()
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

}
package com.example.assurance

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_contrats.*

class ContratActivity : AppCompatActivity(), View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contrats)

        addC.setOnClickListener(this)
        deleteC.setOnClickListener(this)
        modifyC.setOnClickListener(this)
        displayC.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.addC->{
                val intent = Intent(applicationContext,AddContractByClient::class.java)
                startActivity(intent)
            }
            R.id.modifyC->{
                val intent = Intent(applicationContext,VerifyContractOnModifying::class.java)
                startActivity(intent)
            }
            R.id.deleteC->{
                val intent = Intent(applicationContext,VerifyContractOnDeleting::class.java)
                startActivity(intent)
            }
            R.id.displayC->{
                val intent = Intent(applicationContext,VerifyContractOnDisplying::class.java)
                startActivity(intent)
            }
        }
    }

}
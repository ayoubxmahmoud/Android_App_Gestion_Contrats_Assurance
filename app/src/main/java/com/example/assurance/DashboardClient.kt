package com.example.assurance

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.dashboard_client.*
class DashboardClient : AppCompatActivity(){
    private  lateinit var db: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_client)
        db= DataBaseHelper()

        val ls:String? = intent.getStringExtra("login1")

        if(ls != null && ls.isNotEmpty()) { //for sign in
            db.getClientByEmail(ls){ client->
                val cin0 = client[0]

                db.getNumberOfValidContract(cin0){ count->
                    validC.text = count.toString()
                }
                db.getNumberOfInvalidContract(cin0){ count->
                    invalidC.text = count.toString()
                }
                db.getTotalPrix(cin0){ prixTotal->
                    prix.text = prixTotal.toString()
                }
            }



        }else{ //for sign up
            val cin: String? = intent.getStringExtra("cin1")
            if (cin != null) {
                db.getNumberOfValidContract(cin){ count->
                    validC.text = count.toString()
                }
                db.getNumberOfInvalidContract(cin){ count->
                    invalidC.text = count.toString()
                }
                db.getTotalPrix(cin){ prixTotal->
                    prix.text = prixTotal.toString()
                }
            }



        }
    }
}

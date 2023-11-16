package com.example.assurance

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import kotlinx.android.synthetic.main.activity_add_contract_by_agent.*

class AddContractByAgent : AppCompatActivity(){
    private lateinit var db:DataBaseHelper
    private lateinit var notification:EmailSender
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contract_by_agent)
        db = DataBaseHelper()
        notification = EmailSender()

        val cin:String? = intent.getStringExtra("numClient")
        val branche:String? =intent.getStringExtra("branche")

        value_cin.text = cin
        value_branche.text = branche

        ajouterContrat()
    }
    //    fun insertContrat(numContrat: String, dateDebut: String, dateFin: String, valeurAssuree: Double, client: String, agent: String, branche: String) {

    private fun ajouterContrat(){
        addBtnByA.setOnClickListener{
            if (validate()){
                val months = hashMapOf(
                    "Janvier" to 1,
                    "Février" to 2,
                    "Mars" to 3,
                    "Avril" to 4,
                    "Mai" to 5,
                    "Juin" to 6,
                    "Juillet" to 7,
                    "Août" to 8,
                    "Septembre" to 9,
                    "Octobre" to 10,
                    "Novembre" to 11,
                    "Décembre" to 12
                )


                val numContrat = NumC.text.toString()
                val selectedMonth1 = months[spinner_month1.selectedItem.toString()]?.toInt()
                val selectedMonth2 = months[spinner_month2.selectedItem.toString()]
                val dateDebut = first_day.text.toString()+"/"+selectedMonth1+"/"+first_year.text.toString()
                val dateFin = last_day.text.toString()+"/"+selectedMonth2+"/"+last_year.text.toString()

                val valeurAssur = try {
                    valeur_assure.text.toString().toDouble()
                } catch (e: NumberFormatException) {
                    // Handle the exception, e.g. by setting a default value
                    0.0
                }

                val client = value_cin.text.toString()
                val branche = value_branche.text.toString()
                val agent = "JA123"


                var clientEmail: String? = ""
                db.getClient(client) { infos ->
                    clientEmail = infos[4]
                }
                db.insertContrat(numContrat, dateDebut, dateFin, valeurAssur, client, agent, branche,selectedMonth1!!)
                db.deleteFromContratTemp(client)
                notification.sendEmail(this, clientEmail.toString(), "La validation de contrat d'assurance", "Votre contrat d'assurance a été validé avec succès. Merci de votre confiance!", "ayoubmahmoud617@gmail.com")



            }
        }
    }


    private fun validate():Boolean{
        if (NumC.text.isEmpty()){
            NumC.error = "Veuiller saisir le numero de contrat!"
        }
        //correct this : first_day and last_day msut be notEmpty and numeric value
        if (first_day.text.toString().isNotEmpty() || last_day.text.toString().isNotEmpty()){
            if (!isNumeric(first_day.text.toString()) || !isNumeric(last_day.text.toString())){
                first_day.error = "Entrer des valeur numérique"
                last_day.error = "Entrer des valeur numérique"
                return false
            }
        }else{
            first_day.error = "Veuiller saisir le jour"
            last_day.error = "Veuiller saisir le jour"
            return false
        }

        if (first_year.text.toString().length != 4 || last_year.text.toString().length != 4){
            first_year.error = "Veuiller saisir l'année"
            last_year.error = "Veuiller saisir l'année"
            return false
        }
        if (spinner_month1.equals("Mois") || spinner_month2.equals("Mois")){
            Toast.makeText(this, "Veuillez saisir le mois", Toast.LENGTH_SHORT).show()
            return false
        }
        if (valeur_assure.text.isEmpty()){
            valeur_assure.error = "Veuiller saisir la valeur assurée!"
        }
        return true
    }
    fun isNumeric(toCheck: String): Boolean {
        val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
        return toCheck.matches(regex)
    }
}



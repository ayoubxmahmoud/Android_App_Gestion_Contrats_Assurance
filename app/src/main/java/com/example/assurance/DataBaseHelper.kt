package com.example.assurance
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
//i have class like this:
class DataBaseHelper {
    //Instance de firebase FireStore
    private val db = FirebaseFirestore.getInstance()

    //Référence des collections Firestore
    private val clientRef = db.collection("Client")
    private val agentRef = db.collection("Agent")
    private val contratTempRef = db.collection("ContratTemporaire")
    private val contratRef = db.collection("Contrat")
    private val brancheRef = db.collection("Branche")

    //Fonction pour ajouter un client à la collection "Client"
    fun insertClient(numClient: String, nom:String, prenom:String, adresse:String, tel:String, email:String, motePass:String){
        val client = hashMapOf(
            "numClient" to numClient,
            "nom" to nom,
            "prenom" to prenom,
            "adresse" to adresse,
            "tel" to tel,
            "email" to email,
            "motePass" to motePass
        )
        clientRef.document(numClient).set(client)
    }
    //Fonction pour ajouter un agent à la colletion "Agent"
    fun insertAgent(numAgent:String, nom:String, prenom:String, adresse:String,tel:String){
        val agent = hashMapOf(
            "numAgent" to numAgent,
            "nom" to nom,
            "prenom" to prenom,
            "adresse" to adresse,
            "tel" to tel
        )
        agentRef.document(numAgent).set(agent)
    }
    // Fonction pour ajouter un contrat à la collection "contrat_temporaire"
    fun insertContratTemporaire(numclient: String, branche: String) {
        val contrat = hashMapOf(
            "client" to numclient,
            "branche" to branche
        )
        contratTempRef.document(numclient).set(contrat)
    }
    fun deleteFromContratTemp(client: String){
        contratTempRef.document(client).delete()
    }



    // i try to delete document from collection contrat with a given parameters:
    //why my fun not working :

    fun deleteContrat(numClient: String, branche: String) {
        contratRef.whereEqualTo("client", numClient)
            .whereEqualTo("branche", branche)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete()
                    Log.d(TAG, "Document deleted successfully.")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error deleting document: ", exception)
            }
    }


    // Fonction pour ajouter un contrat à la collection "contrats"
    fun insertContrat(numContrat: String, dateDebut: String, dateFin: String, valeurAssuree: Double, client: String, agent: String, branche: String,mois_activation:Int) {
        val contrat = hashMapOf(
            "numContrat" to numContrat,
            "dateDebut" to dateDebut,
            "dateFin" to dateFin,
            "valeurAssuree" to valeurAssuree,
            "client" to client,
            "agent" to agent,
            "branche" to branche,
            "mois_activation" to mois_activation
        )
        contratRef.document(numContrat).set(contrat)
    }


    // Function that checks whether the client information entered is valid or not
    fun checkClientInfo(email: String, mdp: String, callback: (Boolean) -> Unit) {
        clientRef.whereEqualTo("email", email)
            .whereEqualTo("motePass", mdp)
            .get()
            .addOnSuccessListener { documents ->
                callback(!documents.isEmpty)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting document: ", exception)
                callback(false)
            }
    }
    // Function that checks whether the client exists or not
    fun checkNumClient(numClient:String, callback: (Boolean) -> Unit) {
        clientRef.whereEqualTo("numClient", numClient)
            .get()
            .addOnSuccessListener { documents ->
                callback(!documents.isEmpty)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting document: ", exception)
                callback(false)
            }
    }



    //la fonction qui permet de récupérer les infos du client
    fun showDataClient(email: String, callback: (ArrayList<String>) -> Unit) {
        val list = ArrayList<String>()
        clientRef.whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val document = documents.first()
                    list.add(document.getString("numClient") ?: "")
                    list.add(document.getString("nom") ?: "")
                    list.add(document.getString("prenom") ?: "")
                    list.add(document.getString("adresse") ?: "")
                    list.add(document.getString("tel") ?: "")
                    list.add(document.getString("email") ?: "")
                    callback(list)
                }
            }
            .addOnFailureListener{ exception ->
                Log.w(TAG, "Error getting document: ", exception)
            }
    }

    //la fonction qui permet de récupérer les infos du client
    fun showDataAgent(email: String, callback: (ArrayList<String>) -> Unit) {
        val list = ArrayList<String>()
        agentRef.whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val document = documents.first()
                    list.add(document.getString("numAgent") ?: "")
                    list.add(document.getString("nom") ?: "")
                    list.add(document.getString("prenom") ?: "")
                    list.add(document.getString("adresse") ?: "")
                    list.add(document.getString("tel") ?: "")
                    list.add(document.getString("email") ?: "")
                    callback(list)
                }
            }
            .addOnFailureListener{ exception ->
                Log.w(TAG, "Error getting document: ", exception)
            }
    }
    fun getInvalidContracts(callback: (ArrayList<Contract>) -> Unit) {
        val contracts = ArrayList<Contract>()
        contratTempRef.get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty){
                    for (document in documents){
                        val numClient = document.getString("client") ?: ""
                        val branche = document.getString("branche") ?: ""
                        contracts.add(Contract(numClient,branche))
                    }
                    callback(contracts)
                }
            }
            .addOnFailureListener{ exception ->
                Log.w(TAG, "Error getting document: ", exception)
            }
    }

    data class Contract(
        val numClient:String,
        val branche:String
    )


    // Function that checks whether the client exists or not
    fun checkContrat(numClient:String, branche:String,callback: (Boolean) -> Unit) {
        contratRef.whereEqualTo("client", numClient)
            .whereEqualTo("branche",branche)
            .get()
            .addOnSuccessListener { documents ->
                callback(!documents.isEmpty)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting document: ", exception)
                callback(false)
            }
    }
    //la fonction qui permet de récupérer les infos du contrat
    fun getContrat(cin: String, branche:String,callback: (ArrayList<String>) -> Unit) {
        val list = ArrayList<String>()
        contratRef.whereEqualTo("client", cin)
            .whereEqualTo("branche",branche)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val document = documents.first()
                    list.add(document.getString("numContrat") ?: "")
                    list.add(document.getString("client") ?: "")
                    list.add(document.getString("branche") ?: "")
                    list.add(document.getString("agent") ?: "")
                    list.add(document.getString("dateDebut") ?: "")
                    list.add(document.getString("dateFin") ?: "")
                    list.add(document.getDouble("valeurAssuree")?.toString() ?: "")
                    callback(list)
                }
            }
            .addOnFailureListener{ exception ->
                Log.w(TAG, "Error getting document: ", exception)
            }
    }

    fun getClient(numCIN:String, callback: (ArrayList<String>) -> Unit){
        val list = ArrayList<String>()
        clientRef.whereEqualTo("numClient", numCIN)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val document = documents.first()
                    list.add(document.getString("numClient") ?: "")
                    list.add(document.getString("nom") ?: "")
                    list.add(document.getString("prenom") ?: "")
                    list.add(document.getString("adresse") ?: "")
                    list.add(document.getString("email") ?: "")
                    list.add(document.getString("tel") ?: "")
                    list.add(document.getString("motePass") ?: "")

                    callback(list)
                }
            }
            .addOnFailureListener{ exception ->
                Log.w(TAG, "Error getting document: ", exception)
            }
    }


    fun getAgent(callback: (ArrayList<String>) -> Unit){
        val list = ArrayList<String>()
        agentRef.whereEqualTo("numAgent", "rami123")
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val document = documents.first()
                    list.add(document.getString("numAgent") ?: "")
                    list.add(document.getString("nom") ?: "")
                    list.add(document.getString("prenom") ?: "")
                    list.add(document.getString("adresse") ?: "")
                    list.add(document.getString("email") ?: "")
                    list.add(document.getString("tel") ?: "")

                    callback(list)
                }
            }
            .addOnFailureListener{ exception ->
                Log.w(TAG, "Error getting document: ", exception)
            }
    }
    //here is from DataBaseHelper
    fun getClientByEmail(email:String, callback: (ArrayList<String>) -> Unit){
        val list = ArrayList<String>()
        clientRef.whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val document = documents.first()
                    list.add(document.getString("numClient") ?: "")
                    list.add(document.getString("nom") ?: "")
                    list.add(document.getString("prenom") ?: "")
                    list.add(document.getString("adresse") ?: "")
                    list.add(document.getString("email") ?: "")
                    list.add(document.getString("tel") ?: "")
                    list.add(document.getString("motePass") ?: "")

                    callback(list)
                }
            }
            .addOnFailureListener{ exception ->
                Log.w(TAG, "Error getting document: ", exception)
            }
    }
    // Return the number of contracts for a given cin
    fun getNumberOfValidContract(cin: String, callback: (Int) -> Unit) {
        contratRef.whereEqualTo("client", cin)
            .get()
            .addOnSuccessListener { documents ->
                callback(documents.size())
            }
            .addOnFailureListener{ exception ->
                Log.w(TAG, "Error getting documents: ", exception)
                callback(0)
            }
    }

    fun getNumberOfInvalidContract(cin: String, callback: (Int) -> Unit) {
        contratTempRef.whereEqualTo("client", cin)
            .get()
            .addOnSuccessListener { documents ->
                callback(documents.size())
            }
            .addOnFailureListener{ exception ->
                Log.w(TAG, "Error getting documents: ", exception)
                callback(0)
            }
    }


    fun getTotalPrix(cin: String, callback: (Double) -> Unit) {
        var prix = 0.0
        contratRef.whereEqualTo("client", cin)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    for (document in documents) {
                        prix += document.getDouble("valeurAssuree")!!
                    }
                }
                callback(prix)
            }
            .addOnFailureListener{ exception ->
                Log.w(TAG, "Error getting documents: ", exception)
                callback(0.0)
            }
    }

    fun getFrequencyOfBranches(callback: (Map<String, Int>) -> Unit) {
        val branches = arrayListOf<String>("Vie", "Grêle", "Tempête", "Incendie")
        val branchCount = hashMapOf<String, Int>()

        // Initialize branch count
        for (branch in branches) {
            branchCount[branch] = 0
        }

        // Query the Firestore database for all documents in the "Contrat" collection
        contratRef
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    // For each document, increment the count for the corresponding branch
                    val branch = document.getString("branche")
                    if (branch != null && branchCount.containsKey(branch)) {
                        branchCount[branch] = branchCount[branch]!! + 1
                    }
                }

                // Return the branch count to the callback function
                callback(branchCount)
            }
            .addOnFailureListener { exception ->
                // Handle any errors
                Log.e(TAG, "Error getting documents: ", exception)
            }
    }
    fun getMonthsActivate(callback: (Map<Int, Int>) -> Unit) {
        val months = arrayListOf<Int>(1,2,3,4,5,6,7,8,9,10,11,12)
        val monthCount = hashMapOf<Int, Int>()

        // Initialize month count
        for (month in months){
            monthCount[month] = 0
        }

        // Query the Firestore database for all documents in the "Contrat" collection
        contratRef
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    // For each document, increment the count for the corresponding month
                    val month = document.getDouble("mois_activation")?.toInt() ?: 0
                    if (month != 0 && monthCount.containsKey(month)) {
                        monthCount[month] = monthCount[month]!! + 1
                    }
                }

                // Return the branch count to the callback function
                callback(monthCount)
            }
            .addOnFailureListener { exception ->
                // Handle any errors
                Log.e(TAG, "Error getting documents: ", exception)
            }
    }







}


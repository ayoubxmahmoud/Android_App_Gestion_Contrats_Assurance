package com.example.assurance

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_show_contracts.*
import kotlinx.android.synthetic.main.row.view.*

class GestionDeContrats : AppCompatActivity() {
    var db: DataBaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_contracts)

        supportActionBar?.title = "listes de contrats non valides"

        db = DataBaseHelper()
        db?.getInvalidContracts(
            callback = { contracts ->
                val adapter = Adapter(contracts, this)
                recycler.layoutManager = LinearLayoutManager(this)
                recycler.adapter = adapter
            }
        )
    }



    class Adapter(private val arrayList: ArrayList<DataBaseHelper.Contract>, val context: Context) :
        RecyclerView.Adapter<Adapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bindItems(model: DataBaseHelper.Contract) {
                itemView.textview1b.text = model.numClient
                itemView.textview2b.text = model.branche
            }

        }

        override fun getItemCount(): Int {
            return arrayList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.bindItems(arrayList[position])

            holder.itemView.validBtn.setOnClickListener {
                val intent = Intent(context, AddContractByAgent::class.java)
                intent.putExtra("numClient", arrayList[position].numClient)
                intent.putExtra("branche", arrayList[position].branche)
                context.startActivity(intent)
            }

        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val list = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
            return ViewHolder(list)
        }
    }
}

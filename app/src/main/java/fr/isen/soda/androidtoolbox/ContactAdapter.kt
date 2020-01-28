package fr.isen.soda.androidtoolbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_recycler_cell.*
import kotlinx.android.synthetic.main.activity_recycler_cell.view.*


class ContactAdapter(private val contact: List<String>) : RecyclerView.Adapter<ContactAdapter.ContactHolder>() {

    class ContactHolder(val contactView: View) : RecyclerView.ViewHolder(contactView){
        val nom = contactView.nom
        val contactNom = contactView.contactName
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_recycler_cell, parent, false)
        return ContactHolder(view)
    }

    override fun getItemCount(): Int = contact.size

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.contactNom.text = contact[position]
    }

}

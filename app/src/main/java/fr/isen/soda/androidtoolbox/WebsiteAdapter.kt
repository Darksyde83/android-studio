package fr.isen.soda.androidtoolbox

import com.squareup.picasso.Picasso
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_website.view.*

class WebsiteAdapter(private val listContact: Json4Kotlin_Base) : RecyclerView.Adapter<WebsiteAdapter.WebContactHolder>() {

    class WebContactHolder(val webContactView: View) : RecyclerView.ViewHolder(webContactView){
        val webPhoto = webContactView.photoPersonne
        val webNom = webContactView.nomPersonne
        val webAdresse = webContactView.adresse
        val webMail = webContactView.mail
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebsiteAdapter.WebContactHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_website, parent, false)
        return WebContactHolder(view)
    }

    override fun getItemCount(): Int = listContact.results!!.size


    override fun onBindViewHolder(holder: WebsiteAdapter.WebContactHolder, position: Int) {
        holder.webNom?.text = listContact.results?.get(position)?.name?.first + " " + listContact.results?.get(position)?.name?.last
        holder.webMail?.text = listContact.results?.get(position)?.email
        holder.webAdresse?.text = listContact.results?.get(position)?.location?.street?.number + " " + listContact.results?.get(position)?.location?.street?.name + ", " + listContact.results?.get(position)?.location?.postcode + ", " + listContact.results?.get(position)?.location?.city + ", " + listContact.results?.get(position)?.location?.state + ", " + listContact.results?.get(position)?.location?.country
        Picasso.get().load( listContact.results?.get(position)?.picture?.large).into( holder.webPhoto);
    }


}
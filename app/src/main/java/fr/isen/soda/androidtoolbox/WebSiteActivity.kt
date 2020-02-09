package fr.isen.soda.androidtoolbox

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_web_site_.*

class WebSiteActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_site_)

        val url = "https://randomuser.me/api/?inc=name,location,email,picture&results=15"
        val gson = GsonBuilder().create()
        val que = Volley.newRequestQueue(this@WebSiteActivity)
        val req = StringRequest(Request.Method.GET, url, Response.Listener <String>{
            val webContact = gson.fromJson(it, Json4Kotlin_Base::class.java)
            web_Recycle.apply {
                layoutManager = LinearLayoutManager(this@WebSiteActivity)
                adapter = WebsiteAdapter(webContact)
                addItemDecoration(
                    DividerItemDecoration(
                        this@WebSiteActivity,
                        LinearLayoutManager.VERTICAL
                    )
                )
            }
        }, Response.ErrorListener {
            Toast.makeText(this, R.string.wrong, Toast.LENGTH_LONG).show()
        })
        que.add(req)
    }
}



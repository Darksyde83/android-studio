package fr.isen.soda.androidtoolbox

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener{
            /*val identifiant = yourId.text.toString()
            val message = "tu as clique $identifiant"
            Toast.makeText(this , message ,Toast.LENGTH_LONG).show()*/

            login()
        }
    }

    public fun login() {
        if (yourId.getText().toString().equals("admin") && yourmdp.getText().toString().equals("123")) {
            val message = "identifié"
            Toast.makeText(this , message ,Toast.LENGTH_LONG).show()
            startActivity(Intent(this, HomeActivity::class.java))
        } else {
            val message = "non identifié"
            Toast.makeText(this , message ,Toast.LENGTH_LONG).show()
        }
    }
}

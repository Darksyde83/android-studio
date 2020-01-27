package fr.isen.soda.androidtoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import org.json.JSONObject
import java.io.File
import kotlinx.android.synthetic.main.activity_formulaire.*
import java.util.*

class FormulaireActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulaire)
        var age=0
        vale.setOnClickListener{
            val j = tadateJ.text.toString()
            val m =tadateM.text.toString()
            val a =tadateA.text.toString()
            val dateannive="$j / $m / $a"
            val nom = tonNom.text.toString()
            val prenom = tonPrenom.text.toString()
            val jour  = Integer.parseInt(j)
            val mois  = Integer.parseInt(m)
            val année  = Integer.parseInt(a)
            saveDataToFile(nom, prenom, dateannive)
            age = calculAge(jour,mois,année)
        }
        affichage.setOnClickListener {
            showDataFromFile(age)
        }
    }

    private fun saveDataToFile(nom: String, prenom: String, date: String) {

        if(nom.isNotEmpty() && prenom.isNotEmpty() && date.isNotEmpty()/*!= getString(R.string.storage_date_value)*/) {
            val data = "{'$NOM_KEY': '$nom', '$PRENOM_KEY': '$prenom', '$DATE_KEY': '$date'}"
            val dataJson = JSONObject().put(NOM_KEY,nom)
            File(cacheDir.absolutePath + JSON_FILE).writeText(data)
            Toast.makeText(this@FormulaireActivity,"Sauvegarde des informations de l'utilisateur", Toast.LENGTH_LONG).show()
        }
    }

    fun showDataFromFile(age: Int){
        val dataJson = File(cacheDir.absolutePath + JSON_FILE).readText()

        if(dataJson.isNotEmpty()){
            val jsonObject = JSONObject(dataJson)
            val strDate = jsonObject.optString(DATE_KEY)
            val strNom = jsonObject.optString(NOM_KEY)
            val strPrenom = jsonObject.optString(PRENOM_KEY)

            AlertDialog.Builder(this@FormulaireActivity)
                .setTitle("Lecture du fichier")
                .setMessage("Nom: $strNom \n Prenom: $strPrenom \n Date: $strDate \n Age: $age")
                .create()
                .show()
        }
    }

    fun calculAge( day: Int ,month: Int , year: Int): Int {
        val dateAnnive= Calendar.getInstance()
        val today = Calendar.getInstance()

        dateAnnive.set(year, month, day)

        var age = today.get(Calendar.YEAR)-dateAnnive.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_MONTH) < dateAnnive.get(Calendar.DAY_OF_MONTH) && today.get(Calendar.MONTH)== dateAnnive.get(Calendar.MONTH) || today.get(Calendar.MONTH)< dateAnnive.get(Calendar.MONTH)){
            age--
        }
        return age
    }
    companion object{
        private const val JSON_FILE = "data_user_toolbox.json"
        private const val NOM_KEY = "nom"
        private const val PRENOM_KEY = "prenom"
        private const val DATE_KEY = "date"
    }
}

package fr.isen.soda.androidtoolbox

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_affichage.*

class AffichageActivity : AppCompatActivity(), LocationListener {

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000
        //permission coe
        private val PERMISSION_CODE_CONTACT = 1001
        private val PERMISSION_CODE_CONTACT_LOCATION = 1005
        private val PERMISSION_CODE_IMAGE = 1002
        private val PERMISSION_CODE_CAMERA = 1003
        private val PERMISSION_CODE_LOCALISATION = 1004
    }
    private var locationManager : LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affichage)
        val photo = photo

        //recycleview

        //sinon on demande
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_CODE_CONTACT_LOCATION)
            }
            else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), PERMISSION_CODE_CONTACT)
                showCurrentPosition()
            }
        }
        else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_CODE_LOCALISATION)
            displayContact()
        }
        else{
            showCurrentPosition()
            displayContact()
        }

        //acces a la galerie photo
        photo.setOnClickListener {
            showPictureDialog()
        }

    }

    //handle image pick up result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            photo.setImageURI(data?.data)
        }
        else if ( resultCode == Activity.RESULT_OK){
            var bmp = data?.extras?.get("data") as Bitmap
            photo.setImageBitmap(bmp)
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.setType("image/*")
        startActivityForResult(photoPickerIntent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.isNotEmpty() &&grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == PERMISSION_CODE_CONTACT) {
            displayContact()
        }
        else if(grantResults.isNotEmpty() &&grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == PERMISSION_CODE_IMAGE) {
            pickImageFromGallery()
        }
        else if(grantResults.isNotEmpty() &&grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == PERMISSION_CODE_CAMERA) {
            takepickture()
        }
        else if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == PERMISSION_CODE_LOCALISATION)
        {
            showCurrentPosition()
        }
        else if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == PERMISSION_CODE_CONTACT_LOCATION){
            displayContact()
            showCurrentPosition()
        }
        else{
            Toast.makeText(this,R.string.permission,Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayContact() {
        val contact = loadContact()
        permissionRecycleView.apply {
            layoutManager = LinearLayoutManager(this@AffichageActivity)
            adapter = ContactAdapter(contact)
            addItemDecoration(
                DividerItemDecoration(
                    this@AffichageActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }

    private fun loadContact(): List<String>{
        val contactNameList = arrayListOf<String>()
        val phoneCursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,null )
        phoneCursor?.let{ cursor -> // revient a if(phoneCursor != null)
            while (cursor.moveToNext()){
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                contactNameList.add(name)
            }
            cursor.close()
        }
        return contactNameList
    }

    private fun takepickture(){
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent,PERMISSION_CODE_CAMERA)
    }
    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf(getString(R.string.galerie),getString(R.string.camera))
        pictureDialog.setItems(pictureDialogItems) {
                _,  which ->
            when (which) {
                0 -> pickImageFromGalleryPermition()
                1 -> takepickturePermition()
            }
        }
        pictureDialog.show()
    }
    private fun pickImageFromGalleryPermition() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_CODE_IMAGE)
        }
        else{
            pickImageFromGallery()
        }
    }
    private fun takepickturePermition() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.CAMERA), PERMISSION_CODE_CAMERA)
        }
        else{
            takepickture()
        }
    }

    private fun showCurrentPosition () {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(locationManager!!.isProviderEnabled( LocationManager.GPS_PROVIDER )&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,200, 1f, this)
            val location = locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            valLatitude.text = location?.latitude.toString()
            valLongitude.text = location?.longitude.toString()
        }
        else {
            Toast.makeText(this,(R.string.messageloc),Toast.LENGTH_LONG).show()
        }

    }

    override fun onLocationChanged(location: Location?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStop() {
        super.onStop()
        locationManager!!.removeUpdates(this)
    }
}


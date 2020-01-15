package epita.marion_romain.cryptofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import epita.marion_romain.cryptofile.Crypto.KeyStoreManager
import epita.marion_romain.cryptofile.Crypto.Masterkey
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import java.lang.Exception

class Main2Activity : AppCompatActivity(), View.OnClickListener {

    // Activité ou on scroll les fichiers existants et ou on en ajoute de nouveaux.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        addfilebutton.setOnClickListener(this@Main2Activity)


        var originIntent = intent
        // extract data from the intent
        var mk = Masterkey(originIntent.getStringExtra("MESSAGE").toByteArray())
        try {
            //var ks = KeyStoreManager(mk)
        }
        catch (E : Exception){
            var explicitIntent = Intent(this, MainActivity::class.java)
            explicitIntent.putExtra("MESSAGE", "Password incorrect")
            startActivity(explicitIntent)
        }
    }


    override fun onClick(v: View?) {
        if (v!= null && v.id == R.id.addfilebutton) {
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {
            //val selectedFile = data?.data //The uri with the location of the file
            val filename: String? = data?.data?.path
            Log.println(Log.ERROR, "KeyStoreManager", filename)
        }

    }
}
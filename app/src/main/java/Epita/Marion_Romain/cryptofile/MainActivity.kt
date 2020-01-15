package epita.marion_romain.cryptofile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import epita.marion_romain.cryptofile.Crypto.KeyStoreManager
import epita.marion_romain.cryptofile.Crypto.Masterkey
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createbutton.setOnClickListener(this@MainActivity)
        var passwd = findViewById(R.id.passwordin) as EditText

        var originIntent = intent
        // extract data from the intent
        try {
            var message = originIntent.getStringExtra("MESSAGE")
            Toast.makeText(this,message.toString(),Toast.LENGTH_SHORT).show()
        }
        catch (E : Exception){}
    }

    override fun onClick(v: View?) {
        if (v != null) {
            if ( v.id == R.id.createbutton) {
                //Check if password field is not empty
                if (passwordin.text.isNotEmpty() && passwordin.text.isNotBlank())
                {
                    var explicitIntent = Intent(this, Main2Activity::class.java)
                    explicitIntent.putExtra("MESSAGE", passwordin.text.toString())
                    startActivity(explicitIntent)
                }
                else{
                    Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
                }


            }
        }
    }
}

package epita.marion_romain.cryptofile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createbutton.setOnClickListener(this@MainActivity)
    }

    override fun onClick(v: View?) {
        if (v != null && v.id == R.id.createbutton) {
            var explicitIntent = Intent(this, Main2Activity::class.java)
            startActivity(explicitIntent)

        }
    }
}

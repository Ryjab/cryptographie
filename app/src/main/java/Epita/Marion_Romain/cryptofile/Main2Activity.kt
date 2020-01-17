package epita.marion_romain.cryptofile

//import epita.marion_romain.cryptofile.MyAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import epita.marion_romain.cryptofile.Crypto.FileManager
import epita.marion_romain.cryptofile.Crypto.KeyStoreManager
import epita.marion_romain.cryptofile.Crypto.Masterkey
import kotlinx.android.synthetic.main.activity_main2.*
import java.lang.Exception


class Main2Activity : AppCompatActivity(), View.OnClickListener {

    //private lateinit var recyclerView: RecyclerView
    //private lateinit var viewAdapter: RecyclerView.Adapter<*>
    //private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var ks : KeyStoreManager
    private lateinit var fm : FileManager
    // Activité ou on scroll les fichiers existants et ou on en ajoute de nouveaux.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        addfilebutton.setOnClickListener(this@Main2Activity)

        /*
            recyclerview item
        */
        //viewManager = LinearLayoutManager(this)
       // viewAdapter = MyAdapter(myDataset)

        /*
        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
         */
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
         /*   setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }*/
        // end of recyclerview item

        var originIntent = intent
        // extract data from the intent
        var mk = Masterkey(originIntent.getStringExtra("MESSAGE").toByteArray(), this)
        try {
            if (!mk.checkKey()) {
                throw error("incorrect password")
            }
            ks = KeyStoreManager(mk, this)
            fm = FileManager(ks, this)
        }
        catch (E : Exception){
            Log.println(Log.ERROR, "Main2Activity",E.toString())
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
package epita.marion_romain.cryptofile


import epita.marion_romain.cryptofile.Crypto.MyAdapter
import epita.marion_romain.cryptofile.CustomRecyclerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import epita.marion_romain.cryptofile.Crypto.FileManager
import epita.marion_romain.cryptofile.Crypto.KeyStoreManager
import epita.marion_romain.cryptofile.Crypto.Masterkey
import kotlinx.android.synthetic.main.activity_main2.*
import java.lang.Exception


class Main2Activity : AppCompatActivity(), View.OnClickListener {

    private lateinit var recyclerView: RecyclerView
    //private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewAdapter: MyAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager


    // Activité ou on scroll les fichiers existants et ou on en ajoute de nouveaux.
    override fun onCreate(savedInstanceState: Bundle?) {
        var ks: KeyStoreManager;
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        addfilebutton.setOnClickListener(this@Main2Activity)
        var originIntent = intent
        // extract data from the intent
        var mk = Masterkey(originIntent.getStringExtra("MESSAGE").toByteArray(), this)
        try {
            ks = KeyStoreManager(mk, this)
            var fm = FileManager(ks, this)
        }
        catch (E : Exception){
            Log.println(Log.ERROR, "Main2Activity",E.toString())
            var explicitIntent = Intent(this, MainActivity::class.java)
            explicitIntent.putExtra("MESSAGE", "Password incorrect")
            startActivity(explicitIntent)
            return
        }
        /*
            recyclerview item
        */
        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(ks.entryModel, this)

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


        val myItemClickListener = View.OnClickListener {
            // we retrieve the row position from its tag
            val position = it.tag as Int
            val clickedItem = ks.entryModel.models[position]
            // do stuff
            Toast.makeText(
                this@Main2Activity,
                "Clicked " + clickedItem.file_name,
                Toast.LENGTH_SHORT)
                .show()

       /* var originIntent = intent
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
            startActivity(explicitIntent)*/

        }
// as with the listview, attach an adapter and provide some data
        val recyclerAdapter = CustomRecyclerAdapter(this@Main2Activity, ks.entryModel, myItemClickListener)
        Activity2_MyRecyclerView.adapter = recyclerAdapter


        // display performance optimization when list widget size does not change
        Activity2_MyRecyclerView.setHasFixedSize(true)
// here we specify this is a standard vertical list
        Activity2_MyRecyclerView.layoutManager = LinearLayoutManager(
            this@Main2Activity,
            LinearLayoutManager.VERTICAL,
            false)
// as with the listview, attach an adapter and provide some data
        Activity2_MyRecyclerView.adapter = CustomRecyclerAdapter(this@Main2Activity, ks.entryModel,myItemClickListener)


        //characterlist.adapter = adapter
        //characterlist.emptyView = emptylisttext

        /*characterlist.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@Main2Activity, ks.entryModel.models[position].file_name, Toast.LENGTH_SHORT).show()
            }
        })*/



        /* ************************************* */

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
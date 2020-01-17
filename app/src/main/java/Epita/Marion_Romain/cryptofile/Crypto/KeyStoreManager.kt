package epita.marion_romain.cryptofile.Crypto

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.io.File

class KeyStoreManager(val masterkey: Masterkey, private val context: Context){
    var entryModel = EntryModel()
    private var keystore = File(context.filesDir,"keystore.json")

    init {
        keystore.writeText("Ceci est un test")
        loadEntryArray()
    }

    protected fun finalize() {
        saveEntryArray()
        val cypher =  masterEncypt(keystore.readBytes(), masterkey.get())
        keystore.writeBytes("#Encrypted#\n".toByteArray() + cypher)
    }

    private fun saveEntryArray(){
        if (!entryModel.models.isEmpty())
        {
            try {
                var gson = Gson();
                var jsonEntryArray : String? = gson.toJson(entryModel);
                if (jsonEntryArray != null) {
                    keystore.writeText(jsonEntryArray)
                }
            }catch (E : Exception){
                Log.println(Log.ERROR, "KeyStoreManager", E.toString())
            }
        }
    }

    fun initKeystore() {
        try {
            println(keystore.readText())
            println("#Chiffrement#")
            val cypher = masterEncypt(keystore.readBytes(), masterkey.get())
            println("cypher = ${cypher}")
            keystore.writeBytes("#Encrypted#\n".toByteArray() + cypher)
            println("#Dechiffrement#")
            val text = masterDecrypt(cypher, masterkey.get())
            println("clear text = ${String(text, charset("UTF-8"))}")

        } catch (E: Exception) {
            Log.println(Log.ERROR, "initKeystore", E.toString())
        }
    }

    fun loadEntryArray()
    {
        if (keystore.exists())
        {
            var t= keystore.readLines()
            if (t.contains("#Encrypted#\n"))
            {
                var text = keystore.readText().replace("#Encrypted#\n", "")
                keystore.writeText(masterDecrypt(text.toByteArray(),masterkey.get()).toString())
                try {
                    var gson = Gson();
                    entryModel = gson.fromJson(keystore.reader().readText(), EntryModel::class.java);
                }catch (E : Exception)
                {
                    //potential bad masterkey entered
                    Log.println(Log.ERROR, "KeyStoreManager", E.toString())
                }
            }
            else
            {
                initKeystore()
            }
        }
        else
        {
            keystore.createNewFile()
            loadEntryArray()
        }
    }
    public fun addEntry(model: Model) {
        entryModel.models.add(model)
    }

    @SuppressLint("NewApi")
    public fun removeEntry(FileName : String){
        try {
            if (!entryModel.models.removeIf { t: Model -> t.file_name == FileName })
                for (model in entryModel.models)
                {
                    if (model.file_name.equals(FileName))
                    {
                        entryModel.models.remove(model)
                    }
                }
        }catch (E : Exception)
        {
            Log.println(Log.ERROR, "KeyStoreManager", E.toString())
        }
    }



}
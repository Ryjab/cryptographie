package epita.marion_romain.cryptofile.Crypto

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.Display
import com.google.gson.Gson
import java.io.File
import java.security.MessageDigest



class KeyStoreManager(val masterkey: Masterkey, context: Context){
    var entryModel = EntryModel()
    private var keystore = File(context.filesDir,"keystore.json")

    init {
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

    fun initKeystore()
    {
        try {
            val cypher =  masterEncypt(keystore.readBytes(), masterkey.get())
            keystore.writeBytes("#Encrypted#\n".toByteArray() + cypher)
        }catch (E : Exception)
        {
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
                keystore.readText().replace("#Encrypted#\n", "")
                keystore.writeText(masterDecrypt(keystore.readBytes(),masterkey.get()).toString())
                try {
                    var gson = Gson();
                    entryModel = gson.fromJson(keystore.reader().readText(), EntryModel::class.java);
                }catch (E : Exception)
                {
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
package epita.marion_romain.cryptofile.Crypto

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream


class FileManager (val keyStoreManager: KeyStoreManager, context: Context)
{
    var listFiles = ArrayList<File>()
    var Documents = File("/Documents/")
    /*init {
        if (!Documents.exists())
        {
            Documents.mkdirs()
        }
        FileManagerInit()
    }*/

    private fun FileManagerInit()
    {
        Documents.mkdirs()
        Documents.walk().forEach {
            if (it.isFile) {
                Log.println(Log.ERROR, "FileManager", it.name)
                listFiles.add(it)
            }
        }
        if (listFiles.isEmpty())
        {
            var test = File(Documents.absolutePath, "test.txt")
            test.parentFile.mkdirs()
            if (test.createNewFile()){
                Log.println(Log.ERROR, "FileManager", "File created")
            }
            else{
                Log.println(Log.ERROR, "FileManager", "File already exists")
            }
            test.writeText("toto")
            listFiles.add(test)
        }

        listFiles.forEach {
            println(it.path)
            addfile(it.path)
        }
    }

    fun addfile (filePath : String)
    {
        try {
            val model : Model = Model(filePath, generateKey(128), false)
            keyStoreManager.addEntry(model)
        }catch (E:Exception)
        {

        }
    }

    fun removefile (filePath: String)
    {
        val model : Model = Model(filePath, generateKey(128), false)
        keyStoreManager.removeEntry(model.file_name)
    }

}
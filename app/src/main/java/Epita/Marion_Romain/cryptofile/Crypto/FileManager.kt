package epita.marion_romain.cryptofile.Crypto

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileOutputStream


class FileManager (val keyStoreManager: KeyStoreManager, context: Context)
{
    var listFiles = ArrayList<File>()
    var Documents = File(Environment.getExternalStorageDirectory().absolutePath, "/Documents")
    init {
        if (!Documents.exists())
        {
            Documents.mkdirs()
        }
        FileManagerInit()
    }

    private fun FileManagerInit()
    {
        Documents.walk().forEach {
            if (it.isFile) {
                println(it.name)
                listFiles.add(it)
            }
        }
        if (listFiles.isEmpty())
        {
            var test = File(Documents.absolutePath, "test.txt")
            test.parentFile.mkdirs()
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
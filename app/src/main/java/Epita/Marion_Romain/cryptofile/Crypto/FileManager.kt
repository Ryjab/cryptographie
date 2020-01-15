package epita.marion_romain.cryptofile.Crypto


class FileManager (val keyStoreManager: KeyStoreManager)
{
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
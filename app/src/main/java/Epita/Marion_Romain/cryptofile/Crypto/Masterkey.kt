package epita.marion_romain.cryptofile.Crypto


import android.content.Context
import android.content.SharedPreferences
import java.security.MessageDigest

class Masterkey(private var masterkey: ByteArray, val context: Context) {

    private var PRIVATE_MODE = 0
    private var nmk = "MASTERKEY"
    private fun getSHA(key : ByteArray): ByteArray {
        // Static getInstance method is called with hashing SHA
        val md = MessageDigest.getInstance("SHA-256")

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(key)
    }

    init {
        masterkey = getSHA(masterkey)

        //to change to make impossible to change password each time
        val keypref = context?.getSharedPreferences(nmk,PRIVATE_MODE)
        var editor = keypref.edit()
        editor.putString(nmk, masterkey.toString())
        editor.commit()
    }

    fun checkKey() : Boolean
    {
        val keypref = context?.getSharedPreferences(nmk,PRIVATE_MODE)
        var toto = keypref.getString(nmk, null)
        if (toto == masterkey.toString())
        {
            return true
        }
        else {
            if (keypref.getString(nmk, "") == "") {
                var editor = keypref.edit()
                editor.putString(nmk, masterkey.toString())
                editor.commit()
                return true
            }
            return false
        }
    }

    fun get(): ByteArray {
        return masterkey
    }

    fun set(masterkey: ByteArray)
    {
        this.masterkey = masterkey
    }

    fun finalize() {
        this.set(byteArrayOf(0))
    }
}
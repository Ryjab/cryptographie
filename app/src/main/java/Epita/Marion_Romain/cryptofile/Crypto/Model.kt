package epita.marion_romain.cryptofile.Crypto

import java.io.File

class Model (val file_name : String, val file_key : ByteArray, var encrypt : Boolean)
{
    private var nonce : ByteArray = ByteArray(0)

    fun encryptfile ()
    {
        if (!this.encrypt) {
            try {
                val file = File(this.file_name)
                val cipher : Ciphertext = encryptGcm(file.readBytes(),this.file_key)
                this.nonce = cipher.iv
                file.writeText(cipher.ciphertext.toString())
                this.encrypt = true
            } catch (E: Exception) {

            }
        }
        else
        {
            //todo : implement notification handler
        }

    }
    fun decryptfile ()
    {
        if (this.encrypt)
        {
            try {
                val file = File(this.file_name)
                file.writeText(decryptGcm(Ciphertext(file.readBytes(), this.nonce), this.file_key).toString())
                this.encrypt = false
            }catch (E : Exception)
            {
                //todo : implement notification handler
            }

        }

    }
}
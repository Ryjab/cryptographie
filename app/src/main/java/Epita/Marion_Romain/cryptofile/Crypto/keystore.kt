package epita.marion_romain.cryptofile.Crypto

import android.provider.BaseColumns

object keystore {
    class keystore : BaseColumns {
        companion object {
            val TABLE_NAME = "Keystore"
            //path to the file
            val COLUMN_FILE_NAME = "file"
            // byteArray key to crypt and uncrypt the file
            val COLUMN_FILE_KEY = "key"
            //boolean which tell if the file is crypted or not
            val COLUMN_ENCRYPT = "Crypt"
        }
    }
}
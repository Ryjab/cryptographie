package epita.marion_romain.cryptofile.Crypto

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper


class DataBaseManager(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        db?.let { onCreate(it) }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    @Throws(SQLiteConstraintException::class)
    fun InsertEntry(entry : EntryModel): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(keystore.keystore.COLUMN_FILE_NAME, entry.file_name)
        values.put(keystore.keystore.COLUMN_FILE_KEY, entry.file_key)
        values.put(keystore.keystore.COLUMN_ENCRYPT, entry.encrypt)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(keystore.keystore.TABLE_NAME, null, values)

        return true
    }



    companion object{
    val DATABASE_NAME = "Keystore"
    val DATABASE_VERSION = 1
    private val SQL_CREATE_ENTRIES =
        "CREATE TABLE " + keystore.keystore.TABLE_NAME + " (" +
                keystore.keystore.COLUMN_FILE_NAME + " TEXT PRIMARY KEY," +
                keystore.keystore.COLUMN_FILE_KEY+ " BYTEARRAY," +
                keystore.keystore.COLUMN_ENCRYPT + " BOOLEAN)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + keystore.keystore.TABLE_NAME
    }
}
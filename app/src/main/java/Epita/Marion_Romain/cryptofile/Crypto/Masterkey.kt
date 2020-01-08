package epita.marion_romain.cryptofile.Crypto

class Masterkey (private var masterkey: ByteArray){

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
package br.com.andreyneto.desafioandroid

import com.google.android.material.textview.MaterialTextView
import java.math.BigInteger
import java.security.MessageDigest


fun md5(ts: String, private:String, public: String): String? {
    val input: String = ts + private + public
    return BigInteger(1, MessageDigest
        .getInstance("MD5")
        .digest(input.toByteArray())
    ).toString(16).padStart(32, '0')
}

fun MaterialTextView.enableToggle() {
    setOnClickListener {
        maxLines = if(maxLines == 3) {
            100
        } else {
            3
        }
    }
}
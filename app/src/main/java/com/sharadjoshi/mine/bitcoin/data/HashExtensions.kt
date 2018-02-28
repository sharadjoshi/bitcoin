package com.sharadjoshi.mine.bitcoin.data

import java.util.concurrent.ThreadLocalRandom

// Returns a byte array representation of this string in little endian format
// If the string is already in proper endianness, there is no way to know that here
fun String.toHex() : ByteArray {
    val value = ByteArray(this.length/2)   // half the size of string

    for(i in 1..this.length/2) {
        val firstNibble   : Int = this[i*2-2].charToInt() shl 4
        val secondNibble  : Int = this[i*2-1].charToInt()

        // Combine them into one byte
        value[i-1] = (firstNibble or secondNibble).toByte()
    }

    return value.toLittleEndian()
}

fun String.toLittleEndian() : String {
    // Reverse string two bytes at a time
    val stringBuilder = StringBuilder()
    for (i in 0 until this.length - 1 step  2) {
        stringBuilder.insert(0, this[i]).insert(1, this[i+1])
    }

    return stringBuilder.toString()
}

fun ByteArray.toLittleEndian() : ByteArray {
    for (i in 0 until (this.size/2)) {
        var byte = this[this.size - 1 - i]
        this[this.size - 1 - i] = this[i]
        this[i] = byte
    }

    return this
}

fun ByteArray.toHexString() : String {
    val stringBuilder = StringBuilder()
    for (b in this) {
        stringBuilder.append(String.format("%02x", b))
    }

    return stringBuilder.toString()
}

// Check if the hash is smaller than the target
fun ByteArray.isSmaller(target: ByteArray) : Boolean {
    val newHahZeros = (31 downTo 0)
            .takeWhile { this[it].toInt() == 0 }
            .count()

    val oldHashZeros = (31 downTo 0)
            .takeWhile { target[it].toInt() == 0 }
            .count()

    return newHahZeros > oldHashZeros
}

// We need this method because the Kotlin Char.toInt() does a literal conversion
// i.e. the value of char '0' will be 48 instead of 0
private fun Char.charToInt() : Int {
    return if (this.isDigit()) (this - '0') else (this - 'a') + 10
}

fun ClosedRange<Int>.random() =
        ThreadLocalRandom.current().nextInt(endInclusive - start) +  start
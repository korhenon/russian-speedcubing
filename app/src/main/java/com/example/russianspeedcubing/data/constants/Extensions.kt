package com.example.russianspeedcubing.data.constants

fun Int.formatAsTime(): String {
    return if (this == -1) {
        "DNF"
    }else if (this == 0) {
        "--:--"
    } else if (this.toString().length <= 2) {
        "0.${this.addZero()}"
    } else if (this / 100 < 60) {
        "${this / 100}.${this.mod(100).addZero()}"
    } else if (this / 100 >= 60) {
        "${this / 6000}:${(this.mod(6000) / 100).addZero()}.${
            (this.mod(6000).mod(100)).addZero()
        }"
    } else {
        "--:--"
    }
}

fun Any.formatAsTime(): String {
    if (this is Double) {
        val x = this.toInt()
        return if (x == -1) {
            "DNF"
        }else if (x == 0) {
            "--:--"
        } else if (x.toString().length <= 2) {
            "0.${x.addZero()}"
        } else if (x / 100 < 60) {
            "${x / 100}.${x.mod(100).addZero()}"
        } else if (x / 100 >= 60) {
            "${x / 6000}:${(x.mod(6000) / 100).addZero()}.${
                (x.mod(6000).mod(100)).addZero()
            }"
        } else {
            "--:--"
        }
    } else {
        return this.toString()
    }
}

private fun Int.addZero(): String {
    return if (this < 10) "0$this"
    else this.toString()
}

fun String.maxSize(n: Int): String {
    return if (this.length > n) this.take(n - 3) + "..." else this
}

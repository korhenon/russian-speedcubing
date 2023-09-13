package com.example.russianspeedcubing.data.constants

val months = listOf(
    "Января",
    "Февраля",
    "Марта",
    "Апреля",
    "Мая",
    "Июня",
    "Июля",
    "Августа",
    "Сентября",
    "Октября",
    "Ноября",
    "Декабря"
)

fun GetMonth(n: Int): String {
    return months[n - 1]
}
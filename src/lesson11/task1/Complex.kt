@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import lesson12.task1.PhoneBook
import kotlin.math.pow

/**
 * Фабричный метод для создания комплексного числа из строки вида x+yi
 */
fun Complex(s: String): Complex {
    try {
        val plusOrMinus = s.indexOfLast { it == '+' || it == '-' }
        return Complex(
            s.substring(0, plusOrMinus).toDouble(),
            s.substring(plusOrMinus, s.length - 1).toDouble()
        )
    } catch (e: Exception) {
        throw IllegalArgumentException()
    }
}

/**
 * Класс "комплексное число".
 *
 * Общая сложность задания -- лёгкая, общая ценность в баллах -- 8.
 * Объект класса -- комплексное число вида x+yi.
 * Про принципы работы с комплексными числами см. статью Википедии "Комплексное число".
 *
 * Аргументы конструктора -- вещественная и мнимая часть числа.
 */
class Complex(val re: Double, val im: Double) {

    /**
     * Конструктор из вещественного числа
     */
    constructor(x: Double) : this(x, 0.0)

    /**
     * Сложение.
     */
    operator fun plus(other: Complex): Complex = Complex(re + other.re, im + other.im)

    /**
     * Смена знака (у обеих частей числа)
     */
    operator fun unaryMinus(): Complex = Complex(-re, -im)

    /**
     * Вычитание
     */
    operator fun minus(other: Complex): Complex = Complex(re - other.re, im - other.im)

    /**
     * Умножение
     */
    operator fun times(other: Complex): Complex = Complex(
        re * other.re - im * other.im,
        re * other.im + im * other.re
    )

    /**
     * Деление
     */
    operator fun div(other: Complex): Complex {
        val divisor = other.re.pow(2) + other.im.pow(2)
        return Complex(
            (re * other.re + im * other.im) / divisor,
            (im * other.re - re * other.im) / divisor
        )
    }

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean {
        if (other !is Complex) return false
        return re == other.re && im == other.im
    }

    /**
     * Преобразование в строку
     */
    override fun toString(): String {
        fun reformatNumber(number: Double): String {
            return if (number == number.toInt().toDouble()) number.toInt().toString()
            else number.toString()
        }

        val reStr = reformatNumber(re)
        val imStr = reformatNumber(im)

        return if (im < 0) "${reStr}${imStr}i"
        else "${reStr}+${imStr}i"
    }
}

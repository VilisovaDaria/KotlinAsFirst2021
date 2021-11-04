@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson3.task1.digitNumber
import kotlin.math.sqrt
import kotlin.math.*

// Урок 4: списки
// Максимальное количество баллов = 12
// Рекомендуемое количество баллов = 8
// Вместе с предыдущими уроками = 24/33

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.lowercase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая (2 балла)
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>) = sqrt(v.sumOf { it.pow(2) })

/**
 * Простая (2 балла)
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */

fun mean(list: List<Double>): Double =
    if (list.isNotEmpty()) list.sum() / list.size
    else 0.0


/**
 *ьme Средняя (3 балла)
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val meanNew = mean(list)
    if (list.isNotEmpty()) {
        for (i in 0 until list.size) {
            list[i] -= meanNew
        }
    }
    return list
}

/**
 * Средняя (3 балла)
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    val c = mutableListOf<Int>()
    var i = 0
    while (i != b.size && i != a.size) {
        c.add(a[i] * b[i])
        i += 1
    }
    return c.sum()
}


/**
 * Средняя (3 балла)
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    val c = mutableListOf<Int>()
    var i = 0
    while (i != p.size) {
        c.add(p[i] * x.toDouble().pow(i).toInt())
        i += 1
    }
    return c.sum()
}

/**
 * Средняя (3 балла)
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    var i = 0
    for (item in 1 until list.size) {
        list[item] += list[i]
        i += 1
    }
    return list
}

/**
 * Средняя (3 балла)
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> = TODO()


/**
 * Сложная (4 балла)
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = TODO()

/**
 * Средняя (3 балла)
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> = TODO()

/**
 * Сложная (4 балла)
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String = TODO()

/**
 * Средняя (3 балла)
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int = TODO()

/**
 * Сложная (4 балла)
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int = TODO()

/**
 * Сложная (5 баллов)
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var a = ""
    var nNew = n

    while (nNew / 1000 > 0) {
        a += "M"
        nNew -= 1000
    }

    while (nNew / 100 > 0) {
        for (i in 5..8) {
            if (nNew / 100 == i) {
                a += "D" + "C".repeat(nNew / 100 - 5)
            }
        }
        for (i in 1..3) {
            if (nNew / 100 == i) {
                a += "C".repeat(nNew / 100)
            }
        }
        when {
            nNew / 100 == 9 -> a += "CM"
            nNew / 100 == 4 -> a += "CD"
        }
        nNew %= 100
    }

    while (nNew / 10 > 0) {
        for (i in 5..8) {
            if (nNew / 10 == i) {
                a += "L" + "X".repeat(nNew / 10 - 5)
            }
        }
        for (i in 1..3) {
            if (nNew / 10 == i) {
                a += "X".repeat(nNew / 10)
            }
        }
        when {
            nNew / 10 == 9 -> a += "XC"
            nNew / 10 == 4 -> a += "XL"
        }
        nNew %= 10
    }

    while (nNew % 10 > 0) {
        for (i in 5..8) {
            if (nNew % 10 == i) {
                a += "V" + "I".repeat(nNew % 10 - 5)
            }
        }
        for (i in 1..3) {
            if (nNew % 10 == i) {
                a += "I".repeat(nNew % 10)
            }
        }
        when {
            nNew % 10 == 9 -> a += "IX"
            nNew % 10 == 4 -> a += "IV"
        }
        nNew -= nNew
    }
    return a
}

/**
 * Очень сложная (7 баллов)
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    return when {
        digitNumber(n) == 1 -> unit(n)
        digitNumber(n) == 2 -> dozen(n)
        digitNumber(n) == 3 -> hundread(n)
        digitNumber(n) == 4 -> thousands(n)
        digitNumber(n) == 5 -> tensOf(n)
        digitNumber(n) == 6 -> hundreadOf(n)
        else -> ""
    }
}

fun unit(x: Int): String {
    val unit = arrayListOf<String>(
        "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь",
        "девять"
    )
    return if (x % 10 > 0) unit[x - 1]
    else ""
}

fun dozen(x: Int): String {
    val dozens = arrayListOf<String>(
        "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать",
        "семнадцать", "восемнадцать", "девятнадцать"
    )
    val dozensNew = arrayListOf<String>(
        "десять", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят",
        "семьдесят", "восемьдесят", "девяносто"
    )
    if (digitNumber(x) == 2) {
        if (x % 10 == 0 && x / 10 > 1) return dozensNew[x / 10 - 1]
        if (x % 10 != 0 && x / 10 > 1) return "${dozensNew[x / 10 - 1]} ${unit(x % 10)}"
        else return dozens[x % 10 - 1]
    }
    return unit(x % 10)
}

fun hundread(x: Int): String {
    val hundreads = arrayListOf<String>(
        "сто", "двести", "триста", "четыреста", "пятьсот",
        "шестьсот", "семьсот", "восемьсот", "девятьсот"
    )
    return if (digitNumber(x) == 3) "${hundreads[x / 100 - 1]} ${dozen(x % 100)}"
    else dozen(x % 100)
}

fun thousands(x: Int): String {
    val unit = arrayListOf<String>(
        "одна", "две", "три", "четыре", "пять", "шесть", "семь", "восемь",
        "девять"
    )
    val f = arrayListOf<String>("тысяч", "тысяча", "тысячи")
    if (digitNumber(x) == 4) {
        if (x / 1000 == 1) return "${unit[0]} ${f[1]} ${hundread(x % 1000)}"
        return if (x / 1000 == 2 || x / 1000 == 3 || x / 1000 == 4)
            "${unit[x / 1000 - 1]} ${f[2]} ${hundread(x % 1000)}"
        else "${unit[x / 1000 - 1]} ${f[0]} ${hundread(x % 1000)}"
    }
    return if (x % 1000 > 0) "тысяч ${hundread(x % 1000)}"
    else "тысяч"
}

fun tensOf(x: Int): String {
    val dozens = arrayListOf<String>(
        "десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать",
        "семнадцать", "восемнадцать", "девятнадцать"
    )
    val doz = arrayListOf<String>(
        "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят",
        "семьдесят", "восемьдесят", "девяносто"
    )
    val f = arrayListOf<String>("тысяч")
    if (digitNumber(x) == 5) {
        return if (x / 10000 == 1) "${dozens[x / 1000 % 10]} ${f[0]} ${hundread(x % 1000)}"
        else "${doz[x / 10000 - 2]} ${thousands(x % 10000)}"
    }
    return thousands(x % 10000)
}

fun hundreadOf(x: Int): String {
    val d = arrayListOf<String>(
        "сто", "двести", "триста", "четыреста", "пятьсот",
        "шестьсот", "семьсот", "восемьсот", "девятьсот"
    )
    return if (digitNumber(x) == 6) "${d[x / 100000 - 1]} ${tensOf(x % 100000)}"
    else ""
}
@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

fun checking(s: String): String {
    if (!s.matches(Regex("^-?[0-9]+(\\.[0-9]+)?\\s[A-z]+"))) throw IllegalArgumentException("Incorrect format")

    return s
}

/**
 * Класс "Величина с размерностью".
 *
 * Предназначен для представления величин вроде "6 метров" или "3 килограмма"
 * Общая сложность задания - средняя, общая ценность в баллах -- 18
 * Величины с размерностью можно складывать, вычитать, делить, менять им знак.
 * Их также можно умножать и делить на число.
 *
 * В конструктор передаётся вещественное значение и строковая размерность.
 * Строковая размерность может:
 * - либо строго соответствовать одной из abbreviation класса Dimension (m, g)
 * - либо соответствовать одной из приставок, к которой приписана сама размерность (Km, Kg, mm, mg)
 * - во всех остальных случаях следует бросить IllegalArgumentException
 */
class DimensionalValue(value: Double, dimension: String) : Comparable<DimensionalValue> {

    /**
     * Величина с БАЗОВОЙ размерностью (например для 1.0Kg следует вернуть результат в граммах -- 1000.0)
     */
    val value: Double = value * getMultiplier(dimension)

    private fun getMultiplier(s: String): Double {
        if (s.isEmpty()) throw IllegalArgumentException("Dimension is not existing")

        for (prefix in DimensionPrefix.values()) {
            if (prefix.abbreviation == s[0].toString() && s.length > 1) return prefix.multiplier
        }

        if (s.length > 1) throw IllegalArgumentException("Prefix is not existing")

        return 1.0
    }

    /**
     * БАЗОВАЯ размерность (опять-таки для 1.0Kg следует вернуть GRAM)
     */
    val dimension: Dimension = getBase(dimension)

    private fun getBase(s: String): Dimension {
        for (baseDimension in Dimension.values()) {
            if (baseDimension.abbreviation == s[s.lastIndex].toString()) return baseDimension
        }
        throw IllegalArgumentException("Base dimension is not existing")
    }

    /**
     *
     * Конструктор из строки. Формат строки: значение пробел размерность (1 Kg, 3 mm, 100 g и так далее).
     */
    constructor(s: String) : this(checking(s).split(" ")[0].toDouble(), checking(s).split(" ")[1])

    /**
     * Сложение с другой величиной. Если базовая размерность разная, бросить IllegalArgumentException
     * (нельзя складывать метры и килограммы)
     */
    operator fun plus(other: DimensionalValue): DimensionalValue {
        if (other.dimension != dimension) throw IllegalArgumentException("Base dimension is different")
        else return DimensionalValue(other.value + value, dimension.abbreviation)
    }

    /**
     * Смена знака величины
     */
    operator fun unaryMinus(): DimensionalValue = DimensionalValue(-value, dimension.abbreviation)

    /**
     * Вычитание другой величины. Если базовая размерность разная, бросить IllegalArgumentException
     */
    operator fun minus(other: DimensionalValue): DimensionalValue = -other + this

    /**
     * Умножение на число
     */
    operator fun times(other: Double): DimensionalValue = DimensionalValue(value * other, dimension.abbreviation)

    /**
     * Деление на число
     */
    operator fun div(other: Double): DimensionalValue = DimensionalValue(value / other, dimension.abbreviation)

    /**
     * Деление на другую величину. Если базовая размерность разная, бросить IllegalArgumentException
     */
    operator fun div(other: DimensionalValue): Double {
        if (other.dimension != dimension) throw IllegalArgumentException("Base dimension is different")
        else return value / other.value
    }

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean {
        return if (other !is DimensionalValue) false
        else other.value == value && other.dimension == dimension
    }

    /**
     * Сравнение на больше/меньше. Если базовая размерность разная, бросить IllegalArgumentException
     */
    override fun compareTo(other: DimensionalValue): Int {
        if (other.dimension != dimension) throw IllegalArgumentException("Base dimension is different")
        else return when {
            value - other.value > 0 -> 1
            value - other.value < 0 -> -1
            else -> 0
        }
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + dimension.hashCode()
        return result
    }
}


/**
 * Размерность. В этот класс можно добавлять новые варианты (секунды, амперы, прочие), но нельзя убирать
 */
enum class Dimension(val abbreviation: String) {
    METER("m"),
    GRAM("g");
}

/**
 * Приставка размерности. Опять-таки можно добавить новые варианты (деци-, санти-, мега-, ...), но нельзя убирать
 */
enum class DimensionPrefix(val abbreviation: String, val multiplier: Double) {
    KILO("K", 1000.0),
    MILLI("m", 0.001);
}
@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

import ru.spbstu.wheels.NullableMonad.filter
import ru.spbstu.wheels.defaultCompareTo

/**
 * Класс "расписание поездов".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 16.
 * Объект класса хранит расписание поездов для определённой станции отправления.
 * Для каждого поезда хранится конечная станция и список промежуточных.
 * Поддерживаемые методы:
 * добавить новый поезд, удалить поезд,
 * добавить / удалить промежуточную станцию существующему поезду,
 * поиск поездов по времени.
 *
 * В конструктор передаётся название станции отправления для данного расписания.
 */
class TrainTimeTable(val baseStationName: String) {

    private val trainInformation = mutableMapOf<String, Train>()

    /**
     * Добавить новый поезд.
     *
     * Если поезд с таким именем уже есть, следует вернуть false и ничего не изменять в таблице
     *
     * @param train название поезда
     * @param depart время отправления с baseStationName
     * @param destination конечная станция
     * @return true, если поезд успешно добавлен, false, если такой поезд уже есть
     */
    fun addTrain(train: String, depart: Time, destination: Stop): Boolean {
        if (train in trainInformation) return false

        trainInformation[train] = Train(train, Stop(baseStationName, depart), destination)
        return true
    }

    /**
     * Удалить существующий поезд.
     *
     * Если поезда с таким именем нет, следует вернуть false и ничего не изменять в таблице
     *
     * @param train название поезда
     * @return true, если поезд успешно удалён, false, если такой поезд не существует
     */
    fun removeTrain(train: String): Boolean {
        return if (train in trainInformation) {
            trainInformation.remove(train)
            true
        } else false
    }


    /**
     * Добавить/изменить начальную, промежуточную или конечную остановку поезду.
     *
     * Если у поезда ещё нет остановки с названием stop, добавить её и вернуть true.
     * Если stop.name совпадает с baseStationName, изменить время отправления с этой станции и вернуть false.
     * Если stop совпадает с destination данного поезда, изменить время прибытия на неё и вернуть false.
     * Если stop совпадает с одной из промежуточных остановок, изменить время прибытия на неё и вернуть false.
     *
     * Функция должна сохранять инвариант: время прибытия на любую из промежуточных станций
     * должно находиться в интервале между временем отправления с baseStation и временем прибытия в destination,
     * иначе следует бросить исключение IllegalArgumentException.
     * Также, время прибытия на любую из промежуточных станций не должно совпадать с временем прибытия на другую
     * станцию или с временем отправления с baseStation, иначе бросить то же исключение.
     *
     * @param train название поезда
     * @param stop начальная, промежуточная или конечная станция
     * @return true, если поезду была добавлена новая остановка, false, если было изменено время остановки на старой
     */

    fun addStop(train: String, stop: Stop): Boolean {
        val trainName = getTrain(train)

        val stopIndex = trainName.stops.indexOfFirst { (name) -> name == stop.name }

        if (stopIndex != -1) {

            if (stopIndex == 0) { // Проверяем первую станцию
                if (stop.time >= trainName.stops[1].time) throw IllegalArgumentException("Incorrect time")
            } else if (stopIndex == trainName.stops.lastIndex) { // Проверяем последнюю станцию
                if (stop.time <= trainName.stops[trainName.stops.lastIndex - 1].time) throw IllegalArgumentException("Incorrect time")
            } else if (
                (trainName.stops[stopIndex + 1].time <= trainName.stops[stopIndex].time) ||
                (trainName.stops[stopIndex - 1].time >= trainName.stops[stopIndex].time)
            )
                throw IllegalArgumentException("Incorrect time")

            trainName.stops[stopIndex] = stop
            return false
        }
        val stopIndexNew = trainName.stops.indexOfFirst { it.time >= stop.time }

        if (stopIndexNew == 0 || stopIndexNew == -1) throw IllegalArgumentException("Incorrect time")

        trainName.stops.add(stopIndexNew, stop)
        return true
    }

    private fun getTrain(trainName: String): Train =
        trainInformation[trainName] ?: throw IllegalArgumentException("Train is not exist")

    /**
     * Удалить одну из промежуточных остановок.
     *
     * Если stopName совпадает с именем одной из промежуточных остановок, удалить её и вернуть true.
     * Если у поезда нет такой остановки, или stopName совпадает с начальной или конечной остановкой, вернуть false.
     *
     * @param train название поезда
     * @param stopName название промежуточной остановки
     * @return true, если удаление успешно
     */
    fun removeStop(train: String, stopName: String): Boolean {
        val trainName = getTrain(train)

        val stopIndex = trainName.stops.indexOfFirst { (name) -> name == stopName }

        if (stopIndex == 0 || stopIndex == -1 || stopIndex == trainName.stops.lastIndex) return false

        trainName.stops.removeAt(stopIndex)
        return true
    }

    /**
     * Вернуть список всех поездов, упорядоченный по времени отправления с baseStationName
     */
    fun trains(): List<Train> = trainInformation.values.sortedBy { train -> train.stops.first().time }

    /**
     * Вернуть список всех поездов, отправляющихся не ранее currentTime
     * и имеющих остановку (начальную, промежуточную или конечную) на станции destinationName.
     * Список должен быть упорядочен по времени прибытия на станцию destinationName
     */
    fun trains(currentTime: Time, destinationName: String): List<Train> =
        trainInformation.filterValues { train -> train.stops[0].time >= currentTime }
            .filterValues { train -> train.stops.indexOfFirst { (name) -> name == destinationName } != -1 }
            .values
            .sortedBy { it.stops.find{ (name) -> name == destinationName }!!.time }


    /**
     * Сравнение на равенство.
     * Расписания считаются одинаковыми, если содержат одинаковый набор поездов,
     * и поезда с тем же именем останавливаются на одинаковых станциях в одинаковое время.
     */
    override fun equals(other: Any?): Boolean {
        if (other !is TrainTimeTable) return false
        return trainInformation == other.trainInformation
    }
}

/**
 * Время (часы, минуты)
 */
data class Time(val hour: Int, val minute: Int) : Comparable<Time> {
    /**
     * Сравнение времён на больше/меньше (согласно контракту compareTo)
     */
    override fun compareTo(other: Time): Int = (hour * 60 + minute) - (other.hour * 60 + other.minute)
}

/**
 * Остановка (название, время прибытия)
 */
data class Stop(val name: String, val time: Time)

/**
 * Поезд (имя, список остановок, упорядоченный по времени).
 * Первой идёт начальная остановка, последней конечная.
 */

data class Train(val name: String, val stops: MutableList<Stop>) {
    constructor(name: String, vararg stops: Stop) : this(name, stops.asList().toMutableList())
}

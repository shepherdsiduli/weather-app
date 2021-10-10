package za.co.shepherd.weatherapp.utils

interface Mapper<R, D> {
    fun mapFrom(type: R): D
}
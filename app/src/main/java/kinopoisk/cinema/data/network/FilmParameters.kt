package kinopoisk.cinema.data.network

object FilmParameters {

    val countries = listOf("США", "Швейцария", "Франция", "Польша", "Великобритания", "Швеция", "Индия", "Испания", "Германия", "Италия")
    val genres = listOf("Триллер", "Драма", "Криминал", "Мелодрама", "Детектив", "Фантастика", "Приключения")

    fun getRandomCountry(): Pair<String, String> {
        val countryIndex = countries.indices.random()
        val nameCountry = countries[countryIndex]
        val countryCode = (countryIndex + 1).toString()
        return Pair(nameCountry, countryCode)
    }

    fun getRandomGenre(): Pair<String, String> {
        val genreIndex = genres.indices.random()
        val nameGenre = genres[genreIndex]
        val genreCode = (genreIndex + 1).toString()
        return Pair(nameGenre, genreCode)
    }
}

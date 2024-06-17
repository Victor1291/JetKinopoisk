package com.shu.models.detail_person


data class Person(
    val personId: Int?,
    val webUrl: String?,
    val nameRu: String?,
    val nameEn: String?,
    val sex: String?,
    val posterUrl: String?,
    val growth: String?,
    val birthday: String?,
    val death: String?,
    val age: Int?,
    val birthplace: String?,
    val deathplace: String?,
    val hasAwards: Int?,
    val profession: String?,
    val facts: List<String>,
    val spouses: List<Spouses>,
    val films: List<MovieOfActor>,
) {

    private val listMap = mutableMapOf<String, MutableList<MovieOfActor>>()
    private val nameButtons = mutableListOf<String>()
    private fun sortedFilms(): Map<String, List<MovieOfActor>> {
        films.forEach { film ->
            when (film.professionKey) {
                "ACTOR" -> addFilmToListMap("Актёр", film)
                "PRODUCER" -> addFilmToListMap("Продюсер", film)
                "HESELF" -> addFilmToListMap("Участвует в шоу", film)
                "HIMSELF" -> addFilmToListMap("Играет себя", film)
                "HERSELF" -> addFilmToListMap("Участвует в шоу", film)
                "HRONO_TITR_MALE" -> addFilmToListMap("озвучил", film)
                "HRONO_TITR_FEMALE" -> addFilmToListMap("озвучила", film)
                "DIRECTOR" -> addFilmToListMap("Режиссёр", film)
                "WRITER" -> addFilmToListMap("Автор", film)
                "VOICE_MALE" -> addFilmToListMap("голос", film)
                "VOICE_FEMALE" -> addFilmToListMap("голос", film)
                else -> profession.orEmpty()
            }
        }
        return listMap
    }

    private fun addFilmToListMap(profession: String, film: MovieOfActor) {
        val list = listMap[profession]
        if (list.isNullOrEmpty()) {
            listMap[profession] = mutableListOf(film)
            nameButtons.add(profession)
        } else {
            list.add(film)
            listMap[profession] = list
        }
    }

    val listMovies: Map<String, List<MovieOfActor>>
        get() = sortedFilms()

    val listButtons: List<String>
        get() = nameButtons

}


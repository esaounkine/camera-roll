package camera.roll.network

data class RandomuserResponse(
    val results: List<RandomuserPerson>,
    val info: RandomuserInfo
)

data class RandomuserInfo(
    val results: Int,
    val page: Int
)

data class RandomuserPerson(
    val name: RandomuserName,
    val picture: RandomuserPicture
)

data class RandomuserPicture(
    val large: String,
    val medium: String,
    val thumbnail: String
)

data class RandomuserName(
    val title: String,
    val first: String,
    val last: String
)
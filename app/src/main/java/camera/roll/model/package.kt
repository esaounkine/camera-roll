package camera.roll.model

data class PictureItem(
    val imageUrl: String,
    val placeholder: String
)

data class LoadingException(val msg: String) : Exception(msg)
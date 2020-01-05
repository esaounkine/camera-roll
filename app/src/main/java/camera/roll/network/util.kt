package camera.roll.network

private const val TAG = "Network helper"

//fun <R, T> withSuccessfulResponse(
//    request: R,
//    transform: (R?) -> T
//): T {
//    Log.d(TAG, "Requesting data from the API")
//    val response = request
//
//    if (response.isSuccessful) {
//        Log.d(TAG, "Good response received: ${response.code()}")
//
//        return transform(response.body())
//    } else {
//        Log.e(TAG, "Bad response received: ${response.code()}, ${response.errorBody()}")
//
//        throw Exception("The request was unsuccessful: ${response.errorBody()}")
//    }
//}

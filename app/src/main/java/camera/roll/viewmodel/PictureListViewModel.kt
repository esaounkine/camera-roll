package camera.roll.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import camera.roll.model.PictureItem
import camera.roll.network.RandomuserApiRequestHandler

class PictureListViewModel : ViewModel() {

    private val apiService = RandomuserApiRequestHandler()

    val pictureList: LiveData<List<PictureItem>> = fetchData()

    private fun fetchData(): LiveData<List<PictureItem>> = liveData {
        val data = apiService.getData(500)
        emit(data)
    }


}
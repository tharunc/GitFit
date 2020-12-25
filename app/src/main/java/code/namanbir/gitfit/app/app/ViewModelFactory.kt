package code.namanbir.gitfit.app.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import code.namanbir.gitfit.app.data.network.ApiHelper
import code.namanbir.gitfit.app.data.repository.MainRepository
import code.namanbir.gitfit.app.ui.user.UserViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}

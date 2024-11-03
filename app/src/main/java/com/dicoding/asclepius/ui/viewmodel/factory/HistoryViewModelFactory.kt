import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.local.repository.HistoryRepository
import com.dicoding.asclepius.ui.viewmodel.HistoryViewModel

class HistoryViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: HistoryViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): HistoryViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: HistoryViewModelFactory(application).also { INSTANCE = it }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                val repository = HistoryRepository(mApplication)
                HistoryViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}


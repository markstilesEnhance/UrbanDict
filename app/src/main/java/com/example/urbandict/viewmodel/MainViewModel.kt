package com.example.urbandict.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.urbandict.model.DefinitionItem
import com.example.urbandict.model.network.UrbanDictRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.exceptions.UndeliverableException
import rx.plugins.RxJavaErrorHandler
import rx.plugins.RxJavaPlugins
import java.lang.Error
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repo: UrbanDictRepository): ViewModel() {

    sealed class AppState {
        object LOADING: AppState()
        data class SUCCESS(val defList: MutableList<DefinitionItem>): AppState()
        data class ERROR(val error: String): AppState()
    }

    private val disposable = CompositeDisposable()
    private val stateData = MutableLiveData<AppState>()

    fun getState(): LiveData<AppState> {
        return stateData
    }

    fun getDefinitions(term: String) {
        stateData.value = AppState.LOADING
        disposable.add(
            repo
                .getDictionary(term)
                .subscribe({
                    stateData.value = AppState.SUCCESS(it)
                },{
                    stateData.value = it.message?.let { err -> AppState.ERROR(err) }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }



}
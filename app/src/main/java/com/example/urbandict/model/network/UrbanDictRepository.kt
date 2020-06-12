package com.example.urbandict.model.network

import com.example.urbandict.model.DefinitionItem
import com.example.urbandict.model.db.UrbanDictDB
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UrbanDictRepository @Inject constructor(private val urbanDB: UrbanDictDB, private val urbanService: ApiService) {

    fun getDictionary(term: String): Single<MutableList<DefinitionItem>> {
        return getLocalDefs(term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getLocalDefs(term: String): Single<MutableList<DefinitionItem>> {
        return urbanDB.defsDao().getDefinitions(term).flatMap {
            if(it.isNotEmpty()) { Single.just(it) }
            else {
                getRemoteDefs(term).map {
                    urbanDB.defsDao().insertAll(it)
                    it
                }
            }
        }
    }

    private fun getRemoteDefs(term: String): Single<MutableList<DefinitionItem>> {
        return urbanService.getDefinitions(term).map {
            it.list
        }
    }
}
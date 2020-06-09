package com.example.urbandict.model.network

import android.content.Context
import com.example.urbandict.model.DefinitionItem
import com.example.urbandict.model.UrbanDictionaryResponse
import com.example.urbandict.model.db.UrbanDictDB
import com.example.urbandict.model.network.ApiClient.client
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UrbanDictRepository(context: Context) {

    val api: ApiService = client!!.create(ApiService::class.java)

    var urbanDB = UrbanDictDB.getInstance(context)

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
        return api.getDefinitions(term).map {
            it.list
        }
    }
}
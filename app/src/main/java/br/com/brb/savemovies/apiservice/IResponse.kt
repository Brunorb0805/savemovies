package br.com.brb.savemovies.apiservice

interface IResponse<T> {

    fun onResponseSuccess(response: T?)
    fun onResponseError(message: String?)

}

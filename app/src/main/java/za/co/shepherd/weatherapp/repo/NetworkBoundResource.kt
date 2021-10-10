package za.co.shepherd.weatherapp.repo

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import za.co.shepherd.weatherapp.utils.domain.Resource

abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread internal constructor() {
    private val result = MediatorLiveData<Resource<ResultType>>()
    private var mDisposable: Disposable? = null
    private var databaseSource: LiveData<ResultType>

    internal val asLiveData: LiveData<Resource<ResultType>>
        get() = result

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        databaseSource = loadFromDatabase()
        result.addSource(databaseSource) { data ->
            result.removeSource(databaseSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(databaseSource)
            } else {
                result.addSource(databaseSource) { newData -> result.setValue(Resource.success(newData)) }
            }
        }
    }

    private fun fetchFromNetwork(databaseSource: LiveData<ResultType>) {
        result.addSource(databaseSource) { newData -> result.setValue(Resource.loading(newData)) }
        createCall()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : SingleObserver<RequestType> {
                    override fun onSubscribe(d: Disposable) {
                        if (!d.isDisposed) {
                            mDisposable = d
                        }
                    }

                    override fun onSuccess(requestType: RequestType) {
                        result.removeSource(databaseSource)
                        saveResultAndReInit(requestType)
                    }

                    override fun onError(e: Throwable) {
                        onFetchFailed()
                        result.removeSource(databaseSource)
                        result.addSource(databaseSource) { newData ->
                            result.setValue(Resource.error(e.message.toString(), newData))
                        }
                        mDisposable!!.dispose()
                    }
                }
            )
    }

    @MainThread
    private fun saveResultAndReInit(response: RequestType) {
        Completable
            .fromCallable { saveCallResponse(response) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {
                        if (!d.isDisposed) {
                            mDisposable = d
                        }
                    }

                    override fun onComplete() {
                        result.addSource(loadFromDatabase()) { newData -> result.setValue(Resource.success(newData)) }
                        mDisposable!!.dispose()
                    }

                    override fun onError(e: Throwable) {
                        mDisposable!!.dispose()
                    }
                }
            )
    }

    @WorkerThread
    protected abstract fun saveCallResponse(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDatabase(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): Single<RequestType>

    @MainThread
    protected abstract fun onFetchFailed()
}

package com.github.ktomoyasu.mppsample.common

import platform.Foundation.NSUserDefaults
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_queue_t
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable

actual object Preference {
    private val userDefaults = NSUserDefaults()
    actual fun get(key: String): String {
        return userDefaults.stringForKey(key) ?: ""
    }

    actual fun put(key: String, value: String) {
        return userDefaults.setObject(value = value, forKey = key)
    }
}

internal actual val coroutineDispatcher: CoroutineDispatcher =
    NsQueueDispatcher(dispatch_get_main_queue())

internal class NsQueueDispatcher(private val dispatchQueue: dispatch_queue_t) :
    CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatchQueue) {
            block.run()
        }
    }
}
package io.android.todarch.core.util.event

import androidx.lifecycle.Observer

/**
 * @see <a href="https://github.com/nickbutcher/plaid/blob/master/core/src/main/java/io/plaidapp/core/util/event/EventObserver.kt">EventObserver</a>
 *
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
 * already been consumed.
 *
 * [onEventUnconsumedContent] is *only* called if the [Event]'s contents has not been consumed.
 */
@Suppress("unused")
class EventObserver<T>(private val onEventUnconsumedContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.consume()?.run(onEventUnconsumedContent)
    }
}
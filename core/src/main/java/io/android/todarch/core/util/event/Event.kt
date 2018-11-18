package io.android.todarch.core.util.event

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 *
 * @see <a href="https://github.com/nickbutcher/plaid/blob/master/core/src/main/java/io/plaidapp/core/util/event/">Event.kt</a>
 *
 * [Read more about this.](https://medium.com/google-developers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150)
 */
@Suppress("unused")
open class Event<out T>(private val content: T) {

    var consumed = false
        private set // Allow external read but not write

    /**
     * Consumes the content if it's not been consumed yet.
     * @return The unconsumed content or `null` if it was consumed already.
     */
    fun consume(): T? {
        return if (consumed) {
            null
        } else {
            consumed = true
            content
        }
    }

    /**
     * @return The content whether it's been handled or not.
     */
    fun peek(): T = content

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event<*>

        if (content != other.content) return false
        if (consumed != other.consumed) return false

        return true
    }

    override fun hashCode(): Int {
        var result = content?.hashCode() ?: 0
        result = 31 * result + consumed.hashCode()
        return result
    }
}
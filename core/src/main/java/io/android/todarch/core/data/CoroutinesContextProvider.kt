package io.android.todarch.core.data

import kotlin.coroutines.CoroutineContext

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 10.11.2018.
 *
 * Provide coroutines context.
 */
data class CoroutinesContextProvider(val main: CoroutineContext, val io: CoroutineContext)
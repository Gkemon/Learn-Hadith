package com.gk.emon.core_features.exceptions

import android.content.Context
import androidx.annotation.StringRes

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 * Possible to add raw error message string / error message string resource id / throwable
 * OPTIONALLY
 */
sealed class Failure(
    open var message: String? = null,
    open var throwable: Throwable? = null,
    @StringRes open var messageStringRes: Int? = 0
) {
    /** Error generated in network connection*/
    object NetworkConnection : Failure()

    /** Error generated in server*/
    object ServerError : Failure()

    /** Error generated by local db like room/realm*/
    object LocalDBError : Failure()

    /** Error generated by OS*/
    object SystemError : Failure()

    /** First check that if any error message string is existing or not.
     * Then check error string using the string resource id. Then finally check that
     * any throwable is existing or not. If yes then return throwable message */
    fun getProperError(context: Context): String {
        return message ?: (messageStringRes?.let {
            context.resources.getString(it)
        } ?: (throwable?.message ?: ""))
    }

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure(
        override var message: String? = null,
        override var throwable: Throwable? = null,
        @StringRes override var messageStringRes: Int? = 0
    ) : Failure()
}
package com.example.baseapp.vo

/**
 * The resource is comprised of the [resource state][State],
 * and the resource data of type [T].
 */
data class Resource<T> private constructor(
    val state: State,
    val data: T? = null
) {
    /**
     * Resource current status.
     */
    enum class Status {
        LOADING,
        SUCCESS,
        FAILURE
    }

    /**
     * The resource state comprised of the resource current [Status]
     * and in case of an error the error message and the error [cause][Throwable].
     */
    data class State private constructor(
        val status: Status,
        val message: String? = null,
        val cause: Throwable? = null
    ) {
        companion object {
            val LOADING =
                State(Status.LOADING)
            val SUCCESS =
                State(Status.SUCCESS)

            fun error(message: String, cause: Throwable? = null) =
                State(
                    Status.FAILURE,
                    message,
                    cause
                )
        }
    }

    companion object {
        /**
         * Convenient method to build a loading resource.
         */
        fun <T> loading() =
            Resource<T>(State.LOADING)

        /**
         * Convenient method to build a success resource with a given data.
         *
         * @param data The data held by the resource.
         */
        fun <T> success(data: T) = Resource(
            State.SUCCESS,
            data
        )

        /**
         * Convenient method to build a failure resource with a given error message and
         * optionally a [cause][Throwable].
         *
         * @param message The failure error message.
         * @param cause The failure error throwable cause, if exists.
         */
        fun <T> error(message: String, cause: Throwable? = null) =
            Resource<T>(
                State.error(
                    message,
                    cause
                )
            )
    }
}

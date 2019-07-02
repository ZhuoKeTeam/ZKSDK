package com.zkteam.sdk.exception

class ZKBaseException : RuntimeException {

    constructor()

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)
}
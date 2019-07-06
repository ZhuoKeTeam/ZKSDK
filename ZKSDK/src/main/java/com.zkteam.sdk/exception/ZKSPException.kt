package com.zkteam.sdk.exception

class ZKSPException : RuntimeException {

    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}
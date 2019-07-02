package com.zkteam.sdk.exception

/**
 * https://www.yiibai.com/kotlin/kotlin-exception-handling.html
 */
/**
 * 必须处理 的异常，不处理，就 crash 给你看
 *
 * Created by WangQing on 2019年07月02日.
 */
class ZKBaseNullPointerException : NullPointerException {

    constructor()

    constructor(message: String) : super(message)
}

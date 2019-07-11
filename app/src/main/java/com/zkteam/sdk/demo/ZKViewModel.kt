package com.zkteam.sdk.demo

import androidx.lifecycle.ViewModel

class ZKViewModel: ViewModel() {

    val userId: String = "WangQing"
    val user: User = User("WangQing", 28)
}
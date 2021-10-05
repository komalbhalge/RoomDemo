package com.kb.choco.util.extensions

import com.kb.choco.util.CURRENCY

fun String.appendCurrency(): String{
    return (CURRENCY.plus(this))
}
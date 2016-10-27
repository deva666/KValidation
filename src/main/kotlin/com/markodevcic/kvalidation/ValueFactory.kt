package com.markodevcic.kvalidation

interface ValueFactory<in T, out TFor> {
    fun produce(parent: T): TFor?
}
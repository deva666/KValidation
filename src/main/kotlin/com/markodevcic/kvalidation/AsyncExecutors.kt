package com.markodevcic.kvalidation

import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object AsyncExecutors {
    val ioExecutor: Executor by lazy {
        Executors.newCachedThreadPool()
    }

    val cpuBoundExecutor: ExecutorService by lazy {
        val cpuCount = Runtime.getRuntime().availableProcessors()
        return@lazy Executors.newFixedThreadPool(cpuCount)
    }
}

package com.wadhawan.radioandroidproject

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description

import org.junit.runners.model.Statement

@ExperimentalCoroutinesApi
class TestCoroutineRule : TestRule {
    val testDispatcher = TestCoroutineDispatcher()

    override fun apply(base: Statement?, description: Description?): Statement = object : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            try {
                // Before the test runs, set the main dispatcher to the test dispatcher
                Dispatchers.setMain(testDispatcher)
                base?.evaluate()
            } finally {
                // Reset the main dispatcher to the original dispatcher after the test completes
                Dispatchers.resetMain()
                testDispatcher.cleanupTestCoroutines()
            }
        }
    }
}
package com.qatros.logibug.core.data.response.ai

data class Data(
    val expectation: String,
    val precond: String,
    val step: String,
    val testcase: String
)
package com.example.challengemock.feature.form.util

data class ValidField<T>(
    val value: T,
    val error: Boolean = false,
) {

    fun updateValue(newValue: T) = copy(
        value = newValue,
        error = false,
    )

}
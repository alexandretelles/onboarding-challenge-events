package com.talkdesk.onboarding.call.config.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [SortValuesValidator::class])
annotation class SortValues(
    val message: String = "Invalid field value",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
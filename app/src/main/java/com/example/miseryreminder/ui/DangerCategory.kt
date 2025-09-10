package com.example.miseryreminder.ui


enum class DangerCategory {
    SAFE_ZONE,

    COMFORTABLE,

    ATTENTION_NEEDED,

    ACTION_REQUIRED,

    CRITICAL,

    OVERDUE;

    companion object {
        fun getDangerCategory(daysElapsed: Long): DangerCategory {
            return when {
                daysElapsed >= 160 -> OVERDUE
                daysElapsed >= 130 -> CRITICAL
                daysElapsed >= 100 -> ACTION_REQUIRED
                daysElapsed >= 60 -> ATTENTION_NEEDED
                daysElapsed >= 40 -> COMFORTABLE
                daysElapsed >= 14 -> SAFE_ZONE
                else -> SAFE_ZONE
            }
        }
    }
}

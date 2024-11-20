package com.monk.bmi.utils

enum class NumSegments {
    FEMALE {
        override fun getValue(): Int {
            return 0
        }
    },
    MALE {
        override fun getValue(): Int {
            return 1
        }
    };

    abstract fun getValue(): Int
}
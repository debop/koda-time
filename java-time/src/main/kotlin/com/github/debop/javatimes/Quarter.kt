package com.github.debop.javatimes

/**
 * 분기(Quarter) 를 나타내는 enum class 입니다.
 *
 * @autor debop
 * @since 18. 4. 19
 */
enum class Quarter(val value: Int) {

    FIRST(1), SECOND(2), THIRD(3), FOURTH(4);

    operator fun plus(delta: Int): Quarter {
        val amount = delta % 4
        return quarterArray[(ordinal + (amount + QuartersPerYear)) % QuartersPerYear]
    }

    val months: IntArray
        get() = when(this) {
            FIRST  -> FirstQuarterMonths
            SECOND -> SecondQuarterMonths
            THIRD  -> ThirdQuarterMonths
            FOURTH -> FourthQuarterMonths
        }

    val startMonth: Int = this.ordinal * MonthsPerQuarter + 1
    val endMonth: Int = value * MonthsPerQuarter

    companion object {

        @JvmStatic private val quarterArray: Array<Quarter> = Quarter.values()

        @JvmStatic
        fun of(q: Int): Quarter {
            if(q in 1..4) {
                return quarterArray[q - 1]
            }
            throw IllegalArgumentException("Invalid q for Quarter. need 1..4, q=$q")
        }

        @JvmStatic
        fun ofMonth(monthOfYear: Int): Quarter {
            return quarterArray[(monthOfYear - 1) / MonthsPerQuarter]
        }
    }
}
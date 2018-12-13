package com.github.debop.javatimes

import java.io.Serializable
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZonedDateTime

/**
 * YearQuarter
 * @author debop
 * @since 2018. 4. 22.
 */
data class YearQuarter(val year: Int, val quarter: Quarter) : Serializable {

    constructor(moment: LocalDateTime) : this(moment.year, Quarter.ofMonth(moment.monthValue))
    constructor(moment: OffsetDateTime) : this(moment.year, Quarter.ofMonth(moment.monthValue))
    constructor(moment: ZonedDateTime) : this(moment.year, Quarter.ofMonth(moment.monthValue))

}
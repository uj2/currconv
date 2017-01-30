package org.balaguta.currconv.data.impl

import spock.lang.Specification

import java.sql.Timestamp
import java.time.LocalDateTime

import static java.time.Month.JANUARY

class LocalDateTimeAttributeConverterSpec extends Specification {

    def converter = new LocalDateTimeAttributeConverter()

    def "handles null correctly"() {

        expect:
        converter.convertToDatabaseColumn(null) == null
        converter.convertToEntityAttribute(null) == null
    }

    def "converts to Timestamp correctly"() {

        expect:
        converter.convertToDatabaseColumn(LocalDateTime.of(2017, JANUARY, 30, 15, 1, 15)) ==
                new Timestamp(117, 0, 30, 15, 1, 15, 0)
    }

    def "converts to LocalDateTime correctly"() {

        expect:
        converter.convertToEntityAttribute(new Timestamp(117, 0, 30, 15, 1, 15, 0)) ==
                LocalDateTime.of(2017, JANUARY, 30, 15, 1, 15)
    }
}

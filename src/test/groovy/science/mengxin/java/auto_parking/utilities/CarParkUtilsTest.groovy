package science.mengxin.java.auto_parking.utilities

import spock.lang.Shared

import static science.mengxin.java.auto_parking.model.Command.R
import static science.mengxin.java.auto_parking.model.Command.F
import static science.mengxin.java.auto_parking.model.Command.L

import spock.lang.Specification
import spock.lang.Unroll
import spock.lang.Unroll
import science.mengxin.java.auto_parking.model.*

import static java.util.Optional.of

class CarParkUtilsTest extends Specification {
    void setup() {
    }

    void cleanup() {
    }

    @Shared
    def RFL = of([R, F, L])
    @Shared
    def RL = of([R, L])

    @Shared
    def RFLFRFLF = of([R, F, L, F, R, F, L, F])
    @Shared
    def FFLFFLFFLFF = of([F, F, L, F, F, L, F, F, L, F, F])
    @Shared
    def FLFLFFRFFF = of([F, L, F, L, F, F, R, F, F, F])
    @Shared
    def EMPTY = Optional.empty()

    @Unroll("""
convert the commands string : #commandsStr to Command List and result #expectedResult
""")
    def "StrToCommandList"() {
        given: ""

        when: "convert the string to command list"
        def result = CarParkUtils.strToCommandList(commandsStr)
        then: "result should be as expected"
        result == expectedResult
        where: "the scenarios list"
        commandsStr                    || expectedResult | description
        "RFL"                          || RFL            | "accepted string"
        "RFLFRFLF"                     || RFLFRFLF       | "accepted string"
        "FFLFFLFFLFF"                  || FFLFFLFFLFF    | "accepted string"
        "FLFLFFRFFF"                   || FLFLFFRFFF     | "accepted string"
        "RXL"                          || EMPTY          | "unknown X"
        "sdf123sdxxxl!\"£!\"*()&^%|\$" || EMPTY          | "unknown X"
    }


    @Unroll("""
convert the commands string : #commandsStr to Command List and result #expectedResult
""")
    def "StrToCommandListIgnoreUnknow"() {
        given: ""

        when: "convert the string to command list"
        def result = CarParkUtils.strToCommandListIgnoreUnknown(commandsStr)
        then: "result should be as expected"
        result == expectedResult
        where: "the scenarios list"
        commandsStr                              || expectedResult | description
        "RFL"                                    || RFL            | "accepted string"
        "RFLFRFLF"                               || RFLFRFLF       | "accepted string"
        "FFLFFLFFLFF"                            || FFLFFLFFLFF    | "accepted string"
        "FLFLFFRFFF"                             || FLFLFFRFFF     | "accepted string"
        "FLAFBLCFDFERFGFKKKF"                    || FLFLFFRFFF     | "ignore any"
        "RXL"                                    || RL             | "ignore X"
        "F1L23sdxxFLxl!\"£FFRF!\"*FF()&^%|\$" || FLFLFFRFFF     | "ignore any"
    }
}

package science.mengxin.java.auto_parking.model

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll
import static science.mengxin.java.auto_parking.model.Command.*

/**
 * User:    mengxin
 * Date:    2019-08-17
 * Project: auto-parking
 * Package: science.mengxin.java.auto_parking.model
 * Description: CalculateRequestDtoTest.
 * @author mengxin* @version 1.0
 */
class CalculateRequestDtoTest extends Specification {
    void setup() {
    }

    void cleanup() {
    }

    @Shared
    def totalDefault = new CalculateRequest(
            new CarParkLocation(DefaultConfig.DEFAULT_X, DefaultConfig.DEFAULT_Y,
                    DefaultConfig.DEFAULT_HEADING), [],
            DefaultConfig.DEFAULT_MAX_X, DefaultConfig.DEFAULT_MAX_Y)

    @Shared
    def expect1 = new CalculateRequest(
            new CarParkLocation(5, 5, HeadingStatus.East), [F, L, R],
            DefaultConfig.DEFAULT_MAX_X, DefaultConfig.DEFAULT_MAX_Y)
    @Shared
    def expect2 = new CalculateRequest(
            new CarParkLocation(5, 5, HeadingStatus.West), [L, F, R, F, L, R],
            20, 20)

    // we will test all possibility from the request and convert
    @Unroll("""
#description current location: #current with command forward and result #expectedResult
""")
    def "ToCalculateRequest"() {
        given: ""
        def dtp = new CalculateRequestDto(maxX, maxY, x, y, headingStatus, commandListStr, ignoreUnknownCommand)
        when: "handle forward"
        def result = dtp.toCalculateRequest()
        then: "result should be as expected"
        result == expectedResult
        where: "the scenarios list"
        x    | y    | maxX | maxY | headingStatus      | commandListStr | ignoreUnknownCommand || expectedResult            | description
        null | null | null | null | null               | null           | null                 || Optional.of(totalDefault) | "all default"
        5    | 5    | null | null | HeadingStatus.East | "FLRGD"        | null                 || Optional.of(expect1)      | "default is ignore unknown"
        5    | 5    | null | null | HeadingStatus.East | "FLRGD"        | false                || Optional.empty()          | "command list illegal"
        5    | 5    | 20   | 20   | HeadingStatus.West | "LFRFLR"       | false                || Optional.of(expect2)      | "command list illegal"
    }
}

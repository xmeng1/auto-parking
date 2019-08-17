package science.mengxin.java.auto_parking.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import science.mengxin.java.auto_parking.model.CalculateRequest
import science.mengxin.java.auto_parking.model.CarParkLocation
import science.mengxin.java.auto_parking.model.Command
import science.mengxin.java.auto_parking.model.HeadingStatus
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static java.util.Optional.of
import static science.mengxin.java.auto_parking.model.Command.*

@SpringBootTest
class AutoParkingServiceImplSpec extends Specification {
    void setup() {
    }

    void cleanup() {
    }

    @Autowired
    AutoParkingService autoParkingService

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

    def req(Integer x, Integer y, Optional<List<Command>> commandList) {
        if (x == null || y == null) {
            return new CalculateRequest(null, commandList.get())
        }
        if (!commandList.isPresent()) {
            return new CalculateRequest(new CarParkLocation(x, y), null)
        }
        return new CalculateRequest(new CarParkLocation(x, y), commandList.get())
    }

    @Unroll("""
calculate the find location request : #request and result #expectedResult
""")
    def "CalculateFinalLocation"() {
        given: ""

        when: "calculate the request"
        def result = autoParkingService.calculateFinalLocation(request)
        then: "result should be as expected"
        result == expectedResult
        where: "the scenarios list"
        request                     || expectedResult                                     | description
        req(5, 5, RFLFRFLF)         || of(new CarParkLocation(7, 7, HeadingStatus.North)) | "basic example"
        req(6, 6, FFLFFLFFLFF)      || of(new CarParkLocation(6, 6, HeadingStatus.East))  | "basic example"
        req(5, 5, FLFLFFRFFF)       || of(new CarParkLocation(4, 1, HeadingStatus.West))  | "basic example"
        null                        || Optional.empty()                                   | "null example"
        req(null, null, RFLFRFLF)   || Optional.empty()                                   | "null example"
        req(5, 5, Optional.empty()) || Optional.empty()                                   | "null example"
    }
}

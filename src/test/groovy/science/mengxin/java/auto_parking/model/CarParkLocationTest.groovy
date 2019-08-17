package science.mengxin.java.auto_parking.model

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static java.util.Optional.of

class CarParkLocationTest extends Specification {
    void setup() {
    }

    void cleanup() {
    }

    /*
    see https://docs.google.com/spreadsheets/d/1slT6tb7xatZXfrzowD34E-1yg9KmCJRDycQxiZapoig/edit?usp=sharing
    |
    |                                 North
    |                           West         East
  X |                                 South
    |
    |
    |_______________________
                 Y
    */
    @Shared
    def carParkLocationNorth55 = new CarParkLocation(5, 5, HeadingStatus.North)
    @Shared
    def carParkLocationNorth45 = new CarParkLocation(4, 5, HeadingStatus.North)
    @Shared
    def carParkLocationNorth54 = new CarParkLocation(5, 4, HeadingStatus.North)
    @Shared
    def carParkLocationNorth65 = new CarParkLocation(6, 5, HeadingStatus.North)
    @Shared
    def carParkLocationNorth56 = new CarParkLocation(5, 6, HeadingStatus.North)

    @Shared
    def carParkLocationSouth55 = new CarParkLocation(5, 5, HeadingStatus.South)
    @Shared
    def carParkLocationSouth45 = new CarParkLocation(4, 5, HeadingStatus.South)
    @Shared
    def carParkLocationSouth54 = new CarParkLocation(5, 4, HeadingStatus.South)
    @Shared
    def carParkLocationSouth65 = new CarParkLocation(6, 5, HeadingStatus.South)
    @Shared
    def carParkLocationSouth56 = new CarParkLocation(5, 6, HeadingStatus.South)

    @Shared
    def carParkLocationWest55 = new CarParkLocation(5, 5, HeadingStatus.West)
    @Shared
    def carParkLocationWest45 = new CarParkLocation(4, 5, HeadingStatus.West)
    @Shared
    def carParkLocationWest54 = new CarParkLocation(5, 4, HeadingStatus.West)
    @Shared
    def carParkLocationWest65 = new CarParkLocation(6, 5, HeadingStatus.West)
    @Shared
    def carParkLocationWest56 = new CarParkLocation(5, 6, HeadingStatus.West)

    @Shared
    def carParkLocationEast55 = new CarParkLocation(5, 5, HeadingStatus.East)
    @Shared
    def carParkLocationEast45 = new CarParkLocation(4, 5, HeadingStatus.East)
    @Shared
    def carParkLocationEast54 = new CarParkLocation(5, 4, HeadingStatus.East)
    @Shared
    def carParkLocationEast65 = new CarParkLocation(6, 5, HeadingStatus.East)
    @Shared
    def carParkLocationEast56 = new CarParkLocation(5, 6, HeadingStatus.East)

    @Unroll("""
#description current location: #current with command forward and result #expectedResult
""")
    def "HandleForward"() {
        given: ""

        when: "handle forward"
        def result = current.handleForward()
        then: "result should be as expected"
        result == expectedResult
        where: "the scenarios list"
        current                || expectedResult         | description
        carParkLocationNorth55 || carParkLocationNorth65 | "current North forward, x + 1"
        carParkLocationSouth55 || carParkLocationSouth45 | "current South forward, x - 1"
        carParkLocationWest55 || carParkLocationWest54 | "current West forward, y - 1"
        carParkLocationEast55 || carParkLocationEast56 | "current East forward, y + 1"
    }

    @Unroll("""
#description current location: #current with command forward and result #expectedResult
""")
    def "HandleLeft"() {
        given: ""

        when: "handle left"
        def result = current.handleLeft()
        then: "result should be as expected"
        result == expectedResult
        where: "the scenarios list"
        current                || expectedResult         | description
        carParkLocationNorth55 || carParkLocationWest55 | "current North left -> West "
        carParkLocationSouth55 || carParkLocationEast55 | "current South left -> East "
        carParkLocationWest55 || carParkLocationSouth55 | "current West left -> South "
        carParkLocationEast55 || carParkLocationNorth55 | "current East left -> North "
    }

    @Unroll("""
#description current location: #current with command forward and result #expectedResult
""")
    def "HandleRight"() {
        given: ""

        when: "handle right"
        def result = current.handleRight()
        then: "result should be as expected"
        result == expectedResult
        where: "the scenarios list"
        current                || expectedResult         | description
        carParkLocationNorth55 || carParkLocationEast55 | "current North left -> East "
        carParkLocationSouth55 || carParkLocationWest55 | "current South left -> West "
        carParkLocationWest55 || carParkLocationNorth55 | "current West left -> North "
        carParkLocationEast55 || carParkLocationSouth55 | "current East left -> South "
    }
}

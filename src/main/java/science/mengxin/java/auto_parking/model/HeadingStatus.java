package science.mengxin.java.auto_parking.model;

/**
 * the car in the car park heading status
 *
 * Real orientation (2 dimensions orientation)
 *
 */
public enum HeadingStatus {
    North("North(Top)"),
    South("South(Down)"),
    West("West(Left)"),
    East("East(Right)");

    HeadingStatus(String description) {
    }
}

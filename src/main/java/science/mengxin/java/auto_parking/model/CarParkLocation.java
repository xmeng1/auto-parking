package science.mengxin.java.auto_parking.model;

import java.util.Objects;

public class CarParkLocation {
    private Integer x;
    private Integer y;
    // this status cannot be null always, default value is HeadingStatus.North.
    private HeadingStatus headingStatus;
    public CarParkLocation(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.headingStatus = HeadingStatus.North;
    }

    public CarParkLocation(Integer x, Integer y, HeadingStatus headingStatus) {
        this.x = x;
        this.y = y;
        if (headingStatus != null) {
            this.headingStatus = headingStatus;
        }else{
            this.headingStatus = HeadingStatus.North;
        }
    }

    /**
     * handle command F (forward), heading not change, coordination update
     * @return Next CarParkLocation
     */
    public CarParkLocation handleForward() {
        switch (headingStatus) {
            case North:
                return new CarParkLocation(this.x + 1, this.y, this.headingStatus);
            case South:
                return new CarParkLocation(this.x - 1, this.y, this.headingStatus);
            case West:
                return new CarParkLocation(this.x, this.y-1, this.headingStatus);
            case East:
                return new CarParkLocation(this.x, this.y+1, this.headingStatus);
            default:
                return this;
        }
    }

    /**
     * handle command L (left), coordination not change, heading update
     * @return Next CarParkLocation
     */
    public CarParkLocation handleLeft() {
        switch (headingStatus) {
            case North:
                return new CarParkLocation(this.x, this.y, HeadingStatus.West);
            case South:
                return new CarParkLocation(this.x, this.y, HeadingStatus.East);
            case West:
                return new CarParkLocation(this.x, this.y, HeadingStatus.South);
            case East:
                return new CarParkLocation(this.x, this.y, HeadingStatus.North);
            default:
                return this;
        }
    }

    /**
     * handle command R (Right), coordination not change, heading update
     * @return Next CarParkLocation
     */
    public CarParkLocation handleRight() {
        switch (headingStatus) {
            case North:
                return new CarParkLocation(this.x, this.y, HeadingStatus.East);
            case South:
                return new CarParkLocation(this.x, this.y, HeadingStatus.West);
            case West:
                return new CarParkLocation(this.x, this.y, HeadingStatus.North);
            case East:
                return new CarParkLocation(this.x, this.y, HeadingStatus.South);
            default:
                return this;
        }
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }


    public HeadingStatus getHeadingStatus() {
        return headingStatus;
    }

    public void setHeadingStatus(HeadingStatus headingStatus) {
        if (headingStatus != null) {
            this.headingStatus = headingStatus;
        }
    }

    @Override
    public String toString() {
        return "CarParkLocation{" +
                "x=" + x +
                ", y=" + y +
                ", headingStatus=" + headingStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarParkLocation that = (CarParkLocation) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y) &&
                headingStatus == that.headingStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, headingStatus);
    }
}

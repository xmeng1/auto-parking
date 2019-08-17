package science.mengxin.java.auto_parking.model;

import java.util.List;
import java.util.Objects;

import static science.mengxin.java.auto_parking.model.DefaultConfig.DEFAULT_MAX_X;
import static science.mengxin.java.auto_parking.model.DefaultConfig.DEFAULT_MAX_Y;

public class CalculateRequest {
    private CarParkLocation initialCarParkLocation;
    private List<Command> commandList;
    private Integer maxX;
    private Integer maxY;

    public CalculateRequest(CarParkLocation initialCarParkLocation, List<Command> commandList) {
        this.initialCarParkLocation = initialCarParkLocation;
        this.commandList = commandList;
        this.maxX = DEFAULT_MAX_X;
        this.maxY = DEFAULT_MAX_Y;
    }

    public CalculateRequest(CarParkLocation initialCarParkLocation, List<Command> commandList, Integer maxX, Integer maxY) {
        this.initialCarParkLocation = initialCarParkLocation;
        this.commandList = commandList;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public CarParkLocation getInitialCarParkLocation() {
        return initialCarParkLocation;
    }

    public void setInitialCarParkLocation(CarParkLocation initialCarParkLocation) {
        this.initialCarParkLocation = initialCarParkLocation;
    }

    public List<Command> getCommandList() {
        return commandList;
    }

    public void setCommandList(List<Command> commandList) {
        this.commandList = commandList;
    }

    public Integer getMaxX() {
        return maxX;
    }

    public void setMaxX(Integer maxX) {
        this.maxX = maxX;
    }

    public Integer getMaxY() {
        return maxY;
    }

    public void setMaxY(Integer maxY) {
        this.maxY = maxY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculateRequest that = (CalculateRequest) o;
        return Objects.equals(initialCarParkLocation, that.initialCarParkLocation) &&
                Objects.equals(commandList, that.commandList) &&
                Objects.equals(maxX, that.maxX) &&
                Objects.equals(maxY, that.maxY);
    }

    @Override
    public int hashCode() {
        return Objects.hash(initialCarParkLocation, commandList, maxX, maxY);
    }

    @Override
    public String toString() {
        return "CalculateRequest{" +
                "initialLocation=" + initialCarParkLocation +
                ", commandList=" + commandList +
                ", maxX=" + maxX +
                ", maxY=" + maxY +
                '}';
    }
}

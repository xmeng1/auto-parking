package science.mengxin.java.auto_parking.model;

public class LocationOutOfMaxException extends Exception {
    public LocationOutOfMaxException(String errorMessage) {
        super(errorMessage);
    }
}

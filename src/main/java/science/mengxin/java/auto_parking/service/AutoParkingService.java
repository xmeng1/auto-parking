package science.mengxin.java.auto_parking.service;

import science.mengxin.java.auto_parking.model.CalculateRequest;
import science.mengxin.java.auto_parking.model.CarParkLocation;

import java.util.Optional;

public interface AutoParkingService {
    public Optional<CarParkLocation> calculateFinalLocation(CalculateRequest calculateRequest);
}

package science.mengxin.java.auto_parking.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import science.mengxin.java.auto_parking.model.CalculateRequest;
import science.mengxin.java.auto_parking.model.CarParkLocation;
import science.mengxin.java.auto_parking.model.Command;
import science.mengxin.java.auto_parking.model.HeadingStatus;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

@Service
public class AutoParkingServiceImpl implements AutoParkingService {

  private static final Logger logger = LoggerFactory.getLogger(AutoParkingServiceImpl.class);

  public AutoParkingServiceImpl() {}

  @Override
  public Optional<CarParkLocation> calculateFinalLocation(CalculateRequest calculateRequest) {
    BinaryOperator<CarParkLocation> keepLast = (t1, t2) -> {
      logger.info("start ot combinator car park location t1: {} and t2: {}, we always keep last one.", t1, t2);
      return new CarParkLocation(1,1);
    };
//    CarParkLocation result = calculateRequest.getCommandList().stream()
//            .reduce(calculateRequest.getInitialCarParkLocation(), (current,x) -> nextPosition(x, current), (t1, t2) -> {
//              logger.info("start ot combinator car park location t1: {} and t2: {}, we always keep last one.", t1, t2);
//              return new CarParkLocation(1,1);
//            });
    CarParkLocation result = calculateRequest.getCommandList().stream()
            .reduce(calculateRequest.getInitialCarParkLocation(), (current,x) -> nextPosition(x, current), BinaryOperator.maxBy(new Comparator() {
              @Override
              public int compare(Object o1, Object o2) {
                return 0;
              }

              @Override
              public boolean equals(Object obj) {
                return false;
              }
            }));
    return Optional.of(result);
  }

  /**
   * There are 4 heading status:
   *
   *        North
   * West           East
   *        South
   *
   * There are 3 command
   *
   *     F
   *  L     R
   *
   * Different status with different command will lead to different car location.
   *
   * The car initial status is  `North`.
   * F -> only change the coordinate x or y based current heading
   * L and R -> only change the current heading status
   *
   *
   * F ->  North  x+1, y
   * (x,y) South  x-1, y
   *       West   x, y-1
   *       East   x, y+1
   *
   * L -> North   West
   *      South   East
   *      West    South
   *      East    North
   *
   * R -> North   East
   *      South   West
   *      West    North
   *      East    South
   *
   *       Please check README.MD or this link  https://docs.google.com/spreadsheets/d/1slT6tb7xatZXfrzowD34E-1yg9KmCJRDycQxiZapoig/edit?usp=sharing  with detail analysis.
   * @param command  command
   * @param current current location with heading information
   * @return next location with heading information
   */
  private CarParkLocation nextPosition(Command command, CarParkLocation current) {
    if (command == null) return current;
    switch (command) {
      case F:
        return current.handleForward();
      case L:
        return current.handleLeft();
      case R:
        return current.handleRight();
      default:
        return current;
    }
  }
}

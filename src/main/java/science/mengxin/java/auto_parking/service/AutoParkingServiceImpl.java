package science.mengxin.java.auto_parking.service;

import java.util.Optional;
import java.util.function.BinaryOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import science.mengxin.java.auto_parking.model.CalculateRequest;
import science.mengxin.java.auto_parking.model.CarParkLocation;
import science.mengxin.java.auto_parking.model.Command;

@Service
public class AutoParkingServiceImpl implements AutoParkingService {

    private static final Logger logger = LoggerFactory.getLogger(AutoParkingServiceImpl.class);

    public AutoParkingServiceImpl() {
    }

    /**
     * We use Java 8 Stream reduce to implement the location calculation
     *
     * We use the last override reduce method to use the command list to apply to location.
     *
     * <p>
     * <pre>
     * <U> U reduce(U identity,
     *                  BiFunction<U, ? super T, U> accumulator,
     *                  BinaryOperator<U> combiner);
     * </pre>
     * </p>
     * <P> U is the CarParkLocation
     * <p> accumulator is nextLocation (calculate next for one command)
     * <p> combiner is only used for parallel stream, we ignore it (but still need it without null)
     *
     * @param calculateRequest Request include initial location and command list.
     * @return Final car park location
     */
    @Override
    public Optional<CarParkLocation> calculateFinalLocation(CalculateRequest calculateRequest) {
        if (calculateRequest == null
                || calculateRequest.getInitialCarParkLocation() ==null
                || calculateRequest.getCommandList() == null)
            return Optional.empty();

        // this op will be ignore because we do not need parallel stream.
        BinaryOperator<CarParkLocation> combinerIgnore = (t1, t2) -> t2;
        CarParkLocation result = calculateRequest.getCommandList().stream()
                .reduce(calculateRequest.getInitialCarParkLocation(),
                        (current, x) -> nextPosition(x, current), combinerIgnore);
        return Optional.of(result);
    }

    /**
     * There are 4 heading status:
     * <p>
     * North
     * West           East
     * South
     * <p>
     * There are 3 command
     * <p>
     * F
     * L     R
     * <p>
     * Different status with different command will lead to different car location.
     * <p>
     * The car initial status is  `North`.
     * F -> only change the coordinate x or y based current heading
     * L and R -> only change the current heading status
     * <p>
     * <p>
     * F ->  North  x+1, y
     * (x,y) South  x-1, y
     * West   x, y-1
     * East   x, y+1
     * <p>
     * L -> North   West
     * South   East
     * West    South
     * East    North
     * <p>
     * R -> North   East
     * South   West
     * West    North
     * East    South
     * <p>
     * Please check README.MD or this link  https://docs.google.com/spreadsheets/d/1slT6tb7xatZXfrzowD34E-1yg9KmCJRDycQxiZapoig/edit?usp=sharing
     * with detail analysis.
     *
     * @param command command
     * @param current current location with heading information
     * @return next location with heading information
     */
    private CarParkLocation nextPosition(Command command, CarParkLocation current) {
        if (command == null) {
            return current;
        }
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

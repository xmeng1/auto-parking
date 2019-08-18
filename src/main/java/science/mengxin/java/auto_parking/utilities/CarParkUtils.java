package science.mengxin.java.auto_parking.utilities;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import science.mengxin.java.auto_parking.model.Command;
import science.mengxin.java.auto_parking.service.AutoParkingServiceImpl;

public class CarParkUtils {
    private static final Logger logger = LoggerFactory.getLogger(AutoParkingServiceImpl.class);


    /**
     * Convert the String of car park commands to Enum list. If any of command char is illegal, return empty
     *
     * @param commandListStr string of commands like: "FFLFFLFFLFF"
     * @return  if all char of commands are validated, return List of Command, or return Optional.Empty
     */
    static public Optional<List<Command>> strToCommandList(String commandListStr) {
        Stream<Character> commandCharStream = commandListStr.chars()
                .mapToObj(c -> (char) c);
        try {
            return Optional.of(commandCharStream.map(x ->
                    Command.valueOf(x.toString())
            ).collect(Collectors.toList()));
        } catch (Exception e) {
            logger.error("The String of command list {} cannot convert to command list because of {}",
                    commandListStr, e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Convert the String of car park commands to Enum list. If any of command char is illegal, ignore it.
     * @param commandListStr string of commands like: "FFLFFLFFLFF"
     * @return if all char of commands are validated, return List of Command, or ignore any illegal char of command.
     */
    static public Optional<List<Command>> strToCommandListIgnoreUnknown(String commandListStr) {
        Stream<Character> commandCharStream = commandListStr.chars()
                .mapToObj(c -> (char) c);
        return Optional.of(commandCharStream.map(x -> {
                    try {
                        return Command.valueOf(x.toString());
                    } catch (Exception e) {
                        logger.warn("The String of command list [{}] cannot convert to command list because of [{}] cannot be recognised. Exception: {}",
                                commandListStr, x, e.getMessage());
                        return null;
                    }
                }
        ).filter(Objects::nonNull).collect(Collectors.toList()));
    }

    static public Pair<Boolean, String> checkCoordinate(Integer x, Integer y, Integer maxX, Integer maxY) {
        boolean illegalX = false;
        boolean illegalY = false;
        if (x <= 0 || x > maxX) {
            illegalX = true;
        }
        if (y <= 0 || y > maxY) {
            illegalY = true;
        }
        if (!illegalX && !illegalY) {
            return ImmutablePair.of(true, "");
        } else {
            String errorMessage = "The coordination of location (" +
                    x +
                    "," +
                    y +
                    ") is illegal:";
            if (illegalX) {
                errorMessage += "x should be in [1," + maxX + "]";
            }
            if (illegalY) {
                errorMessage += "y should be in [1," + maxY + "]";
            }
            return ImmutablePair.of(false, errorMessage);
        }
    }
}

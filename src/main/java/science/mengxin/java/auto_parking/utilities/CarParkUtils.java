package science.mengxin.java.auto_parking.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import science.mengxin.java.auto_parking.model.Command;
import science.mengxin.java.auto_parking.service.AutoParkingServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarParkUtils {
    private static final Logger logger = LoggerFactory.getLogger(AutoParkingServiceImpl.class);

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

    static public Optional<List<Command>> strToCommandListIgnoreUnknown(String commandListStr) {
        Stream<Character> commandCharStream = commandListStr.chars()
                .mapToObj(c -> (char) c);
        return Optional.of(commandCharStream.map(x -> {
                    try {

                        return Command.valueOf(x.toString());
                    } catch (Exception e) {
                        logger.error("The String of command list [{}] cannot convert to command list because of [{}] cannot be recognised. Exception: {}",
                                commandListStr, x, e.getMessage());
                        return null;
                    }
                }
        ).collect(Collectors.toList()));
    }
}

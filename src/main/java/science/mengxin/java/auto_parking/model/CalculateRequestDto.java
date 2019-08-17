package science.mengxin.java.auto_parking.model;

import static science.mengxin.java.auto_parking.model.DefaultConfig.DEFAULT_HEADING;
import static science.mengxin.java.auto_parking.model.DefaultConfig.DEFAULT_MAX_X;
import static science.mengxin.java.auto_parking.model.DefaultConfig.DEFAULT_MAX_Y;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import science.mengxin.java.auto_parking.utilities.CarParkUtils;

/**
 * User:    mengxin
 * Date:    2019-08-17
 * Project: auto-parking
 * Package: science.mengxin.java.auto_parking.model
 * Description: CalculateRequestDto.
 *
 * @author mengxin
 * @version 1.0
 */
public class CalculateRequestDto {
    private Integer maxX = DEFAULT_MAX_X;
    private Integer maxY = DEFAULT_MAX_Y;
    private Integer x;
    private Integer y;
    private HeadingStatus headingStatus = DEFAULT_HEADING;
    private String commandListStr;
    private Boolean ignoreUnknownCommand = true;

    private static final Logger logger = LoggerFactory.getLogger(CalculateRequestDto.class);

    public Optional<CalculateRequest> toCalculateRequest() {
        Optional<List<Command>> commandList;
        if (ignoreUnknownCommand) {
            commandList = CarParkUtils.strToCommandListIgnoreUnknown(commandListStr);
        }else{
            commandList = CarParkUtils.strToCommandList(commandListStr);
        }
        if (!commandList.isPresent()) {
            logger.error("The command string {} cannot be recognised.", commandListStr);
            return Optional.empty();
        }
        if (x == null || y == null || x<0 || y <0 || x>maxX || y>maxY) {
            logger.error("The initial x {} should be in [0, {}] and y {} should be in [0, {}].", x, maxX, y, maxY);
            return Optional.empty();
        }
        CarParkLocation carParkLocation = new CarParkLocation(x, y, headingStatus);
        CalculateRequest calculateRequest = new CalculateRequest(carParkLocation, commandList.get(), maxX, maxY);
        return Optional.of(calculateRequest);
    }

    public CalculateRequestDto(Integer maxX, Integer maxY, Integer x, Integer y, HeadingStatus headingStatus, String commandListStr, Boolean ignoreUnknownCommand) {
        if (maxX!=null) this.maxX = maxX;
        if (maxY!=null) this.maxY = maxY;
        if (x!=null) this.x = x; else this.x = DefaultConfig.DEFAULT_X;
        if (y!=null) this.y = y; else this.y = DefaultConfig.DEFAULT_Y;
        if (headingStatus!=null) this.headingStatus = headingStatus;
        if (commandListStr!=null) this.commandListStr = commandListStr; else this.commandListStr="";
        if (ignoreUnknownCommand!=null) this.ignoreUnknownCommand = ignoreUnknownCommand;
    }

    public CalculateRequestDto() {
    }

    public CalculateRequestDto(Integer x, Integer y, String commandListStr) {
        this.x = x;
        this.y = y;
        this.commandListStr = commandListStr;
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
        this.headingStatus = headingStatus;
    }

    public String getCommandListStr() {
        return commandListStr;
    }

    public void setCommandListStr(String commandListStr) {
        this.commandListStr = commandListStr;
    }

    public Boolean getIgnoreUnknownCommand() {
        return ignoreUnknownCommand;
    }

    public void setIgnoreUnknownCommand(Boolean ignoreUnknownCommand) {
        this.ignoreUnknownCommand = ignoreUnknownCommand;
    }
}

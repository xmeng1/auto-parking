package science.mengxin.java.auto_parking.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import science.mengxin.java.auto_parking.model.basic.ResultList;
import science.mengxin.java.auto_parking.service.AutoParkingService;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@V1APIController
public class AutoParkingController {

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  private final AutoParkingService autoParkingService;


  private static final Logger logger = LoggerFactory.getLogger(AutoParkingController.class);

  public AutoParkingController(AutoParkingService autoParkingService) {
    this.autoParkingService = autoParkingService;
  }

  @PostMapping(value = "/split", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @CrossOrigin(origins = "http://localhost:4200")
  @ResponseBody
  public ResultList<Object> splitString(@RequestBody Object request) {
//    return new VersionInfo("0.0.1");
    return null;
  }

}

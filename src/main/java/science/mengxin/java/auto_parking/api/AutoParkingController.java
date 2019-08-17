package science.mengxin.java.auto_parking.api;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import science.mengxin.java.auto_parking.model.CalculateRequest;
import science.mengxin.java.auto_parking.model.CalculateRequestDto;
import science.mengxin.java.auto_parking.model.CarParkLocation;
import science.mengxin.java.auto_parking.model.basic.Result;
import science.mengxin.java.auto_parking.service.AutoParkingService;

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

    @PostMapping(value = "/findLocation2", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseBody
    public Result<CarParkLocation> findLocation2(@RequestBody CalculateRequest request) {

        Optional<CarParkLocation> calculateResult =
                autoParkingService.calculateFinalLocation(request);
        return calculateResult.map(Result::ok)
                .orElseGet(() -> Result.build(900, "Cannot find the final location", null));

    }

  @PostMapping(value = "/findLocation", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @CrossOrigin(origins = "http://localhost:4200")
  @ResponseBody
  public Result<CarParkLocation> findLocation(@RequestBody CalculateRequestDto requestDto) {
    Optional<CalculateRequest> calculateRequestOptional = requestDto.toCalculateRequest();
    if (!calculateRequestOptional.isPresent()) {
      return Result.build(900, "Invalid input of request Dto", null);
    }
    Optional<CarParkLocation> calculateResult =
            autoParkingService.calculateFinalLocation(calculateRequestOptional.get());
    return calculateResult.map(Result::ok)
            .orElseGet(() -> Result.build(901, "Cannot find the final location", null));

  }

}

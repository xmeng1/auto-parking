package science.mengxin.java.auto_parking.api;

import java.util.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import science.mengxin.java.auto_parking.model.CalculateRequestDto;
import science.mengxin.java.auto_parking.model.CarParkLocation;
import science.mengxin.java.auto_parking.model.HeadingStatus;
import science.mengxin.java.auto_parking.model.basic.Result;
import science.mengxin.java.auto_parking.service.AutoParkingService;
import science.mengxin.java.auto_parking.utilities.CarParkUtils;

/**
 * User:    mengxin
 * Date:    2019-08-17
 * Project: auto-parking
 * Package: science.mengxin.java.auto_parking.api
 * Description: AutoParkingControllerTest.
 *
 * @author mengxin
 * @version 1.0
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AutoParkingControllerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Autowired
    private AutoParkingService autoParkingService;

    @Autowired
    private AutoParkingController autoParkingController;

    /**
     * We mock the server return x, y bigger than the maxX and maxY. to check the error
     */
    @Test
    public void findLocationWith901Error() {
        CalculateRequestDto test = new CalculateRequestDto(5,5, "FLR");
        // mock to return x y bigger than max x and max y
        Mockito.when(autoParkingService.calculateFinalLocation(test.toCalculateRequest().get())).thenReturn(
                Optional.of(new CarParkLocation(20, 20, HeadingStatus.North)));
        Result<CarParkLocation> result =  autoParkingController.findLocation(test);
        assert  result.getError() == 901;
        System.out.println(result.getMessage());
        System.out.println(CarParkUtils.checkCoordinate(20,20,15,15).getRight());
        // the error message should match
        assert  result.getMessage().equals(CarParkUtils.checkCoordinate(20,20,15,15).getRight());
    }
}

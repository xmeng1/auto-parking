package science.mengxin.java.auto_parking.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import science.mengxin.java.auto_parking.service.AutoParkingService;

/**
 * User:    mengxin
 * Date:    2019-08-17
 * Project: auto-parking
 * Package: science.mengxin.java.auto_parking.config
 * Description: AutoParkingServiceTestConfig.
 *
 * @author mengxin
 * @version 1.0
 */
@Profile("test")
@Configuration
public class AutoParkingServiceTestConfig {
    @Bean
    @Primary
    public AutoParkingService autoParkingService() {
        return Mockito.mock(AutoParkingService.class);
    }
}

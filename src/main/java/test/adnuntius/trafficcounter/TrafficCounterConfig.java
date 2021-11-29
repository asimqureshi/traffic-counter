package test.adnuntius.trafficcounter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.adnuntius.trafficcounter.repository.VehiclePassedEventRepository;
import test.adnuntius.trafficcounter.summary.AvgSpeedOf2ndFastestVehicleBySizeLogItem;
import test.adnuntius.trafficcounter.summary.SummaryLogWriter;
import test.adnuntius.trafficcounter.summary.TotalCarsByBusiestSensorLogItem;
import test.adnuntius.trafficcounter.summary.TotalCountLogItem;

import java.util.Arrays;

@Configuration
public class TrafficCounterConfig {

    @Autowired
    private VehiclePassedEventRepository repository;

    @Bean
    SummaryLogWriter summaryLogWriter() {
        return new SummaryLogWriter(Arrays.asList(new TotalCountLogItem(repository),
                new TotalCarsByBusiestSensorLogItem(repository),
                new AvgSpeedOf2ndFastestVehicleBySizeLogItem(repository)));
    }
}

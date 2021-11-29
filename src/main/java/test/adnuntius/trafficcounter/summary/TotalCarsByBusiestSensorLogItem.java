package test.adnuntius.trafficcounter.summary;

import org.springframework.beans.factory.annotation.Value;
import test.adnuntius.trafficcounter.model.VehiclePassedEvent;
import test.adnuntius.trafficcounter.repository.VehiclePassedEventRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TotalCarsByBusiestSensorLogItem implements SummaryLogItem {

    private VehiclePassedEventRepository repository;

    @Value("summary.interval.minutes")
    private int minutes = 10;

    public TotalCarsByBusiestSensorLogItem(VehiclePassedEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public String getLog() {

        Optional<Map.Entry<String, List<VehiclePassedEvent>>> vehicleBySensorEntry = repository.findByBusiestSensorInLastNMinutes(10);
        if (vehicleBySensorEntry.isPresent()) {
            return String.format("Total cars by busiest sensor [%s] in last 10 minutes = %s",
                    vehicleBySensorEntry.get().getKey(), vehicleBySensorEntry.get().getValue().size());
        }
        return "Total cars by busiest sensor in last 10 minutes = 0";
    }
}

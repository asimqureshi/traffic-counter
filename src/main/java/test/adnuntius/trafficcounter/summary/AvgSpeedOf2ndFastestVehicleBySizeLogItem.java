package test.adnuntius.trafficcounter.summary;

import org.springframework.beans.factory.annotation.Value;
import test.adnuntius.trafficcounter.model.VehiclePassedEvent;
import test.adnuntius.trafficcounter.model.VehicleSize;
import test.adnuntius.trafficcounter.repository.VehiclePassedEventRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AvgSpeedOf2ndFastestVehicleBySizeLogItem implements SummaryLogItem {

    private VehiclePassedEventRepository repository;

    @Value("summary.interval.minutes")
    private int minutes = 10;

    public AvgSpeedOf2ndFastestVehicleBySizeLogItem(VehiclePassedEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public String getLog() {

        Optional<Map.Entry<VehicleSize, List<VehiclePassedEvent>>> vehicleBySizeEntry = repository.findBy2ndFastestVehicleBySizeInLastNMinutes(10);
        if (vehicleBySizeEntry.isPresent()) {
            return String.format("Average speed of second fastest vehicle by size [%s] in last 10 minutes = %s",
                    vehicleBySizeEntry.get().getKey(),
                    vehicleBySizeEntry.get().getValue().stream()
                            .mapToInt(VehiclePassedEvent::getSpeed)
                            .average().getAsDouble());
        }
        return "Average speed of second fastest vehicle by size in last 10 minutes = 0";
    }
}

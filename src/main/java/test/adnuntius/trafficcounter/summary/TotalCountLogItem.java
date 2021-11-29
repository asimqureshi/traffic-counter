package test.adnuntius.trafficcounter.summary;

import org.springframework.beans.factory.annotation.Value;
import test.adnuntius.trafficcounter.repository.VehiclePassedEventRepository;

public class TotalCountLogItem implements SummaryLogItem {

    private VehiclePassedEventRepository repository;

    @Value("summary.interval.minutes")
    private int minutes = 10;

    public TotalCountLogItem(VehiclePassedEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public String getLog() {
        return String.format("Total cars in last 10 minutes = %s", repository.countByLastNMinutes(minutes));
    }
}

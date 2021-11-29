package test.adnuntius.trafficcounter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import test.adnuntius.trafficcounter.repository.VehiclePassedEventRepository;
import test.adnuntius.trafficcounter.summary.SummaryLogWriter;

@Component
public class ScheduledJob {

    @Autowired
    SummaryLogWriter writer;

    @Autowired
    VehiclePassedEventRepository repository;

    @Value("${purge.interval.minutes}")
    private int minutes = 60;

    @Scheduled(fixedDelay = 10000)
    void doEveryMinute() {
        writer.print();
        repository.deleteOlderThanNMinutes(60);
    }

}

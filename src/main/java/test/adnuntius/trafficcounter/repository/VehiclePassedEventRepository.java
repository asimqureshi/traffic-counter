package test.adnuntius.trafficcounter.repository;

import org.springframework.stereotype.Component;
import test.adnuntius.trafficcounter.model.VehiclePassedEvent;
import test.adnuntius.trafficcounter.model.VehicleSize;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.groupingBy;

@Component
public class VehiclePassedEventRepository {
    private final Set<VehiclePassedEvent> vehiclePassedEvents = new TreeSet<>(comparing(VehiclePassedEvent::getTimestamp));

    public void save(VehiclePassedEvent vehiclePassedEvent) {
        vehiclePassedEvents.add(vehiclePassedEvent);
    }

    public void saveAll(List<VehiclePassedEvent> vehiclePassedEventList) {
        vehiclePassedEvents.addAll(vehiclePassedEventList);
    }

    public void deleteAll() {
        vehiclePassedEvents.clear();
    }

    public void deleteOlderThanNMinutes(int minutes) {
        vehiclePassedEvents.removeIf(e -> e.getTimestamp().isBefore(LocalDateTime.now().minusMinutes(minutes)));
    }

    public long countByLastNMinutes(int minutes) {
        LocalDateTime timeBefore = LocalDateTime.now().minusMinutes(minutes);
        return vehiclePassedEvents.stream().filter(event -> event.getTimestamp().isAfter(timeBefore)).count();
    }

    public Optional<Map.Entry<String, List<VehiclePassedEvent>>> findByBusiestSensorInLastNMinutes(int minutes) {
        LocalDateTime timeBefore = LocalDateTime.now().minusMinutes(minutes);
        Set<Map.Entry<String, List<VehiclePassedEvent>>> groupedBySensor = vehiclePassedEvents.stream().filter(event -> event.getTimestamp().isAfter(timeBefore)).collect(groupingBy(VehiclePassedEvent::getCounterId)).entrySet();

        if (!groupedBySensor.isEmpty()) {
            return Optional.of(Collections.max(groupedBySensor,
                    comparingInt(event -> event.getValue().size())));
        }
        return Optional.empty();
    }

    public Optional<Map.Entry<VehicleSize, List<VehiclePassedEvent>>> findBy2ndFastestVehicleBySizeInLastNMinutes(int minutes) {
        LocalDateTime timeBefore = LocalDateTime.now().minusMinutes(minutes);
        Set<Map.Entry<VehicleSize, List<VehiclePassedEvent>>> groupedBySize = vehiclePassedEvents.stream()
                .filter(event -> event.getTimestamp().isAfter(timeBefore))
                .collect(groupingBy(VehiclePassedEvent::getSize)).entrySet();
        Optional<Map.Entry<VehicleSize, List<VehiclePassedEvent>>> secondFastest = groupedBySize.stream().
                sorted(comparingInt(entry -> entry.getValue().stream().max(comparingInt(VehiclePassedEvent::getSpeed)).get().getSpeed()))
                .limit(2).skip(1).findFirst();
        return secondFastest;
    }

}

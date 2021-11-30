package test.adnuntius.trafficcounter.transformer;

import org.springframework.stereotype.Component;
import test.adnuntius.trafficcounter.model.VehiclePassedEvent;
import test.adnuntius.trafficcounter.model.VehicleSize;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VehiclePassedEventTransformer {

    public List<VehiclePassedEvent> apply(String request) {
        return Arrays.stream(request.split(",")).filter(eventString -> !eventString.isEmpty()).map(eventString -> {
            String[] attributes = Arrays.stream(eventString.split("_")).map(String::trim).collect(Collectors.toList()).toArray(new String[0]);
            return new VehiclePassedEvent(attributes[0], LocalDateTime.parse(attributes[1]), VehicleSize.valueOf(attributes[2]), Integer.parseInt(attributes[3]));
        }).collect(Collectors.toList());
    }

}

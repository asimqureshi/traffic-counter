package test.adnuntius.trafficcounter.transformer;

import org.springframework.stereotype.Component;
import test.adnuntius.trafficcounter.model.VehiclePassedEvent;
import test.adnuntius.trafficcounter.model.VehicleSize;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class VehiclePassedEventTransformer {

    public List<VehiclePassedEvent> apply(String request) {
        return Arrays.stream(request.split(","))
                .filter(eventString -> !eventString.isEmpty())
                .map(eventString -> {
                    List<String> attributes = Arrays.stream(eventString.split("_"))
                            .map(String::trim).collect(Collectors.toList());
                    return newVehiclePassedEvent(attributes);
                }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private VehiclePassedEvent newVehiclePassedEvent(List<String> attributes){

        try {
            return new VehiclePassedEvent(attributes.get(0), LocalDateTime.parse(attributes.get(1)),
                    VehicleSize.valueOf(attributes.get(2)), Integer.parseInt(attributes.get(3)));
        } catch (Exception e) {
            //ignore
            System.err.println(e.getMessage());
        }

        return null;
    }

}

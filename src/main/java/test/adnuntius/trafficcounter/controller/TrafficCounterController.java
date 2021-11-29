package test.adnuntius.trafficcounter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import test.adnuntius.trafficcounter.repository.VehiclePassedEventRepository;
import test.adnuntius.trafficcounter.transformer.VehiclePassedEventTransformer;

@RestController
public class TrafficCounterController {

    @Autowired
    VehiclePassedEventRepository repository;

    @PostMapping("/vehicle-passed-events")
    void newVehiclePassedEvent(@RequestBody String body) {
        repository.saveAll(VehiclePassedEventTransformer.get(body));
    }

}

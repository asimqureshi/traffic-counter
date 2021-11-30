package test.adnuntius.trafficcounter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import test.adnuntius.trafficcounter.repository.VehiclePassedEventRepository;
import test.adnuntius.trafficcounter.transformer.VehiclePassedEventTransformer;

@RestController
public class TrafficCounterController {

    @Autowired
    VehiclePassedEventRepository repository;

    @Autowired
    VehiclePassedEventTransformer transformer;

    @PostMapping("/vehicle-passed-events")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void newVehiclePassedEvent(@RequestBody String body) {

        repository.saveAll(transformer.apply(body));
    }

}

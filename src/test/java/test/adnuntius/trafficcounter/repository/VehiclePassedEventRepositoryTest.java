package test.adnuntius.trafficcounter.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.adnuntius.trafficcounter.model.VehiclePassedEvent;
import test.adnuntius.trafficcounter.model.VehicleSize;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VehiclePassedEventRepositoryTest {
    
    @Autowired
    VehiclePassedEventRepository repository;

    @Test
    void testTotalCarsInLastNMinutes() {

        VehiclePassedEvent eventNow = VehiclePassedEventTestBuilder.withDefaults().withTimestampNow().build();
        VehiclePassedEvent event1MinBefore = VehiclePassedEventTestBuilder.withDefaults().withTimestampNMinsFromNow(1).build();
        VehiclePassedEvent event3MinBefore = VehiclePassedEventTestBuilder.withDefaults().withTimestampNMinsFromNow(3).build();

        repository.saveAll(List.of(eventNow, event1MinBefore, event3MinBefore));
        long totalCars = repository.countByLastNMinutes(2);
        assertEquals(2, totalCars);
    }

    @Test
    void testTotalCarsByBusiestSensorInLastNMinutes() {
        VehiclePassedEvent eventNowWithCounter1 = VehiclePassedEventTestBuilder.withDefaults().withTimestampNow().build();
        VehiclePassedEvent event1MinBeforeWithCounter1 = VehiclePassedEventTestBuilder.withDefaults().withTimestampNMinsFromNow(1).build();
        VehiclePassedEvent event3MinBeforeWithCounter1 = VehiclePassedEventTestBuilder.withDefaults().withTimestampNMinsFromNow(3).build();
        VehiclePassedEvent event5MinBeforeWithCounter1 = VehiclePassedEventTestBuilder.withDefaults().withTimestampNMinsFromNow(5).build();

        VehiclePassedEvent eventNowWithCounter2 = VehiclePassedEventTestBuilder.withDefaults().withCounterId(2).withTimestampNow().build();
        VehiclePassedEvent event1MinBeforeWithCounter2 = VehiclePassedEventTestBuilder.withDefaults().withCounterId(2).withTimestampNMinsFromNow(1).build();
        VehiclePassedEvent event3MinBeforeWithCounter2 = VehiclePassedEventTestBuilder.withDefaults().withCounterId(2).withTimestampNMinsFromNow(3).build();
        VehiclePassedEvent event11MinBeforeWithCounter2 = VehiclePassedEventTestBuilder.withDefaults().withCounterId(2).withTimestampNMinsFromNow(11).build();

        repository.saveAll(List.of(eventNowWithCounter1, event1MinBeforeWithCounter1, event3MinBeforeWithCounter1, event5MinBeforeWithCounter1, eventNowWithCounter2, event1MinBeforeWithCounter2, event3MinBeforeWithCounter2, event11MinBeforeWithCounter2));

        Map.Entry<String, List<VehiclePassedEvent>> vehicleBySensorEntry = repository.findByBusiestSensorInLastNMinutes(10).get();
        assertEquals("1", vehicleBySensorEntry.getKey());
        assertEquals(4, vehicleBySensorEntry.getValue().size());
    }

    @Test
    void testAvgSpeedOf2ndFastestVehicleBySizeInLastNMinutes() {
        VehiclePassedEvent eventSizeLSpeed100 = VehiclePassedEventTestBuilder.withDefaults().withSpeed(100).build();
        VehiclePassedEvent eventSizeLSpeed20 = VehiclePassedEventTestBuilder.withDefaults().withSpeed(20).build();
        VehiclePassedEvent eventSizeLSpeed30 = VehiclePassedEventTestBuilder.withDefaults().withSpeed(30).build();

        VehiclePassedEvent eventSizeMSpeed110= VehiclePassedEventTestBuilder.withDefaults().withSize(VehicleSize.M).withSpeed(110).build();
        VehiclePassedEvent eventSizeMSpeed10 = VehiclePassedEventTestBuilder.withDefaults().withSize(VehicleSize.M).withSpeed(10).build();

        VehiclePassedEvent eventSizeXLSpeed120= VehiclePassedEventTestBuilder.withDefaults().withSize(VehicleSize.XL).withSpeed(120).build();

        repository.saveAll(List.of(eventSizeLSpeed100, eventSizeLSpeed20, eventSizeLSpeed30, eventSizeMSpeed110, eventSizeMSpeed10, eventSizeXLSpeed120));

        Map.Entry<VehicleSize, List<VehiclePassedEvent>> vehicleBySizeEntry = repository.findBy2ndFastestVehicleBySizeInLastNMinutes(10).get();
        assertEquals(VehicleSize.M, vehicleBySizeEntry.getKey());
        assertEquals(2, vehicleBySizeEntry.getValue().size());
    }

    @Test
    void deleteOlderThanNMinutes(){
        VehiclePassedEvent eventNow = VehiclePassedEventTestBuilder.withDefaults().withTimestampNow().build();
        VehiclePassedEvent event1MinBefore = VehiclePassedEventTestBuilder.withDefaults().withTimestampNMinsFromNow(1).build();
        VehiclePassedEvent event3MinBefore = VehiclePassedEventTestBuilder.withDefaults().withTimestampNMinsFromNow(3).build();
        VehiclePassedEvent event5MinBefore = VehiclePassedEventTestBuilder.withDefaults().withTimestampNMinsFromNow(5).build();

        repository.saveAll(List.of(eventNow, event1MinBefore, event3MinBefore, event5MinBefore));
        repository.deleteOlderThanNMinutes(4);
        assertEquals(3, repository.countByLastNMinutes(6));
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }


    public static class VehiclePassedEventTestBuilder {

        private String counterId = "1";
        private LocalDateTime timestamp = LocalDateTime.now();
        private VehicleSize size = VehicleSize.L;
        private int speed = 100;

        private VehiclePassedEventTestBuilder() { }

        public static VehiclePassedEventTestBuilder withDefaults(){

            return new VehiclePassedEventTestBuilder();
        }

        public VehiclePassedEventTestBuilder withCounterId(String counterId){
            this.counterId = counterId;
            return this;
        }

        public VehiclePassedEventTestBuilder withCounterId(long counterId){
            this.counterId = counterId + "";
            return this;
        }

        public VehiclePassedEventTestBuilder withTimestamp(LocalDateTime timestamp){
            this.timestamp = timestamp;
            return this;
        }

        public VehiclePassedEventTestBuilder withTimestampNow(){
            this.timestamp = LocalDateTime.now();
            return this;
        }

        public VehiclePassedEventTestBuilder withTimestampNMinsFromNow(int mins){
            this.timestamp = LocalDateTime.now().minusMinutes(mins);
            return this;
        }

        public VehiclePassedEventTestBuilder withSize(VehicleSize size){
            this.size = size;
            return this;
        }

        public VehiclePassedEventTestBuilder withSpeed(int speed){
            this.speed = speed;
            return this;
        }

        public VehiclePassedEvent build(){
            return new VehiclePassedEvent(counterId, timestamp, size, speed);
        }
    }
}
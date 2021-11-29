package test.adnuntius.trafficcounter.transformer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import test.adnuntius.trafficcounter.model.VehiclePassedEvent;
import test.adnuntius.trafficcounter.model.VehicleSize;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VehiclePassedEventTransformerTest {

    @Test
    void get() {
        String request = """
                18673541_2020-10-07T07:34:06_M_058,
                18673541_2020-12-01T05:18:06_S_061,
                18896541_2020-10-07T07:36:06_XL_056""";
        List<VehiclePassedEvent> expected = Arrays.asList(new VehiclePassedEvent("18673541", LocalDateTime.of(2020, 10, 07, 07, 34, 06), VehicleSize.M, 58),
                new VehiclePassedEvent("18673541", LocalDateTime.of(2020, 12, 01, 05, 18, 06), VehicleSize.S, 61),
                new VehiclePassedEvent("18896541", LocalDateTime.of(2020, 10, 07, 07, 36, 06), VehicleSize.XL, 56));

        List<VehiclePassedEvent> events = VehiclePassedEventTransformer.get(request);
        assertThat(events, is(expected));
    }
}
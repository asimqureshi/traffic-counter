package test.adnuntius.trafficcounter.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import test.adnuntius.trafficcounter.model.VehiclePassedEvent;
import test.adnuntius.trafficcounter.repository.VehiclePassedEventRepository;
import test.adnuntius.trafficcounter.transformer.VehiclePassedEventTransformer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TrafficCounterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    VehiclePassedEventRepository repository;

    @MockBean
    VehiclePassedEventTransformer transformer;

    @Test
    void testNewVehiclePassedEvent_statusIsNoContent() throws Exception {
        List<VehiclePassedEvent> events = new ArrayList<>();
        when(transformer.apply(anyString())).thenReturn(events);
        doNothing().when(repository).saveAll(events);
        mockMvc.perform(post("/vehicle-passed-events").content("18673541_2021-11-29T23:20:06_M_058")).andExpect(status().isNoContent());
    }

    @Test
    void testNewVehiclePassedEvent_statusIsInternalServerError() throws Exception {
        List<VehiclePassedEvent> events = new ArrayList<>();
        when(transformer.apply(anyString())).thenThrow(new RuntimeException());
        Assertions.assertThrows(Throwable.class, () -> {
            mockMvc.perform(post("/vehicle-passed-events").content("18673541_2021-11-29T23:20:06_M_058")).andExpect(status().isInternalServerError());
        });
    }
}
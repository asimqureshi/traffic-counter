package test.adnuntius.trafficcounter.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VehiclePassedEvent {

    private String counterId;
    private LocalDateTime timestamp;
    private VehicleSize size;
    private int speed;

    public VehiclePassedEvent(String counterId, LocalDateTime timestamp, VehicleSize size, int speed) {
        this.counterId = counterId;
        this.timestamp = timestamp;
        this.size = size;
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "VehiclePassedEvent{" +
                "counterId='" + counterId + '\'' +
                ", timestamp=" + timestamp +
                ", size=" + size +
                ", speed=" + speed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehiclePassedEvent that = (VehiclePassedEvent) o;

        if (speed != that.speed) return false;
        if (counterId != null ? !counterId.equals(that.counterId) : that.counterId != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
        return size == that.size;
    }

    @Override
    public int hashCode() {
        int result = counterId != null ? counterId.hashCode() : 0;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + size.hashCode();
        result = 31 * result + speed;
        return result;
    }
}

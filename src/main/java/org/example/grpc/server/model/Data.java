package org.example.grpc.server.model;

import grpc.common.GRPCData;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "data_sensor")
@NoArgsConstructor
@Getter
@Setter
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "sensor_id")
    private long sensorId;
    private LocalDateTime timestamp;
    private double measurement;
    @Column(name = "measurement_type")
    @Enumerated(value = EnumType.STRING)
    private MeasurementType measurementType;

    public enum MeasurementType {
        TEMPERATURE,
        VOLTAGE,
        POWER
    }

    public Data(GRPCData data) {
        this.id = data.getId();
        this.sensorId = data.getSensorId();
        this.timestamp = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(
                        data.getTimestamp().getSeconds()
                ),
                ZoneId.systemDefault());
        this.measurement = data.getMeasurement();
        this.measurementType = MeasurementType.valueOf(data.getMeasurementType().name());
    }
}

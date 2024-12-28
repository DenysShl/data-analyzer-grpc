package org.example.grpc.server.service;

import com.google.protobuf.Timestamp;
import grpc.common.AnalyticsServerGrpc;
import grpc.common.GRPCAnalyticsRequest;
import grpc.common.GRPCData;
import grpc.common.MeasurementType;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.grpc.server.model.Data;

import java.time.ZoneOffset;
import java.util.List;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class GRPSAnalyticsServiceImpl extends AnalyticsServerGrpc.AnalyticsServerImplBase {
    private final DataService dataService;

    @Override
    public void askForData(GRPCAnalyticsRequest request, StreamObserver<GRPCData> responseObserver) {

        List<Data> data = dataService.getWithBatchSize(request.getBatchSize());

        for (Data d : data) {
            GRPCData grpcData = GRPCData.newBuilder()
                    .setSensorId(d.getSensorId())
                    .setTimestamp(Timestamp.newBuilder()
                            .setSeconds(
                                    d.getTimestamp().toEpochSecond(ZoneOffset.UTC)
                            )
                            .build()
                    )
                    .setMeasurement(d.getMeasurement())
                    .setMeasurementType(MeasurementType.valueOf(d.getMeasurementType().name()))
                    .build();
            responseObserver.onNext(grpcData);
        }
        log.info("Batch was sent!");
        responseObserver.onCompleted();
    }

}

package org.example.grpc.server.service;

import com.google.protobuf.Empty;
import grpc.common.DataServerGrpc;
import grpc.common.GRPCData;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.grpc.server.model.Data;

@GrpcService
@RequiredArgsConstructor
public class GRPCDataService extends DataServerGrpc.DataServerImplBase {

    private final DataService dataService;

    @Override
    public void addData(GRPCData request, StreamObserver<Empty> responseObserver) {
        Data data = new Data(request);
        dataService.handle(data);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<GRPCData> addStreamOfData(StreamObserver<Empty> responseObserver) {
        return new StreamObserver<GRPCData>() {
            @Override
            public void onNext(GRPCData grpcData) {
                Data data = new Data(grpcData);
                dataService.handle(data);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(Empty.newBuilder().build());
                responseObserver.onCompleted();
            }
        };
    }
}

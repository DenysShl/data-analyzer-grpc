package org.example.grpc.server.service;

import org.example.grpc.server.model.Data;

import java.util.List;

public interface DataService {
    void handle(Data data);

    List<Data> getWithBatchSize(long batchSize);
}

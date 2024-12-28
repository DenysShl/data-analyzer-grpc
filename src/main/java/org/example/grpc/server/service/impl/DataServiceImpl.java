package org.example.grpc.server.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.grpc.server.model.Data;
import org.example.grpc.server.repository.DataRepository;
import org.example.grpc.server.service.DataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataServiceImpl implements DataService {

    private final DataRepository dataRepository;

    @Override
    public void handle(Data data) {
        log.info("Received data: {} and was saved", data);
        dataRepository.save(data);
    }

    @Override
    public List<Data> getWithBatchSize(long batchSize) {
        List<Data> allWithOffSet = dataRepository.findAllWithOffSet(batchSize);
        if (!allWithOffSet.isEmpty()) {
            dataRepository.updateIncrementOffset(Long.min(batchSize, allWithOffSet.size()));
        }
        return allWithOffSet;
    }
}

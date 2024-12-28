package org.example.grpc.server.repository;

import org.example.grpc.server.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DataRepository extends JpaRepository<Data, Long> {

    @Query(value = """
            SELECT *
            FROM data_sensor
            OFFSET (SELECT current_offset FROM data_offset )
            LIMIT :batchSize
            """, nativeQuery = true)
    List<Data> findAllWithOffSet(@Param("batchSize") long batchSize);

    @Modifying
    @Query(value = """
            UPDATE data_offset SET current_offset = current_offset + :batchSize
            """, nativeQuery = true)
    void updateIncrementOffset(@Param("batchSize") long batchSize);
}

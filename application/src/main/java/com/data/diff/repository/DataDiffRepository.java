package com.data.diff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.data.diff.entity.DiffData;

public interface DataDiffRepository extends JpaRepository<DiffData, Integer> {

    /**
     * Gets the corresponding entity DiffData for given object {@code objectId}
     * @param objectId
     * @return
     */
    Optional<DiffData> findByObjectId(Integer objectId);

}

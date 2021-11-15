package com.lime.lime.shop.repository;

import com.lime.lime.shop.model.dto.LimeDTO;
import com.lime.lime.shop.model.entity.LimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LimeRepository extends JpaRepository<LimeEntity,Long> {
    @Query("from LimeEntity l where l.owner.id = :id")
    List<LimeEntity> getAllLimeByProducerId(@Param("id") Long id);
}

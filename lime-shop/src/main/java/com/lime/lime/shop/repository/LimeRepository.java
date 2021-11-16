package com.lime.lime.shop.repository;

import com.lime.lime.shop.model.dto.LimeDTO;
import com.lime.lime.shop.model.entity.LimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LimeRepository extends JpaRepository<LimeEntity,Long> {
    @Query("from LimeEntity l where l.owner.id = :id and  l.isDeleted <> true")
    List<LimeEntity> getAllLimeByProducerId(@Param("id") Long id);

    @Query("select distinct l from LimeEntity l where l.owner.id = :id and l.type = :type  and  l.isDeleted <> true")
    Optional<LimeEntity> getLimeByTypeAndProducerId(@Param("type") String type,@Param("id") Long id);

    @Query("select distinct l from LimeEntity l where l.id = :id and l.isDeleted <> true ")
    Optional<LimeEntity> getLimeById(@Param("id")Long id);
}

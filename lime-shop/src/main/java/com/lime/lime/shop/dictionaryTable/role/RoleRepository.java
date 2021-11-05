package com.lime.lime.shop.dictionaryTable.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Short>{

    @Query("from RoleEntity r where r.roleName = :roleName")
    Optional<RoleEntity> findRoleByName(@Param("roleName") String roleName);

}

package com.lime.lime.shop.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity,Long> {
    @Query("select distinct a from AddressEntity a where a.user.id = :userId")
    AddressEntity getAddressByUserId(@Param("userId") Long userId);

}

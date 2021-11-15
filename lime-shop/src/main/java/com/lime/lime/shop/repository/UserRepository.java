package com.lime.lime.shop.repository;

import com.lime.lime.shop.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long>{
    @Query("select distinct u from UserEntity u where u.username = :username")
    Optional<UserEntity> getUserByUsername(@Param("username") String username);

    @Query("select  distinct  u from  UserEntity u where u.phoneNumber = :number")
    Optional<UserEntity> getUserByPhoneNumber(@Param("number") String number);

    @Query("select  distinct  u from  UserEntity u where u.email = :email")
    Optional<UserEntity> getUserByEmail(@Param("email") String email);
}

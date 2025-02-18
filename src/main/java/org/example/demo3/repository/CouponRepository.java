package org.example.demo3.repository;

import org.example.demo3.entity.Coupon;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends CrudRepository<Coupon, Long> {
    
    @Query("SELECT * FROM t_coupon WHERE state = :state")
    List<Coupon> findByState(@Param("state") Integer state);
    
    @Query("SELECT * FROM t_coupon WHERE name LIKE CONCAT('%', :name, '%')")
    List<Coupon> findByNameContaining(@Param("name") String name);
} 
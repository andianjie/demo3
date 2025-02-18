package org.example.demo3.mapper;

import org.apache.ibatis.annotations.*;
import org.example.demo3.entity.Coupon;
import java.util.List;

@Mapper
public interface CouponMapper {
    
    @Select("SELECT * FROM t_coupon WHERE id = #{id}")
    Coupon findById(@Param("id") Long id);
    
    @Select("SELECT * FROM t_coupon WHERE state = #{state}")
    List<Coupon> findByState(@Param("state") Integer state);
    
    @Select("SELECT * FROM t_coupon WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Coupon> findByNameContaining(@Param("name") String name);
    
    @Insert("INSERT INTO t_coupon(name, money, coupon_desc, create_time, expire_time, state) " +
            "VALUES(#{name}, #{money}, #{couponDesc}, #{createTime}, #{expireTime}, #{state})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Coupon coupon);
    
    @Delete("DELETE FROM t_coupon WHERE id = #{id}")
    int deleteById(@Param("id") Long id);
    
    @Update("UPDATE t_coupon SET state = #{state} WHERE id = #{id}")
    int updateState(@Param("id") Long id, @Param("state") Integer state);
} 
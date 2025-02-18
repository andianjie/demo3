package org.example.demo3.service;

import org.example.demo3.entity.Coupon;
import org.example.demo3.mapper.CouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouponService {
    
    @Autowired
    private CouponMapper couponMapper;
    
    public Coupon save(Coupon coupon) {
        couponMapper.insert(coupon);
        return coupon;
    }
    
    public Optional<Coupon> findById(Long id) {
        return Optional.ofNullable(couponMapper.findById(id));
    }
    
    public List<Coupon> findByState(Integer state) {
        return couponMapper.findByState(state);
    }
    
    public List<Coupon> findByNameContaining(String name) {
        return couponMapper.findByNameContaining(name);
    }
    
    public void deleteById(Long id) {
        couponMapper.deleteById(id);
    }
    
    public boolean updateState(Long id, Integer state) {
        return couponMapper.updateState(id, state) > 0;
    }
} 
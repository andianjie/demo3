package org.example.demo3.controller;

import org.example.demo3.entity.Coupon;
import org.example.demo3.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponController {
    
    @Autowired
    private CouponService couponService;
    
    @PostMapping
    public ResponseEntity<Coupon> create(@RequestBody Coupon coupon) {
        return ResponseEntity.ok(couponService.save(coupon));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Coupon> findById(@PathVariable Long id) {
        return couponService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/state/{state}")
    public ResponseEntity<List<Coupon>> findByState(@PathVariable Integer state) {
        return ResponseEntity.ok(couponService.findByState(state));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Coupon>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(couponService.findByNameContaining(name));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        couponService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 
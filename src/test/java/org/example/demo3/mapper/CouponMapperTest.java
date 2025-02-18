package org.example.demo3.mapper;

import org.example.demo3.entity.Coupon;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CouponMapperTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CouponMapperTest.class);

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private RedisTemplate<String, Object>  redisTemplate;

    @Test
    public void testInsertCoupons() {
        // 创建几个测试优惠券
        Coupon coupon1 = createCoupon("新人优惠券", new BigDecimal("50"), "新用户专享优惠券", 0);
        Coupon coupon2 = createCoupon("满100减20", new BigDecimal("20"), "满100可用", 0);
        Coupon coupon3 = createCoupon("周末特惠券", new BigDecimal("30"), "仅周末可用", 0);
        
        // 插入优惠券
        couponMapper.insert(coupon1);
        couponMapper.insert(coupon2);
        couponMapper.insert(coupon3);
        
        LOGGER.info("插入优惠券成功：");
        LOGGER.info("优惠券1 ID: {}", coupon1.getId());
        LOGGER.info("优惠券2 ID: {}", coupon2.getId());
        LOGGER.info("优惠券3 ID: {}", coupon3.getId());
        
        // 验证插入是否成功
        List<Coupon> validCoupons = couponMapper.findByState(0);
        assertNotNull(validCoupons);
        assertTrue(validCoupons.size() >= 3);
        
        // 查询并打印优惠券信息
        for (Coupon coupon : validCoupons) {
            LOGGER.info("优惠券信息: {}", coupon);
        }
    }

    @Test
    public void testQueryCoupons() {
        // 测试按状态查询
        List<Coupon> validCoupons = couponMapper.findByState(0);
        LOGGER.info("有效优惠券数量: {}", validCoupons.size());
        
        // 测试模糊查询
        List<Coupon> specialCoupons = couponMapper.findByNameContaining("特惠");
        LOGGER.info("特惠优惠券数量: {}", specialCoupons.size());
        
        // 如果有数据，打印第一条记录
        if (!validCoupons.isEmpty()) {
            LOGGER.info("第一张优惠券信息: {}", validCoupons.get(0));
        }}
    @Test
    public void testCouponExpire(){
        Coupon coupon = createCoupon("失效通知特惠券", new BigDecimal("1031"), "测试失效通知", 0);
        couponMapper.insert(coupon);
        redisTemplate.opsForValue().set("coupon:" + coupon.getId(), coupon.toString(),15, TimeUnit.SECONDS);
    }

    @Test
    public void testUpdateState() {
        // 先创建一个优惠券
        Coupon coupon = createCoupon("测试更新状态优惠券", new BigDecimal("100"), "测试更新状态", 1);
        couponMapper.insert(coupon);
        LOGGER.info("创建优惠券成功，ID: {}, 初始状态: {}", coupon.getId(), coupon.getState());
        
        // 更新状态为生效(0)
        int result = couponMapper.updateState(coupon.getId(), 0);
        assertTrue(result > 0);
        
        // 验证更新是否成功
        Coupon updatedCoupon = couponMapper.findById(coupon.getId());
        assertNotNull(updatedCoupon);
        assertEquals(0, updatedCoupon.getState());
        LOGGER.info("优惠券状态更新成功，当前状态: {}", updatedCoupon.getState());
    }

    private Coupon createCoupon(String name, BigDecimal money, String desc, Integer state) {
        Coupon coupon = new Coupon();
        coupon.setName(name);
        coupon.setMoney(money);
        coupon.setCouponDesc(desc);
        coupon.setCreateTime(LocalDateTime.now());
        coupon.setExpireTime(LocalDateTime.now().plusSeconds(15)); // 15s后过期
        coupon.setState(state);
        return coupon;
    }
} 
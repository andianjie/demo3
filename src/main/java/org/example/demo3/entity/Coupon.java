package org.example.demo3.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Coupon {
    private Long id;
    private String name;
    private BigDecimal money;
    private String couponDesc;
    private LocalDateTime createTime;
    private LocalDateTime expireTime;
    private Integer state;

    @Override
    public String toString()
    {
        return "Coupon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                ", couponDesc='" + couponDesc + '\'' +
                ", createTime=" + createTime +
                ", expireTime=" + expireTime +
                ", state=" + state +
                '}';
    }
} 
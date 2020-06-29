/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package jl.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
@Table(name = "jl_mall_order")
public class MallOrder {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name="order_id")
    private Long orderId;

    @Column(name="order_no")
    private String orderNo;

    @Column(name="user_id")
    private Long userId;

    @Column(name="total_price")
    private BigDecimal totalPrice;

    @Column(name="pay_status")
    private Byte payStatus;

    @Column(name="pay_type")
    private Byte payType;

    @Column(name="pay_time")
    private Date payTime;

    @Column(name="order_status")
    private Byte orderStatus;

    @Column(name="extra_info")
    private String extraInfo;

    /**
     * 0:正常  1：禁用   2： 删除
     */
    @Column(name="status")
    private Byte status = 0;

    @Column(name="create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Column(name="update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package jl.mall.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString
@Table(name = "jl_mall_order_address")
public class MallOrderAddress {

    @Id
    @Column(name="order_id")
    private Long orderId;

    @Column(name="user_name")
    private String userName;

    @Column(name="user_phone")
    private String userPhone;

    @Column(name="province_name")
    private String provinceName;

    @Column(name="city_name")
    private String cityName;

    @Column(name="region_name")
    private String regionName;

    @Column(name="detail_address")
    private String detailAddress;
}
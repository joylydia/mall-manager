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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@ToString
@Table(name = "jl_mall_shopping_cart_item")
public class MallShoppingCartItem {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name="cart_item_id")
    private Long cartItemId;

    @Column(name="user_id")
    private Long userId;

    @Column(name="goods_id")
    private Long goodsId;

    @Column(name="goods_count")
    private Integer goodsCount;

    @Column(name="status")
    private Byte status = 0;

    @Column(name="create_time")
    private Date createTime;

    @Column(name="update_time")
    private Date updateTime;
}
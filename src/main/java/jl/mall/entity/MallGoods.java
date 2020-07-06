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

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@ToString
@Table(name = "jl_mall_goods_info")
public class MallGoods extends BaseEntity{

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name="goods_id",nullable = false)
    private Long goodsId;

    @Column(name="goods_name",nullable = false)
    private String goodsName;

    @Column(name="goods_intro",nullable = false)
    private String goodsIntro;

    @Column(name="goods_category_id",nullable = false)
    private Long goodsCategoryId;

    @Column(name="goods_cover_img",nullable = false)
    private String goodsCoverImg;

    @Column(name="goods_carousel",nullable = false)
    private String goodsCarousel;

    @Column(name="original_price",nullable = false)
    private BigDecimal originalPrice;

    @Column(name="selling_price",nullable = false)
    private BigDecimal sellingPrice;

    @Column(name="stock_num",nullable = false)
    private Integer stockNum;

    @Column(name="tag",nullable = false)
    private String tag;

    @Column(name="goods_sell_status",nullable = false)
    private Byte goodsSellStatus;

    @Transient
    private String goodsDetailContent;

}
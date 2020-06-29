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
import java.util.Date;

@Table(name = "jl_mall_goods_category")
@Data
@ToString
public class GoodsCategory extends BaseEntity{

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name="category_id")
    private Long categoryId;

    @Column(name="category_level")
    private Byte categoryLevel;

    @Column(name="parent_id")
    private Long parentId;

    @Column(name="category_name")
    private String categoryName;

    @Column(name="category_rank")
    private Integer categoryRank;

    /**
     * 分类状态： 0： 正常  1：禁用  2：删除
     */
    @Column(name="status")
    private Byte status = 0;


}
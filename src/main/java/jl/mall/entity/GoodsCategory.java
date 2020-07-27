
package jl.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
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

    @Column(name="category_icon")
    private String categoryIcon;

}
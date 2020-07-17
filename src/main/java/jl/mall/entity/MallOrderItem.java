
package jl.mall.entity;

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
@Table(name = "jl_mall_order_item")
public class MallOrderItem {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name="order_item_id")
    private Long orderItemId;

    @Column(name="order_id")
    private Long orderId;

    @Column(name="goods_id")
    private Long goodsId;

    @Column(name="goods_name")
    private String goodsName;

    @Column(name="goods_cover_img")
    private String goodsCoverImg;

    @Column(name="selling_price")
    private BigDecimal sellingPrice;

    @Column(name="goods_count")
    private Integer goodsCount;

    @Column(name="create_time")
    private Date createTime;


}

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
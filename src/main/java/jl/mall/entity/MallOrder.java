
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
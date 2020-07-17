
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
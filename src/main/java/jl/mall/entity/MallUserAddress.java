
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
@Table(name = "jl_mall_user_address")
public class MallUserAddress {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name="address_id")
    private Long addressId;

    @Column(name="user_id")
    private Long userId;

    @Column(name="user_name")
    private String userName;

    @Column(name="user_phone")
    private String userPhone;

    @Column(name="default_flag")
    private Byte defaultFlag;

    @Column(name="province_name")
    private String provinceName;

    @Column(name="city_name")
    private String cityName;

    @Column(name="region_name")
    private String regionName;

    @Column(name="detail_address")
    private String detailAddress;

    /**
     * 状态   0：正常
     */
    @Column(name="status")
    private Byte status = 0;

    @Column(name="create_time")
    private Date createTime;

    @Column(name="update_time")
    private Date updateTime;
}
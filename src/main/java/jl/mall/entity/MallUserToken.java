
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
@Table(name = "jl_mall_user_token")
public class MallUserToken {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name="user_id",nullable = false)
    private Long userId;

    @Column(name="token",nullable = false)
    private String token;

    @Column(name="update_time",nullable = false)
    private Date updateTime;

    @Column(name="expire_time",nullable = false)
    private Date expireTime;
}
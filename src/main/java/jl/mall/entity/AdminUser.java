
package jl.mall.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "jl_mall_admin_user")
@ToString
public class AdminUser {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name="admin_user_id")
    private Integer adminUserId;

    @Column(name="user_name")
    private String userName;

    @Column(name="password")
    private String password;

    @Column(name="nick_name")
    private String nickName;

    @Column(name="status")
    private Byte status = 0;


}
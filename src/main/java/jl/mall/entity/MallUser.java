
package jl.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@ToString
@Table(name = "jl_mall_user")
public class MallUser {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name="user_id",nullable = false)
    private Long userId;

    @Column(name="nick_name")
    private String nickName;

    @Column(name="login_name")
    private String loginName;

    @Column(name="password_md5")
    private String passwordMd5;

    @Column(name="introduce_sign")
    private String introduceSign;

    @Column(name="status")
    private Byte status = 0;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name="create_time")
    private Date createTime;


}
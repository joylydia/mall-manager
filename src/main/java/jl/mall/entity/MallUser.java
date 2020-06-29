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
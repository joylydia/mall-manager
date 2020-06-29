/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
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

package jl.mall.dao;

import jl.mall.common.Mapper;
import jl.mall.entity.AdminUser;
import org.apache.ibatis.annotations.Param;

public interface AdminUserMapper extends Mapper<AdminUser>{

    /**
     * 登陆方法
     *
     * @param userName
     * @param password
     * @return
     */
    AdminUser login(@Param("userName") String userName, @Param("password") String password);

}
package jl.mall.util;

import com.alibaba.fastjson.JSON;
import jl.mall.entity.AdminUser;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author joy
 */
@Slf4j
public class SystemUtil {

    private SystemUtil() {
    }


    /**
     * 登录或注册成功后,生成保持用户登录状态会话token值
     *
     * @param src:为用户最新一次登录时的now()+user.id+random(4)
     * @return
     */
    public static String genToken(String src) {
        if (null == src || "".equals(src)) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(src.getBytes());
            String result = new BigInteger(1, md.digest()).toString(16);
            if (result.length() == 31) {
                result = result + "-";
            }
            System.out.println(result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }


    public static Integer getAdminUserId(){
        AdminUser adminUser = (AdminUser)GlobalThreadLocal.USERINFO.get();
        if(adminUser != null){
            return adminUser.getAdminUserId();
        }
        return null;
    }

}

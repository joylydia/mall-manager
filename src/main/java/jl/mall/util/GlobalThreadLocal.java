package jl.mall.util;

import jl.mall.entity.AdminUser;
import org.springframework.core.NamedThreadLocal;

public class GlobalThreadLocal {

	public static NamedThreadLocal<Object> GLOBALTHREADLOCAL = new NamedThreadLocal<>("globalThreadLocal");
	public static NamedThreadLocal<Object> USERINFO = new NamedThreadLocal<>("userInfo");


}

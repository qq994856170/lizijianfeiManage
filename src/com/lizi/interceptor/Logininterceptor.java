package com.lizi.interceptor;

import javax.servlet.http.HttpSession;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

public class Logininterceptor implements Interceptor {

	@Override
	public void intercept(ActionInvocation ai) {
		// TODO Auto-generated method stub
		HttpSession session=ai.getController().getSession();
		if(session.getAttribute("user_login")==null){
			ai.getController().setAttr("msg", "您还没有登录请登录");
			ai.getController().render("/WEB-INF/manager/login.htm");
		}else{
			ai.invoke();
		}
	}

}

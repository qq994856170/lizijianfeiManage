package com.lizi.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class LoginValidator extends Validator {

	@Override
	protected void handleError(Controller c) {
		c.keepPara();
		c.render("/WEB-INF/manager/login.htm");
	}

	@Override
	protected void validate(Controller c) {
		// TODO Auto-generated method stub
		validateRequiredString("TxtUserName", "nameMsg", "请输入用户名");
		validateRequiredString("TxtPassword", "passMsg", "请输入密码");
	
	}

}

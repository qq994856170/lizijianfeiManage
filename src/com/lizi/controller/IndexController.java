package com.lizi.controller;

import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;
import com.lizi.model.User;
import com.lizi.util.MD5;
import com.lizi.validator.LoginValidator;

public class IndexController extends Controller {
	public void main(){
		setAttr("user_login", getSessionAttr("user_login"));
		render("index.html");
	}
	@ClearInterceptor(ClearLayer.ALL)
	public void index(){
		render("/login.htm");
	}
	@ClearInterceptor(ClearLayer.ALL)
	@Before(LoginValidator.class)
	public void login(){
		String method=getRequest().getMethod();
		if("GET".equals(method.toUpperCase())){
			setAttr("msg", "非法的请求");
			render("/login.htm");
			return;
		}
		String username=getPara("TxtUserName");
		String pwd=getPara("TxtPassword");
		String encode=MD5.MD5(MD5.MD5(username)+pwd);
		User u=User.dao.findFirst("select * from user where username=? and isAdmin=1", username);
		if(u==null){
			keepPara();
			setAttr("msg", "您输入的用户名不存在");
			render("/login.htm");
			return;
		}
		if(!u.getStr("password").equals(encode)){
			keepPara();
			setAttr("msg", "您输入的密码不正确");
			render("/login.htm");
			return;
		}
		setSessionAttr("user_login", u);
		redirect("/index/main");
		
	}
	
	public void  logout(){
		removeSessionAttr("user_login");
		render("/login.htm");
	}

}

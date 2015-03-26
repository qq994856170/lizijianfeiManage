package com.lizi;



import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.lizi.controller.CustomersController;
import com.lizi.controller.GoodsController;
import com.lizi.controller.IndexController;
import com.lizi.interceptor.Logininterceptor;
import com.lizi.model.CustType;
import com.lizi.model.Customer;
import com.lizi.model.Goods;
import com.lizi.model.User;


public class LiziConfig extends JFinalConfig {
	
	
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setViewType(ViewType.FREE_MARKER);
		me.setBaseViewPath("/WEB-INF/manager");
	}
	
	public void configRoute(Routes me) {
		me.add("/index", IndexController.class,"/");
		me.add("/cust", CustomersController.class,"/cust");
		me.add("/goods", GoodsController.class,"/goods");
	}
	
	public void configPlugin(Plugins me) {
		DruidPlugin druid = new DruidPlugin("jdbc:mysql://127.0.0.1/lizi?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull", "ggd", "123456");
		me.add(druid);
		ActiveRecordPlugin arpMysql = new ActiveRecordPlugin("mysql", druid);
		arpMysql.setShowSql(true);
		arpMysql.setDialect(new MysqlDialect());
		arpMysql.addMapping("custType", CustType.class);
		arpMysql.addMapping("customer", Customer.class);
		arpMysql.addMapping("goods", Goods.class);
		arpMysql.addMapping("user", User.class);
		me.add(arpMysql);
		
		EhCachePlugin ecp = new EhCachePlugin();
		me.add(ecp);
	}
	
	public void configInterceptor(Interceptors me) {
		me.add(new Logininterceptor());
	}
	
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler("ctx"));
	}
	
	public static void main(String[] args) {
		//JFinal.start("WebContent", 80, "/", 5);
		//JFinal.start(webAppDir, port, context, scanIntervalSeconds);
	}
}

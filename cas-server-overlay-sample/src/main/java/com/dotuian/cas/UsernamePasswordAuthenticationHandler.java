package com.dotuian.cas;

import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.springframework.util.StringUtils;

/**
 * 自定义的用户验证类
 * 
 * 配置：
 * deployerConfigContext.xml 中 <property name="authenticationHandlers"> </property> 中间添加该Bean
 * 
 * @author SHOU
 *
 */

public class UsernamePasswordAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {

	@Override
	public boolean authenticateUsernamePasswordInternal(final UsernamePasswordCredentials credentials) {
		final String username = credentials.getUsername();
		final String password = credentials.getPassword();

		if (StringUtils.hasText(username) && StringUtils.hasText(password)
				&& username.equals("shoukii") && password.equals("shouadmin")) {
			
			log.debug("User [" + username + "] was successfully authenticated.");
			return true;
		}

		log.debug("User [" + username + "] failed authentication");

		return false;
	}

}

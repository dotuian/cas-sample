package com.dotuian.cas.authority;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jasig.cas.client.validation.Assertion;
import org.springframework.security.cas.userdetails.AbstractCasAssertionUserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsServiceImpl extends AbstractCasAssertionUserDetailsService {

	@Override
	protected UserDetails loadUserDetails(Assertion assertion) {
		final List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
		
        // CAS传递过来的用户信息
        String role = (String)assertion.getPrincipal().getAttributes().get("role");
        if(role != null ) {
        	// 登录的用户的角色判断
        	switch (role) {
        	case "STUDENT":
        		grantedAuthorities.add(new SimpleGrantedAuthority("STUDENT"));
        		break;
        	case "ADMIN":
        		grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
        		break;
        	case "TEACHER":
        		grantedAuthorities.add(new SimpleGrantedAuthority("TEACHER"));
        		break;
        	default:
        		break;
        	}
        }
		
        // 获取CAS Server端传送过来的属性值
		Map<String, String> attributes = assertion.getPrincipal().getAttributes();

		UserIdentity identity = new UserIdentity(assertion.getPrincipal().getName(), "", grantedAuthorities, attributes);  
		System.out.println(identity);
		
		return identity;
	}

}

package com.dotuian.cas;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.jasig.cas.client.validation.Assertion;
import org.springframework.security.cas.userdetails.AbstractCasAssertionUserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsServiceImpl extends AbstractCasAssertionUserDetailsService {

	@Override
	protected UserDetails loadUserDetails(Assertion assertion) {
		final List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
		
		System.out.println("==============================");
		System.out.println("UserDetailsServiceImpl#loadUserDetails");
		System.out.println("==============================");
		
//        // CAS传递过来的用户信息
//        // 用户CODE
//        String yhCd = (String)assertion.getPrincipal().getAttributes().get("userCd");
//        // 用户角色CODE
//        String roleCd = (String)assertion.getPrincipal().getAttributes().get("roleCd");
//        
//        // 登录的用户的角色判断
//        switch (roleCd) {
//			case Constants.USER_ROLE_CODE_STUDENT:
//				// 学生角色
//				grantedAuthorities.add(new SimpleGrantedAuthority(Constants.USER_ROLE_STUDENT));
//				break;
//			case Constants.USER_ROLE_CODE_ADMIN:
//				// 管理员角色
//				grantedAuthorities.add(new SimpleGrantedAuthority(Constants.USER_ROLE_ADMIN));
//				break;
//			default:
//				// 只要不是学生和管理员，就当作教师处理
//				// 老师角色
//				grantedAuthorities.add(new SimpleGrantedAuthority(Constants.USER_ROLE_TEACHER));
//				break;
//		}
		
		String username = "username";
		grantedAuthorities.add(new SimpleGrantedAuthority("Admin"));
		
		return new UserIdentity(assertion.getPrincipal().getName(), "password", grantedAuthorities);
	}

}

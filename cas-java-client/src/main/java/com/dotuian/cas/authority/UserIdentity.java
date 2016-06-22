package com.dotuian.cas.authority;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserIdentity extends User {
	
	public static final String USERNAME = "username";
	public static final String BIRTHDAY = "birthday";
	public static final String SEX = "sex";
	public static final String EMAIL = "email";
	public static final String ID = "id";
	public static final String ROLE = "role";
	
	private static final long serialVersionUID = 1L;

	private String id;
	private String birthday;
	private String sex;
	private String email;
	private String role;


	public UserIdentity(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, Map<String, String> map) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.setUserValue(map);
	}

	public UserIdentity(String username, String password, Collection<? extends GrantedAuthority> authorities, Map<String, String> map) {
		super(username, password, authorities);
		this.setUserValue(map);
	}
	
	private void setUserValue(Map<String, String> map) {
		this.setId(map.get(ID));
		this.setEmail(map.get(EMAIL));
		this.setSex(map.get(SEX));
		this.setBirthday(map.get(BIRTHDAY));
		this.setRole(map.get(ROLE));
	}
	
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserIdentity [id=");
		builder.append(id);
		builder.append(", username=");
		builder.append(this.getUsername());
		builder.append(", birthday=");
		builder.append(birthday);
		builder.append(", sex=");
		builder.append(sex);
		builder.append(", email=");
		builder.append(email);
		builder.append(", role=");
		builder.append(role);
		builder.append("]");
		return builder.toString();
	}
	
}

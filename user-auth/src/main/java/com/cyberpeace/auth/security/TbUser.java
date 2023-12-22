package com.cyberpeace.auth.security;

import java.util.Set;

import org.apache.commons.compress.utils.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cyberpeace.auth.constant.UserRole;
import com.cyberpeace.auth.domain.SysUser;

import lombok.Data;

/**
 * @author SN
 * @date 2023/12/05
 */
@Data
public class TbUser implements UserDetails {

	private String userId;

	private String username;

	private String password;

	private boolean isAdmin;

	private Set<GrantedAuthority> authorities;

	public TbUser(String userId, String username, String password) {
		this.userId = userId;
		this.username = username;
		this.password = password;
	}

	public TbUser(SysUser sysUser) {
		String role = UserRole.USER;
		if (Boolean.TRUE.equals(sysUser.getAdmin())) {
			role = UserRole.ADMIN;
		}
		this.userId = sysUser.getResourceId();
		this.username = sysUser.getUsername();
		this.password = sysUser.getPassword();
		this.roles(role);
		this.isAdmin = sysUser.getAdmin();
	}

	public void roles(String... roles) {
		this.authorities = Sets.newHashSet();
		for (String role : roles) {
			this.authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		}
	}

	public void setRole(boolean isAdmin) {
		String role = UserRole.USER;
		if (isAdmin) {
			role = UserRole.ADMIN;
		}
		this.roles(role);
	}

	@Override
	public Set<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}

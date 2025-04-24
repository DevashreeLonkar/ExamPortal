package com.exam.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@EqualsAndHashCode(exclude = "userRoles")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class Users implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private boolean enabled = true;
	private String profile;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<UserRole> userRoles= new HashSet<>();


//	public String getUserName() {
//	return username;
//	}


//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		
//		Set<SimpleGrantedAuthority> set= new HashSet<>();
//		this.userRoles.forEach(userRole -> {
//			set.add(new SimpleGrantedAuthority(userRole.getRole().getRoleName()));
//		});
//		return null;
//	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    Set<SimpleGrantedAuthority> set = new HashSet<>();
	    this.userRoles.forEach(userRole -> {
	        set.add(new SimpleGrantedAuthority(userRole.getRole().getRoleName()));
	    });
	    return set; // ✅ not null
	}

//	@Override
//	public String getUsername() {
//		return username;
//	}
//	
	
	public boolean isAccountExpired() {
		return true;
	}
	
	public boolean isAccountLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
}

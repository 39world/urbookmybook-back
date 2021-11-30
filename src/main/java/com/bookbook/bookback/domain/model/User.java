package com.bookbook.bookback.domain.model;

import com.bookbook.bookback.domain.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String password;
	private String email;
	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private List<String> roles = new ArrayList<>();
	private String image;
	private String town;
	//    private String address;
	private String comment;
	private String myBook; //추후 구체적으로 수정 필요 ex) JSON or List<String> or List<int>
	private Double star;  //별점 업데이트 추가 필요. 업데이트 시점, 평점 내는 로직 구현
	@Column(name = "point", nullable = true)
	private Long point;

	@ElementCollection
	private List<Long> scrapList;

	// OAuth를 위해 구성한 추가 필드 2개
	private String provider;
	private String providerId;

	@CreationTimestamp
	private Timestamp createdAt;

	//userDetail
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream()
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return email;
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

	public User(UserDto userDto) {
		this.email = userDto.getEmail();
		this.username = userDto.getUsername();
		this.image = userDto.getImage();
		this.point = 0L;
	}

	public void update(UserDto userDto) {
		this.id = userDto.getId();
		this.username = userDto.getUsername();
		this.image = userDto.getImage();
		this.town = userDto.getTown();
		this.comment = userDto.getComment();
	}
}

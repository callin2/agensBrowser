package net.bitnine.domain;

import javax.persistence.*;

/**
 * JPA를 통해 회원정보를 저장하는 엔티티
 * @author 김형우
 *
 */
@Entity
public class Member {

	@Id
	@Column(name="id")
	private String username;

    @Column
	private String password;

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="role_id")
    private MemberRole role;
    

    

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public MemberRole getRole() {
		return role;
	}

	public void setRole(MemberRole role) {
		this.role = role;
	}

	
}











































package net.bitnine.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * JPA를 통해 회원권한정보를 저장하는 엔티티
 * @author 김형우
 *
 */
@Entity
public class MemberRole {

    @Id
	private int roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    protected Role role;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
    
    
}

package net.bitnine.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "fno")
@ToString
public class MemberRole {

	@Id
	private Long fno;
	
	private String roleName;
}

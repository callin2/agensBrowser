package net.bitnine.persistence;

import org.springframework.data.repository.CrudRepository;

import net.bitnine.domain.Member;

public interface MemberRepository extends CrudRepository<Member, String> {

	
}

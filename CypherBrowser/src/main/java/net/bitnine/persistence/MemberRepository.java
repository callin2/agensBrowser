package net.bitnine.persistence;

import net.bitnine.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, String> {

	
}

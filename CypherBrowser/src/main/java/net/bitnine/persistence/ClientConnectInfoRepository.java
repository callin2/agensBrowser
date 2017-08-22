package net.bitnine.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import net.bitnine.domain.ClientConnectInfo;

public interface ClientConnectInfoRepository extends JpaRepository<ClientConnectInfo, String> {

}

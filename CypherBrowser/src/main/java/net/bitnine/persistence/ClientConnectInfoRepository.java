package net.bitnine.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import net.bitnine.domain.ClientConnectInfo;

public interface ClientConnectInfoRepository extends JpaRepository<ClientConnectInfo, String> {
    
    @Query(value = "SELECT * FROM conn_info c, history h WHERE c.state = 'VALID' and c.clientid = h.token_id",
                    nativeQuery=true
    )
    public List<ClientConnectInfo> findValidAll();

}

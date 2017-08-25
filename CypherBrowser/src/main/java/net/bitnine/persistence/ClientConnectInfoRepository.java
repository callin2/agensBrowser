package net.bitnine.persistence;

import net.bitnine.domain.ClientConnectInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientConnectInfoRepository extends JpaRepository<ClientConnectInfo, String> {

	// native queries
    @Query(value = "SELECT * FROM CONN_INFO WHERE state = 'VALID'",
                    nativeQuery=true
    )
    public List<ClientConnectInfo> findValidAll();
    

    @Query(value = "SELECT * FROM CONN_INFO ORDER BY CLIENTID DESC",
                    nativeQuery=true
    )
    public List<ClientConnectInfo> findAllSortByClientId();
    

    @Query(value = "SELECT * FROM CONN_INFO ORDER BY DB_USERNAME ASC",
                    nativeQuery=true
    )
    public List<ClientConnectInfo> findAllSortByUserName();
    

    @Query(value = "SELECT * FROM CONN_INFO ORDER BY CONN_TIME ASC",
                    nativeQuery=true
    )
    public List<ClientConnectInfo> findAllSortByConnectTime();
    
    
    

}

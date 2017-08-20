package net.bitnine.service;

import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.stereotype.Service;

import net.bitnine.domain.dto.DBConnectionInfo;
import net.bitnine.exception.QueryException;
import net.bitnine.util.JDBCTutorialUtilities;

@Service
public class DatabaseService {
    

    public void createPGPoolingDataSource (DBConnectionInfo dataSourceDTO, String tokenString) throws NamingException, QueryException {


        /*BasicDataSource dataSource = new BasicDataSource();
        
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(dataSourceDTO.getUrl());
        dataSource.setUsername(dataSourceDTO.getUsername());
        dataSource.setPassword(dataSourceDTO.getPassword());*/
        
        tokenString = tokenString.replaceAll("\\.","");
        

        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        String[] databaseUrl = dataSourceDTO.getUrl().split("\\/");
        String[] hostName = databaseUrl[2].split("\\:");
        
        dataSource.setDataSourceName("jdbc/" + tokenString);
        dataSource.setServerName(hostName[0]);
        dataSource.setPortNumber(Integer.parseInt(hostName[1]));
        dataSource.setDatabaseName(databaseUrl[3]);
        dataSource.setUser(dataSourceDTO.getUsername());
        dataSource.setPassword(dataSourceDTO.getPassword());
        dataSource.setMaxConnections(10);

        try {
            dataSource.getConnection();

            InitialContext ic = new InitialContext();

            ic.createSubcontext("java:");
            ic.createSubcontext("java:/comp");
            ic.createSubcontext("java:/comp/env");
            ic.createSubcontext("java:/comp/env/jdbc");

            ic.bind("java:/comp/env/jdbc/" + tokenString, dataSource);
            
        } catch (SQLException ex) {

            JDBCTutorialUtilities.printSQLException(ex);
            ex.printStackTrace();
            throw new QueryException (JDBCTutorialUtilities.getSQLState(ex), ex);            
        }
//      dataSource.getConnection();     // 유효한 dataSource인지를 체크

//        new InitialContext().rebind("java:comp/env/jdbc/" + tokenString, dataSource);
//        new InitialContext().bind(tokenString, dataSource);
    }
	
	public DataSource createDataSource (DBConnectionInfo dbConnectionInfo) {
		BasicDataSource dataSource = new BasicDataSource();
		
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl(dbConnectionInfo.getUrl());
		dataSource.setUsername(dbConnectionInfo.getUsername());
		dataSource.setPassword(dbConnectionInfo.getPassword());

        System.out.println("dataSource: " + dataSource);
		return dataSource;
	}

	public void checkValidDataSource(DBConnectionInfo dbConnectionInfo) throws SQLException {
		try {
			createDataSource(dbConnectionInfo).getConnection();
    	}
    	catch (SQLException ex) {
            JDBCTutorialUtilities.printSQLException(ex);
            ex.printStackTrace();
            throw new QueryException (JDBCTutorialUtilities.getSQLState(ex), ex);       // custom exception 사용.
    	}			
	}
	
	/*public void createDataSource(DataSourceDTO dto) throws NamingException {
	    PGPoolingDataSource dataSource = new PGPoolingDataSource();
	    source.setDataSourceName("A Data Source");
	    source.setServerName("localhost");
	    source.setDatabaseName("test");
	    source.setUser("testuser");
	    source.setPassword("testpassword");
	    source.setMaxConnections(10);

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(dto.getUrl());
        dataSource.setUser(dto.getUsername());
        dataSource.setPassword(dto.getPassword());
        
	    new InitialContext().rebind("DataSource", source);
	}*/
}

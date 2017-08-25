package net.bitnine.util;

import net.bitnine.domain.DataMeta;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MetaDataUtils {


    /**
     * ResultSetMetaData을 사용하여 메타 데이터를 생성하는 메소드
     * @param data
     * @return
     * @throws SQLException
     */
	public static List<DataMeta> getMetaDataList (ResultSetMetaData data) throws SQLException {
		List<DataMeta> dataMetaList = new ArrayList<>();
    	
        for(int i = 1 ; i <=data.getColumnCount() ; i++){
        	
        	String columnLabel = data.getColumnLabel(i);
            String columnTypeName = getChangedType(data.getColumnTypeName(i));      // 컬럼타입 단순화
//    		System.out.println("columnTypeName: " + columnTypeName);
    		
        	boolean isReadOnly = data.isReadOnly(i); 
        	
        	DataMeta dataMeta = new DataMeta (columnLabel, columnTypeName, isReadOnly);
        	
        	dataMetaList.add(dataMeta);
        }
		return dataMetaList;
	}

	/**
	 * 메타 데이터 컬럼 타입을 단순화
	 * @param columnTypeName
	 * @return
	 */
    private static String getChangedType(String columnTypeName) {
        String changedType = "";
        if ( columnTypeName.equals("int2") || columnTypeName.equals("int4") || columnTypeName.equals("int8") ) {
            changedType = "int";
        }
        else if ( columnTypeName.equals("numeric") || columnTypeName.equals("float4")  || columnTypeName.equals("float8")  || columnTypeName.equals("serial")  || columnTypeName.equals("bigserial") ) {
            changedType = "double";
        }
        else {
            changedType = columnTypeName;
        }
        return changedType;
    }
}




























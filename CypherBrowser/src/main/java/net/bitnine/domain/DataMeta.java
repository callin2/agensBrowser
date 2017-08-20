package net.bitnine.domain;

public class DataMeta {	
	String columnLabel;
	String columnTypeName;
	boolean isReadOnly;
	
	public DataMeta() {
	}

	public DataMeta(String columnLabel, String columnTypeName, boolean isReadOnly) {
		this.columnLabel = columnLabel;
		this.columnTypeName = columnTypeName;
		this.isReadOnly = isReadOnly;
	}

	public String getColumnLabel() {
		return columnLabel;
	}

	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}

	public String getColumnTypeName() {
		return columnTypeName;
	}

	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}

	public boolean isReadOnly() {
		return isReadOnly;
	}

	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}
	
	
}

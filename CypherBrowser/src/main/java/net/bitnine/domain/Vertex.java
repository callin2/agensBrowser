package net.bitnine.domain;

import org.json.simple.JSONObject;

public class Vertex  {

	private String id;
	private String type;
	private String name;
	private JSONObject props;

    @SuppressWarnings("WeakerAccess")
    public Vertex() {
        setType("vertex");
    }
    
	public Vertex(String id, String type, String name, JSONObject props) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.props = props;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JSONObject getProps() {
		return props;
	}

	public void setProps(JSONObject props) {
		this.props = props;
	}
	
	
	
}

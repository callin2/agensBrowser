package net.bitnine.domain;

import java.math.BigDecimal;
import java.util.List;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "rProps", "pProps" })
public class Edge  {

	private String id;
	private String type;
	private String name;
	private String source;
	private String target;
	private JSONObject  props;
	
	public Edge() {
	}

	public Edge(String id, String type, String name, String source, String target, JSONObject props) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.source = source;
		this.target = target;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public JSONObject getProps() {
		return props;
	}

	public void setProps(JSONObject props) {
		this.props = props;
	}
	
	
}

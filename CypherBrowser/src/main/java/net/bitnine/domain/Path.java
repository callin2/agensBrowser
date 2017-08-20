package net.bitnine.domain;

import java.util.List;

public class Path {

	private String source;
	private String target;

	private List<Vertex> nodes;
	private List<Edge> edges;

	public Path() {
	}

	public Path(String source, String target, List<Vertex> nodes, List<Edge> edges) {
		this.source = source;
		this.target = target;
		this.nodes = nodes;
		this.edges = edges;
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

	public List<Vertex> getNodes() {
		return nodes;
	}

	public void setNodes(List<Vertex> nodes) {
		this.nodes = nodes;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	
	
}

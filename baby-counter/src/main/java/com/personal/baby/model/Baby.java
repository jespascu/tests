package com.personal.baby.model;

public class Baby {
	
	Integer id;
	String name;
	Integer numPoo;
	Integer numCry;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumPoo() {
		return numPoo;
	}
	public void setNumPoo(Integer numPoo) {
		this.numPoo = numPoo;
	}
	public Integer getNumCry() {
		return numCry;
	}
	public void setNumCry(Integer numCry) {
		this.numCry = numCry;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Baby [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", numPoo=");
		builder.append(numPoo);
		builder.append(", numCry=");
		builder.append(numCry);
		builder.append("]");
		return builder.toString();
	}

}

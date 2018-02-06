package com.dropsnorz.showcasefx.example.pets;

public class PetType {
	
	protected String name;
	protected String icon;
	
	public PetType(String name, String icon) {
		super();
		this.name = name;
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	

}

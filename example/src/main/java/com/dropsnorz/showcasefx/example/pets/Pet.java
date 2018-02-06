package com.dropsnorz.showcasefx.example.pets;

public class Pet {
	
	protected PetType type;
	protected String name;
	
	
	public Pet(PetType type, String name) {
		super();
		this.type = type;
		this.name = name;
	}


	public PetType getType() {
		return type;
	}


	public void setType(PetType type) {
		this.type = type;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
	
	
	

}

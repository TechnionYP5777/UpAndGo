package model;

public class Faculty {

	private String id;
	private String name;
		
	public Faculty(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object ¢){
		return ¢ != null && (¢ == this || (¢ instanceof Faculty && (this.id.equals(((Faculty) ¢).getId())) && (this.name.equals(((Faculty) ¢).getName()))));
	}
	

}

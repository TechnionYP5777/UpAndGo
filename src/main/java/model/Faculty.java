package model;

/**
 * 
 * @author Yaniv Levinsky
 * @since 07-01-17
 * 
 * Class that holds information about specific faculty.
 * 
 */
public class Faculty {

	private String id;
	private String name;

	public Faculty(final String id, final String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public boolean equals(final Object ¢) {
		return ¢ != null && (¢ == this
				|| ¢ instanceof Faculty && id.equals(((Faculty) ¢).getId()) && name.equals(((Faculty) ¢).getName()));
	}

}

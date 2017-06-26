package upandgo.shared.entities;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum Semester implements IsSerializable {
	WINTER16("חורף 2016/17 תשע\"ז","201602"),
	SUMMER16("קיץ 2016/17 תשע\"ז","201603");
	
	private final String name;
	private final String id;
	
	private Semester(final String name, final String id){
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
	
}

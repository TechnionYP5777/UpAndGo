package upandgo.shared.entities;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum Semester implements IsSerializable {
	WINTER17("חורף 2017/18 תשע\"ח","201701"),
	SUMMER16("קיץ 2016/17 תשע\"ז","201603"),
	SPRING16("אביב 2016/17 תשע\"ז","201602");
	
	private final String name;
	private final String id;
	
	private Semester(){
		this.name = "";
		this.id = "";
	}
	
	private Semester(final String name, final String id){
		this.name = name;
		this.id = id;
	}
	
	public static Semester fromId(String id){
		for (Semester semester : Semester.values())
			if (semester.id.equals(id))
				return semester;
		return null;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
	
}

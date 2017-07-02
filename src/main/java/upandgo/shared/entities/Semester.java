package upandgo.shared.entities;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum Semester implements IsSerializable {
	WINTER17("חורף 2017/18 תשע\"ח","201701","22/10/2017","25/01/2018"),
	SUMMER16("קיץ 2016/17 תשע\"ז","201603","02/08/2017","18/09/2017"),
	SPRING16("אביב 2016/17 תשע\"ז","201602","20/03/2017","04/07/2017");
	
	private final String name;
	private final String id;
	private final String startDate;
	private final String endDate;
	
	private Semester(){
		this.name = "";
		this.id = "";
		this.startDate = "";
		this.endDate = "";
	}
	
	private Semester(final String name, final String id, final String startDate, final String endDate){
		this.name = name;
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}
	
	
	
}

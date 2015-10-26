package util;

public enum EPlatform {

	Any("any"),
	Linux("linux"),
	Mac_OS("Mac OS"),
	Mac_OS_X("Mac OS X"),
	Windows("windows"),
	OS2("OS2"),
	Solaris("Solaris"),
	SunOS("SunOS"),
	MPEiX("MP/ix"),
	HP_UX("HP-UX"),
	AIX("AIX"),
	OS390("OS/390"),
	FreeBSD("FreeBSD"),
	Irix("Irix"),
	Digital_Unix("Digital_Unix"),
	 NetWare_411("NetWare"), 
	 OSF1("OSF1"), 
	 OpenVMS("OpenVMS"),
	 Others("Others");  
	
	EPlatform(String description){
		this.description = description;
	}
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}

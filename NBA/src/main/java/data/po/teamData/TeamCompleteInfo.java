package data.po.teamData;

public class TeamCompleteInfo {
	   TeamBaseInfo baseinfo = new TeamBaseInfo();
	   TeamLData LData = new TeamLData();
	   TeamHData HData = new TeamHData();
	   OtherTeamData OtherTeam= new OtherTeamData();
	   
	public TeamBaseInfo getBaseinfo() {
		return baseinfo;
	}
	public void setBaseinfo(TeamBaseInfo baseinfo) {
		this.baseinfo = baseinfo;
	}
	public TeamLData getLData() {
		return LData;
	}
	public void setLData(TeamLData lData) {
		LData = lData;
	}
	public TeamHData getHData() {
		return HData;
	}
	public void setHData(TeamHData hData) {
		HData = hData;
	}
	public OtherTeamData getOtherTeam() {
		return OtherTeam;
	}
	public void setOtherTeam(OtherTeamData otherTeam) {
		OtherTeam = otherTeam;
	}
	   
	   

}

package data.po.matchData;

public class MatchDataSeason {
	boolean isSeason;
	String Date;
	String season; // 形式如85-86
	String Team; // 主队
	String OtherTeam; // 客队
	String MathcID;

	String[] point = new String[10]; // point[0] 是总比分，后面分别是每节比赛比分


	
	
	public boolean isSeason() {
		return isSeason;
	}
	public void setSeason(boolean isSeason) {
		this.isSeason = isSeason;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getTeam() {
		return Team;
	}
	public void setTeam(String team) {
		Team = team;
	}
	public String getOtherTeam() {
		return OtherTeam;
	}
	public void setOtherTeam(String otherTeam) {
		OtherTeam = otherTeam;
	}
	public String getMathcID() {
		return MathcID;
	}
	public void setMathcID(String mathcID) {
		MathcID = mathcID;
	}
	public String[] getPoint() {
		return point;
	}
	public void setPoint(String[] point) {
		this.point = point;
	}


	
}

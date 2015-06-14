package data.po.matchData;

import java.util.ArrayList;

public class MatchDataSeason {
	String isSeason;
	String Date;
	String season; // 形式如85-86
	String Team; // 主队
	String OtherTeam; // 客队
	String MatchID;
	String[] point = new String[10]; // point[0] 是总比分，后面分别是每节比赛比分

	ArrayList<MatchPlayer> teamPlayer = new ArrayList<MatchPlayer>();
	ArrayList<MatchPlayer> otherteamPlayer = new ArrayList<MatchPlayer>();
	
	MatchTeam teamdata = new MatchTeam();
	MatchTeam otherteamdata = new MatchTeam();
	
	
	
	
	
	public ArrayList<MatchPlayer> getTeamPlayer() {
		return teamPlayer;
	}

	public void setTeamPlayer(ArrayList<MatchPlayer> teamPlayer) {
		this.teamPlayer = teamPlayer;
	}

	public ArrayList<MatchPlayer> getOtherteamPlayer() {
		return otherteamPlayer;
	}

	public void setOtherteamPlayer(ArrayList<MatchPlayer> otherteamPlayer) {
		this.otherteamPlayer = otherteamPlayer;
	}

	public MatchTeam getTeamdata() {
		return teamdata;
	}

	public void setTeamdata(MatchTeam teamdata) {
		this.teamdata = teamdata;
	}

	public MatchTeam getOtherteamdata() {
		return otherteamdata;
	}

	public void setOtherteamdata(MatchTeam otherteamdata) {
		this.otherteamdata = otherteamdata;
	}

	public String getPoints(){
		String res="";
		for(int i=0;i<point.length;i++){
			res=res+point[i]+";" ;
		}
		return res;
	}
	
	public String[] getpointArray(String points){
		String[] pointArray=points.split(";");
		return pointArray;
	}
	
	public String getisSeason() {
		return isSeason;
	}
	public void setisSeason(String isSeason) {
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
	public String getMatchID() {
		return MatchID;
	}
	public void setMatchID(String matchID) {
		MatchID = matchID;
	}
	public String[] getPoint() {
		return point;
	}
	public void setPoint(String[] point) {
		this.point = point;
	}


	
}

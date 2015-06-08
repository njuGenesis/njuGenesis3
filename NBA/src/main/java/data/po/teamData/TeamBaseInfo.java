package data.po.teamData;

public class TeamBaseInfo {
	String Name;// 队名
	  String ShortName;
	  String Season;//赛季   形式如13_14
	  String IsSeason;
	  String City;// 城市
    String EorW;// 东西部
 	  String Area;// 分区
	  String Mainposition;// 场馆
	  String Players; // 球员，以英文分号隔开 形如 xxx;xxx;xxx;。。。;
	  int Buildyear;// 年份
	  
	  
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getShortName() {
		return ShortName;
	}
	public void setShortName(String shortName) {
		ShortName = shortName;
	}
	public String getSeason() {
		return Season;
	}
	public void setSeason(String season) {
		Season = season;
	}
	public String getIsSeason() {
		return IsSeason;
	}
	public void setIsSeason(String isSeason) {
		IsSeason = isSeason;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getEorW() {
		return EorW;
	}
	public void setEorW(String eorW) {
		EorW = eorW;
	}
	public String getArea() {
		return Area;
	}
	public void setArea(String area) {
		Area = area;
	}
	public String getMainposition() {
		return Mainposition;
	}
	public void setMainposition(String mainposition) {
		Mainposition = mainposition;
	}
	public String getPlayers() {
		return Players;
	}
	public void setPlayers(String players) {
		Players = players;
	}
	public int getBuildyear() {
		return Buildyear;
	}
	public void setBuildyear(int buildyear) {
		Buildyear = buildyear;
	}
	  
	  
}

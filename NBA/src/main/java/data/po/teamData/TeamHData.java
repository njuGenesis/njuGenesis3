package data.po.teamData;

public class TeamHData {
	String shortName;
	String season;
	String isseason;
	 double Off;// 进攻回合
     double Def;//防守回合
     double OffEff;// 进攻效率
     double DefEff;// 防守效率
 	 double OffBackBoardEff;// 进攻篮板效率
  	 double BackBoardEff;// 篮板效率
  	 double DefBackBoardEff;// 防守篮板效率
 	 double StealEff;// 抢断效率
    double AssistEff;// 助攻率
    
    
    
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getIsseason() {
		return isseason;
	}
	public void setIsseason(String isseason) {
		this.isseason = isseason;
	}
	public double getOff() {
		return Off;
	}
	public void setOff(double off) {
		Off = off;
	}
	public double getDef() {
		return Def;
	}
	public void setDef(double def) {
		Def = def;
	}
	public double getOffEff() {
		return OffEff;
	}
	public void setOffEff(double offEff) {
		OffEff = offEff;
	}
	public double getDefEff() {
		return DefEff;
	}
	public void setDefEff(double defEff) {
		DefEff = defEff;
	}
	public double getOffBackBoardEff() {
		return OffBackBoardEff;
	}
	public void setOffBackBoardEff(double offBackBoardEff) {
		OffBackBoardEff = offBackBoardEff;
	}
	public double getBackBoardEff() {
		return BackBoardEff;
	}
	public void setBackBoardEff(double backBoardEff) {
		BackBoardEff = backBoardEff;
	}
	public double getDefBackBoardEff() {
		return DefBackBoardEff;
	}
	public void setDefBackBoardEff(double defBackBoardEff) {
		DefBackBoardEff = defBackBoardEff;
	}
	public double getStealEff() {
		return StealEff;
	}
	public void setStealEff(double stealEff) {
		StealEff = stealEff;
	}
	public double getAssistEff() {
		return AssistEff;
	}
	public void setAssistEff(double assistEff) {
		AssistEff = assistEff;
	}
    
}

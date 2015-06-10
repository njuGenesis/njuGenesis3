package data.po;

public class TeamData_Avg_PO {
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
      
//本队数据
  	  //基础数据
      double MatchNumber;// 比赛场数
      double WinMatch; // 胜场数
      double Winrate;//胜率
      double PPG;//场均得分

      
      
      double ShootEff;// 投篮命中率
      double ShootNumberPG;// 场均投篮
      double ShootEffNumberPG;// 场均投篮命中数
      
      double TPEff;// 三分命中率
      double TPNumberPG;// 场均三分出手数
      double TPEffNumberPG;// 场均三分命中数
      
      
      double FTEff;// 罚球命中率
      double FTNumberPG;// 场均罚球数
      double FTEffNumberPG;// 场均罚球命中数
      
      
      double BackBoardPG;// 场均篮板数
      double OffBackBoardPG;// 场均进攻篮板
      double DefBackBoardPG;// 场均防守篮板
      
      double AssitNumberPG;//场均助攻数
      double StealNumberPG;//场均抢断数
      double RejectionPG;//场均盖帽数
      double ToPG;//场均失误数
      double FoulPG;//场均犯规数
      
      //本队高阶数据
      double Off;// 进攻回合
      double Def;//防守回合
      double OffEff;// 进攻效率
      double DefEff;// 防守效率
  	 double OffBackBoardEff;// 进攻篮板效率
   	 double BackBoardEff;// 篮板效率
   	 double DefBackBoardEff;// 防守篮板效率
  	 double StealEff;// 抢断效率
     double AssistEff;// 助攻率
      
      
      
      
 //对手球队数据
      //基础数据
      double OtherMatchNumber;// 比赛场数
      double OtherWinMatch; // 胜场数
      double OtherWinrate;//胜率
      double OtherPPG;//场均得分

     
      
      double OtherShootEff;// 投篮命中率
      double OtherShootNumberPG;// 场均投篮
      double OtherShootEffNumberPG;// 场均投篮命中数
      
      double OtherTPEff;// 三分命中率
      double OtherTPNumberPG;// 场均三分出手数
      double OtherTPEffNumberPG;// 场均三分命中数
      
      
      double OtherFTEff;// 罚球命中率
      double OtherFTNumberPG;// 场均罚球数
      double OtherFTEffNumberPG;// 场均罚球命中数
      
      
      double OtherBackBoardPG;// 场均篮板数
      double OtherOffBackBoardPG;// 场均进攻篮板
      double OtherDefBackBoardPG;// 场均防守篮板
      
      double OtherAssitNumberPG;//场均助攻数
      double OtherStealNumberPG;//场均抢断数
      double OtherRejectionPG;//场均盖帽数
      double OtherToPG;//场均失误数
      double OtherFoulPG;//场均犯规数   

      
      
	public String getIsSeason() {
		return IsSeason;
	}
	public void setIsSeason(String isSeason) {
		IsSeason = isSeason;
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
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
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
	public double getMatchNumber() {
		return MatchNumber;
	}
	public void setMatchNumber(double matchNumber) {
		MatchNumber = matchNumber;
	}
	public double getWinMatch() {
		return WinMatch;
	}
	public void setWinMatch(double winMatch) {
		WinMatch = winMatch;
	}
	public double getWinrate() {
		return Winrate;
	}
	public void setWinrate(double winrate) {
		Winrate = winrate;
	}
	public double getPPG() {
		return PPG;
	}
	public void setPPG(double pPG) {
		PPG = pPG;
	}

	public double getShootEff() {
		return ShootEff;
	}
	public void setShootEff(double shootEff) {
		ShootEff = shootEff;
	}
	public double getShootNumberPG() {
		return ShootNumberPG;
	}
	public void setShootNumberPG(double shootNumberPG) {
		ShootNumberPG = shootNumberPG;
	}
	public double getShootEffNumberPG() {
		return ShootEffNumberPG;
	}
	public void setShootEffNumberPG(double shootEffNumberPG) {
		ShootEffNumberPG = shootEffNumberPG;
	}
	public double getTPEff() {
		return TPEff;
	}
	public void setTPEff(double tPEff) {
		TPEff = tPEff;
	}
	public double getTPNumberPG() {
		return TPNumberPG;
	}
	public void setTPNumberPG(double tPNumberPG) {
		TPNumberPG = tPNumberPG;
	}
	public double getTPEffNumberPG() {
		return TPEffNumberPG;
	}
	public void setTPEffNumberPG(double tPEffNumberPG) {
		TPEffNumberPG = tPEffNumberPG;
	}
	public double getFTEff() {
		return FTEff;
	}
	public void setFTEff(double fTEff) {
		FTEff = fTEff;
	}
	public double getFTNumberPG() {
		return FTNumberPG;
	}
	public void setFTNumberPG(double fTNumberPG) {
		FTNumberPG = fTNumberPG;
	}
	public double getFTEffNumberPG() {
		return FTEffNumberPG;
	}
	public void setFTEffNumberPG(double fTEffNumberPG) {
		FTEffNumberPG = fTEffNumberPG;
	}
	public double getBackBoardPG() {
		return BackBoardPG;
	}
	public void setBackBoardPG(double backBoardPG) {
		BackBoardPG = backBoardPG;
	}
	public double getOffBackBoardPG() {
		return OffBackBoardPG;
	}
	public void setOffBackBoardPG(double offBackBoardPG) {
		OffBackBoardPG = offBackBoardPG;
	}
	public double getDefBackBoardPG() {
		return DefBackBoardPG;
	}
	public void setDefBackBoardPG(double defBackBoardPG) {
		DefBackBoardPG = defBackBoardPG;
	}
	public double getAssitNumberPG() {
		return AssitNumberPG;
	}
	public void setAssitNumberPG(double assitNumberPG) {
		AssitNumberPG = assitNumberPG;
	}
	public double getStealNumberPG() {
		return StealNumberPG;
	}
	public void setStealNumberPG(double stealNumberPG) {
		StealNumberPG = stealNumberPG;
	}
	public double getRejectionPG() {
		return RejectionPG;
	}
	public void setRejectionPG(double rejectionPG) {
		RejectionPG = rejectionPG;
	}
	public double getToPG() {
		return ToPG;
	}
	public void setToPG(double toPG) {
		ToPG = toPG;
	}
	public double getFoulPG() {
		return FoulPG;
	}
	public void setFoulPG(double foulPG) {
		FoulPG = foulPG;
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
	public double getOtherMatchNumber() {
		return OtherMatchNumber;
	}
	public void setOtherMatchNumber(double otherMatchNumber) {
		OtherMatchNumber = otherMatchNumber;
	}
	public double getOtherWinMatch() {
		return OtherWinMatch;
	}
	public void setOtherWinMatch(double otherWinMatch) {
		OtherWinMatch = otherWinMatch;
	}
	public double getOtherWinrate() {
		return OtherWinrate;
	}
	public void setOtherWinrate(double otherWinrate) {
		OtherWinrate = otherWinrate;
	}
	public double getOtherPPG() {
		return OtherPPG;
	}
	public void setOtherPPG(double otherPPG) {
		OtherPPG = otherPPG;
	}
	
	public double getOtherShootEff() {
		return OtherShootEff;
	}
	public void setOtherShootEff(double otherShootEff) {
		OtherShootEff = otherShootEff;
	}
	public double getOtherShootNumberPG() {
		return OtherShootNumberPG;
	}
	public void setOtherShootNumberPG(double otherShootNumberPG) {
		OtherShootNumberPG = otherShootNumberPG;
	}
	public double getOtherShootEffNumberPG() {
		return OtherShootEffNumberPG;
	}
	public void setOtherShootEffNumberPG(double otherShootEffNumberPG) {
		OtherShootEffNumberPG = otherShootEffNumberPG;
	}
	public double getOtherTPEff() {
		return OtherTPEff;
	}
	public void setOtherTPEff(double otherTPEff) {
		OtherTPEff = otherTPEff;
	}
	public double getOtherTPNumberPG() {
		return OtherTPNumberPG;
	}
	public void setOtherTPNumberPG(double otherTPNumberPG) {
		OtherTPNumberPG = otherTPNumberPG;
	}
	public double getOtherTPEffNumberPG() {
		return OtherTPEffNumberPG;
	}
	public void setOtherTPEffNumberPG(double otherTPEffNumberPG) {
		OtherTPEffNumberPG = otherTPEffNumberPG;
	}
	public double getOtherFTEff() {
		return OtherFTEff;
	}
	public void setOtherFTEff(double otherFTEff) {
		OtherFTEff = otherFTEff;
	}
	public double getOtherFTNumberPG() {
		return OtherFTNumberPG;
	}
	public void setOtherFTNumberPG(double otherFTNumberPG) {
		OtherFTNumberPG = otherFTNumberPG;
	}
	public double getOtherFTEffNumberPG() {
		return OtherFTEffNumberPG;
	}
	public void setOtherFTEffNumberPG(double otherFTEffNumberPG) {
		OtherFTEffNumberPG = otherFTEffNumberPG;
	}
	public double getOtherBackBoardPG() {
		return OtherBackBoardPG;
	}
	public void setOtherBackBoardPG(double otherBackBoardPG) {
		OtherBackBoardPG = otherBackBoardPG;
	}
	public double getOtherOffBackBoardPG() {
		return OtherOffBackBoardPG;
	}
	public void setOtherOffBackBoardPG(double otherOffBackBoardPG) {
		OtherOffBackBoardPG = otherOffBackBoardPG;
	}
	public double getOtherDefBackBoardPG() {
		return OtherDefBackBoardPG;
	}
	public void setOtherDefBackBoardPG(double otherDefBackBoardPG) {
		OtherDefBackBoardPG = otherDefBackBoardPG;
	}
	public double getOtherAssitNumberPG() {
		return OtherAssitNumberPG;
	}
	public void setOtherAssitNumberPG(double otherAssitNumberPG) {
		OtherAssitNumberPG = otherAssitNumberPG;
	}
	public double getOtherStealNumberPG() {
		return OtherStealNumberPG;
	}
	public void setOtherStealNumberPG(double otherStealNumberPG) {
		OtherStealNumberPG = otherStealNumberPG;
	}
	public double getOtherRejectionPG() {
		return OtherRejectionPG;
	}
	public void setOtherRejectionPG(double otherRejectionPG) {
		OtherRejectionPG = otherRejectionPG;
	}
	public double getOtherToPG() {
		return OtherToPG;
	}
	public void setOtherToPG(double otherToPG) {
		OtherToPG = otherToPG;
	}
	public double getOtherFoulPG() {
		return OtherFoulPG;
	}
	public void setOtherFoulPG(double otherFoulPG) {
		OtherFoulPG = otherFoulPG;
	}
      
   
     

      
}

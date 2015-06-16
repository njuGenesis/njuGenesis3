package data.po.matchData;

public class MatchTeam {
	String MatchID;
	String isseason;
	String season;
	String date;
	String twoteam;   //客队-主队
	String result;//结果比分    客队得分-主队得分
	String TeamShortName;
	String playerNumber;//上场人数
	String points; // 得分
	
	String ShootEff;// 投篮命中率
	String Shoot; // 投球数
	String ShootEffNumber; // 投篮命中数
	
	String TPShootEff;// 三分命中率
	String TPShoot; // 三分投篮数
	String TPShootEffNumber; // 三分命数
	
	String FTShootEff;// 罚球命中率
	String FT; // 罚球数
	String FTShootEffNumber; // 罚篮命中数
	
	String Bank; // 篮板
	String BankOff; // 进攻篮板
	String BankDef; // 防守篮板
	String realEff;//真实命中率
	
	String Ass; // 助攻数
	String Steal;// 抢断数
	String Rejection;// 盖帽数
	String Foul;// 犯规数
	String To; // 失误
	
	
    public double getproperty(String property){
   	 if(property.equals("TPShootEff")){
   		 return Double.valueOf(getTPShootEff().replaceAll("%", ""));
   	 }
   	else if(property.equals("TPShoot")){
  		 return Double.valueOf(getTPShoot());
  	 }
   	else if(property.equals("TPShootEffNumber")){
  		 return Double.valueOf(getTPShootEffNumber());
  	 }
   	 else if(property.equals("realEff")){
   		 return Double.valueOf(getRealEff().replaceAll("%", ""));
   	 }
   	 else if(property.equals("ShootEff")){
   		 return Double.valueOf(getShootEff().replaceAll("%", ""));
   	 }
   	 else if(property.equals("Shoot")){
   		 return Double.valueOf(getShoot());
   	 }
   	 else if(property.equals("ShootEffNumber")){
   		 return Double.valueOf(getShootEffNumber());
   	 }
   	 else if(property.equals("FTShootEff")){
   		 return Double.valueOf(getFTShootEff().replaceAll("%", ""));
   	 }
   	 else if(property.equals("FT")){
   		 return Double.valueOf(getFT());
   	 }
   	 else if(property.equals("FTShootEffNumber")){
   		 return Double.valueOf(getFTShootEffNumber());
   	 }
   	 else if(property.equals("Bank")){
   		 return Double.valueOf(getBank());
   	 }
   	 else if(property.equals("Ass")){
   		 return Double.valueOf(getAss());
   	 }
   	 else if(property.equals("Steal")){
   		 return Double.valueOf(getSteal());
   	 }
   	 else if(property.equals("Rejection")){
   		 return Double.valueOf(getRejection());
   	 }
   	 else if(property.equals("Foul")){
   		 return Double.valueOf(getFoul());
   	 }
   	 else if(property.equals("To")){
   		 return Double.valueOf(getTo());
   	 }
   	 else if(property.equals("points")){
   		 return Double.valueOf(getPoints());
   	 }

   	 return 0;
    }
   
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getTwoteam() {
		return twoteam;
	}
	public void setTwoteam(String twoteam) {
		this.twoteam = twoteam;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getIsseason() {
		return isseason;
	}
	public void setIsseason(String isseason) {
		this.isseason = isseason;
	}
	public String getMatchID() {
		return MatchID;
	}
	public void setMatchID(String matchID) {
		MatchID = matchID;
	}
	public String getTeamShortName() {
		return TeamShortName;
	}
	public void setTeamShortName(String teamShortName) {
		TeamShortName = teamShortName;
	}
	public String getPlayerNumber() {
		return playerNumber;
	}
	public void setPlayerNumber(String playerNumber) {
		this.playerNumber = playerNumber;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getRealEff() {
		return realEff;
	}
	public void setRealEff(String realEff) {
		this.realEff = realEff;
	}
	public String getBank() {
		return Bank;
	}
	public void setBank(String bank) {
		Bank = bank;
	}
	public String getBankOff() {
		return BankOff;
	}
	public void setBankOff(String bankOff) {
		BankOff = bankOff;
	}
	public String getBankDef() {
		return BankDef;
	}
	public void setBankDef(String bankDef) {
		BankDef = bankDef;
	}
	public String getFT() {
		return FT;
	}
	public void setFT(String fT) {
		FT = fT;
	}
	public String getFTShootEff() {
		return FTShootEff;
	}
	public void setFTShootEff(String fTShootEff) {
		FTShootEff = fTShootEff;
	}
	public String getFTShootEffNumber() {
		return FTShootEffNumber;
	}
	public void setFTShootEffNumber(String fTShootEffNumber) {
		FTShootEffNumber = fTShootEffNumber;
	}
	public String getShoot() {
		return Shoot;
	}
	public void setShoot(String shoot) {
		Shoot = shoot;
	}
	public String getShootEffNumber() {
		return ShootEffNumber;
	}
	public void setShootEffNumber(String shootEffNumber) {
		ShootEffNumber = shootEffNumber;
	}
	public String getShootEff() {
		return ShootEff;
	}
	public void setShootEff(String shootEff) {
		ShootEff = shootEff;
	}
	public String getTPShoot() {
		return TPShoot;
	}
	public void setTPShoot(String tPShoot) {
		TPShoot = tPShoot;
	}
	public String getTPShootEffNumber() {
		return TPShootEffNumber;
	}
	public void setTPShootEffNumber(String tPShootEffNumber) {
		TPShootEffNumber = tPShootEffNumber;
	}
	public String getTPShootEff() {
		return TPShootEff;
	}
	public void setTPShootEff(String tPShootEff) {
		TPShootEff = tPShootEff;
	}
	public String getAss() {
		return Ass;
	}
	public void setAss(String ass) {
		Ass = ass;
	}
	public String getSteal() {
		return Steal;
	}
	public void setSteal(String steal) {
		Steal = steal;
	}
	public String getRejection() {
		return Rejection;
	}
	public void setRejection(String rejection) {
		Rejection = rejection;
	}
	public String getFoul() {
		return Foul;
	}
	public void setFoul(String foul) {
		Foul = foul;
	}
	public String getTo() {
		return To;
	}
	public void setTo(String to) {
		To = to;
	}
	
	
	
	
}

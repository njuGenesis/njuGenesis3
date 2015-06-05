package data.po.matchData;

public class MatchPlayer {
	String MatchID; 
	String OtherTeam;
	String Team;
	String Playername;
	String Time;  //在场时间
	String points;  //得分
	String isFirst;//是否首发
	
	String BankOff;   //进攻篮板
	String BankDef;    //防守篮板
	String Bank;    //篮板

	String Shoot;   //投球数
	String ShootEffNumber; //投篮命中数
	String ShootEff;//投篮命中率
	
	String TPShootEff;//三分命中率
	String TPShoot; //三分投篮数
	String TPShootEffNumber; //三分命数

	String FT;   //罚球数
	String FTShootEff;//罚球命中率
	String FTShootEffNumber; //罚篮命中数
	
	String realEff;//真实命中率
	String To;   //失误
	String Ass; //助攻数
	String Steal;//抢断数
	String Rejection;//盖帽数
	String Foul;//犯规数
	
	
	
	public String getMatchID() {
		return MatchID;
	}
	public void setMatchID(String matchID) {
		MatchID = matchID;
	}
	public String getOtherTeam() {
		return OtherTeam;
	}
	public void setOtherTeam(String otherTeam) {
		OtherTeam = otherTeam;
	}
	public String getTeam() {
		return Team;
	}
	public void setTeam(String team) {
		Team = team;
	}
	public String getPlayername() {
		return Playername;
	}
	public void setPlayername(String playername) {
		Playername = playername;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getIsFirst() {
		return isFirst;
	}
	public void setIsFirst(String isFirst) {
		this.isFirst = isFirst;
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
	public String getBank() {
		return Bank;
	}
	public void setBank(String bank) {
		Bank = bank;
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
	public String getTPShootEff() {
		return TPShootEff;
	}
	public void setTPShootEff(String tPShootEff) {
		TPShootEff = tPShootEff;
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
	public String getRealEff() {
		return realEff;
	}
	public void setRealEff(String realEff) {
		this.realEff = realEff;
	}
	public String getTo() {
		return To;
	}
	public void setTo(String to) {
		To = to;
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
	
	
	
}

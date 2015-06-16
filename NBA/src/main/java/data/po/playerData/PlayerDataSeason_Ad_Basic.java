package data.po.playerData;

public class PlayerDataSeason_Ad_Basic {
	String name;
	int id;
	String season;
	String team;
	String backeff;//篮板率
	String offbeff;//进攻篮板率
	String defbeff;
	String assisteff;//助攻率
	String stealeff;//抢断率
	String rejeff;//盖帽率
	String misseff;//失误率
	String useeff;//使用率
	String offeff;//进攻效率
	String defeff;//防守效率
	String ws;//胜利贡献值
	String offws;
	String defws;
	String per;//效率值
	String strshoot;//扣篮
	String kda;// 2/3+1
	String berej;//被冒
	String nameCn;
	public String getProperty(String type){
		String res = "";
		switch(type){
		case "name":res = name;
		case "season":res = season;
		case "team":res = team;
		case "backeff":res = backeff;
		case "offbeff":res = offbeff;
		case "defbeff":res =defbeff;
		case "assisteff":res = assisteff;
		case "stealeff":res = stealeff;
		case "rejeff":res = rejeff;
		case "misseff":res = misseff;
		case "useeff":res = useeff;
		case "offeff":res = offeff;
		case "defeff":res = defeff;
		case "ws":res = ws;
		case "offws":res = offws;
		case "defws":res = defws;
		case "per":res = per;
		case "strshoot":res = strshoot;
		case "kda":res = kda;
		case "berej":res = berej;
		case "nameCn":res = nameCn;
		}
		return res;
	}
	public String getNameCn(){
		return nameCn;
	}
	public void setNameCn(String name){
		nameCn = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getBackeff() {
		return backeff;
	}
	public void setBackeff(String backeff) {
		this.backeff = backeff;
	}
	public String getOffbeff() {
		return offbeff;
	}
	public void setOffbeff(String offbeff) {
		this.offbeff = offbeff;
	}
	public String getDefbeff() {
		return defbeff;
	}
	public void setDefbeff(String defbeff) {
		this.defbeff = defbeff;
	}
	public String getAssisteff() {
		return assisteff;
	}
	public void setAssisteff(String assisteff) {
		this.assisteff = assisteff;
	}
	public String getStealeff() {
		return stealeff;
	}
	public void setStealeff(String stealeff) {
		this.stealeff = stealeff;
	}
	public String getRejeff() {
		return rejeff;
	}
	public void setRejeff(String rejeff) {
		this.rejeff = rejeff;
	}
	public String getMisseff() {
		return misseff;
	}
	public void setMisseff(String misseff) {
		this.misseff = misseff;
	}
	public String getUseeff() {
		return useeff;
	}
	public void setUseeff(String useeff) {
		this.useeff = useeff;
	}
	public String getOffeff() {
		return offeff;
	}
	public void setOffeff(String offeff) {
		this.offeff = offeff;
	}
	public String getDefeff() {
		return defeff;
	}
	public void setDefeff(String defeff) {
		this.defeff = defeff;
	}
	public String getWs() {
		return ws;
	}
	public void setWs(String ws) {
		this.ws = ws;
	}
	public String getOffws() {
		return offws;
	}
	public void setOffws(String offws) {
		this.offws = offws;
	}
	public String getDefws() {
		return defws;
	}
	public void setDefws(String defws) {
		this.defws = defws;
	}
	public String getPer() {
		return per;
	}
	public void setPer(String per) {
		this.per = per;
	}
	public String getStrshoot() {
		return strshoot;
	}
	public void setStrshoot(String strshoot) {
		this.strshoot = strshoot;
	}
	public String getKda() {
		return kda;
	}
	public void setKda(String kda) {
		this.kda = kda;
	}
	public String getBerej() {
		return berej;
	}
	public void setBerej(String berej) {
		this.berej = berej;
	}
	
}

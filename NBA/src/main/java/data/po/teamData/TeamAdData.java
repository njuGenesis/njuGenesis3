package data.po.teamData;

public class TeamAdData {
	private  void TeamRound(TeamData_Avg_PO team) {
		//进攻防守回合数
		team.setOff(team.getShootNumberPG()+ 0.4* team.getFTNumberPG()+1.07*team.getToPG()
				- 1.07* team.getOffBackBoardPG() *(team.getShootNumberPG()-team.getShootEffNumberPG())
						/ (team.getOffBackBoardPG() + team.getOtherDefBackBoardPG()));
		
		team.setDef(team.getOtherShootNumberPG()+ 0.4* team.getOtherFTNumberPG()+1.07*team.getOtherToPG()
				- 1.07* team.getOtherOffBackBoardPG() *(team.getOtherShootNumberPG()-team.getOtherShootEffNumberPG())
						/ (team.getOtherOffBackBoardPG() + team.getDefBackBoardPG()));
	}
	
	public void TeamRate(TeamData_Avg_PO team) {
		TeamRound(team);
		
		team.setWinrate(team.getWinMatch()/team.getMatchNumber()); //胜率
		
		team.setOffEff(team.getPPG()/team.getOff());                        //进攻效率
		team.setDefEff(team.getOtherPPG()/team.getDef());        //防守效率
		
		team.setOffBackBoardEff(team.getOffBackBoardPG()/(team.getOffBackBoardPG()+team.getOtherDefBackBoardPG()));//进攻篮板效率
		team.setDefBackBoardEff(team.getDefBackBoardPG()/(team.getDefBackBoardPG()+team.getOtherOffBackBoardPG()));//防守篮板效率
		
		team.setStealEff(team.getStealNumberPG()/team.getDef());  //抢断效率
		team.setAssistEff(team.getAssitNumberPG()/team.getOff());  //助攻效率
		
		
	}
	
	
	

}

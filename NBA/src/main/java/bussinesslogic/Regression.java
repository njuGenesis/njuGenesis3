package bussinesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import data.po.PersonR;
import data.po.playerData.PlayerDataSeason_Avg_Basic;
import bussinesslogic.player.PlayerLogic_db;

public class Regression {
	
	
	public static void main(String[] args) throws RemoteException {
		Regression r =new Regression();
		r.personR(753);
	}
	
	public PersonR personR(int id,String type) throws RemoteException{
		PersonR res= new PersonR();
		res.setId(id);
		PlayerLogic_db p = new PlayerLogic_db();
		ArrayList<PlayerDataSeason_Avg_Basic>  players =p.gets_a_b(id);
		System.out.println(players.size());
		if(players.size()<=3){
			System.out.println("数据太少无法分析");
		}
		
		double avg_x =0.0;
		double avg_y = 0.0;
		
		for(int i=0;i<players.size();i++){
			avg_x=avg_x+Double.valueOf(players.get(i).getPts().replaceAll("%", ""));
			avg_y=avg_y+Double.valueOf(players.get(i).getOffb().replaceAll("%", ""));
		}
		avg_x=avg_x/players.size();
		avg_y=avg_y/players.size();
		
		System.out.println(avg_x + "   " + avg_y);

		double s_x = 0.0;
		double s_y = 0.0;

		for (int i = 0; i < players.size(); i++) {
			s_x = s_x
					+ Math.pow(Double.valueOf(players.get(i).getPts().replaceAll("%", "")) - avg_x,
							2);
			s_y = s_y
					+ Math.pow(Double.valueOf(players.get(i).getOffb().replaceAll("%", "")) - avg_y,
							2);
		}
		//System.out.println(s_x+"   "+s_y);

		s_x = Math.sqrt(s_x);
		s_y = Math.sqrt(s_y);

		System.out.println(s_x+"   "+s_y);
		
		double r = 0.0;
		for (int i = 0; i < players.size(); i++) {
			r = r + (Double.valueOf(players.get(i).getPts())- avg_x)
					* (Double.valueOf(players.get(i).getOffb().replaceAll("%", "")) - avg_y)
					/ (s_x * s_y);
		}

		System.out.println(r);
		double t_value = r
				* Math.pow((players.size() - 2) / (1 - Math.pow(r, 2)), 0.5);
		System.out.println(t_value);
		
		return res;
		
	}

}

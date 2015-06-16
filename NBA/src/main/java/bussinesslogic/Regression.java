package bussinesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import bussinesslogic.player.PlayerLogic_db;
import data.po.PersonR;
import data.po.RegreResult;
import data.po.playerData.PlayerDataSeason_Avg_Basic;

public class Regression {

	private static double[][] x;
	private static double[] y;
	private static int n;
	private static int m;

	public static void main(String[] args) throws RemoteException {
		Regression r = new Regression();
		r.sqt(1862);
		// r.getRelation(2222);

	}

	public ArrayList<PersonR> getRelation(int id) throws RemoteException {
		ArrayList<PersonR> res = new ArrayList<PersonR>();
		res.add(new PersonR());
		res.add(new PersonR());
		res.add(new PersonR());
		res.add(new PersonR());
		res.add(new PersonR());
		res.set(0, personR(id, "time"));
		res.set(1, personR(id, "shootper"));
		res.set(2, personR(id, "th_in"));
		res.set(3, personR(id, "ft_in"));
		res.set(4, personR(id, "offb"));

		for (int i = 0; i < res.size(); i++) {
			System.out.println(res.get(i).getPersonR() + "     "
					+ res.get(i).getType());
		}

		for (int i = 0; i < res.size(); i++) {
			// System.out.println(res.get(i).getPersonR());
			if (res.get(i).getPersonR() < 0.4
					|| (!(res.get(i).getPersonR() <= 1))) {
				res.remove(i);
				i--;
			}
		}

		return res;

	}

	// 与得分相关数据的相关性检验
	public PersonR personR(int id, String type) throws RemoteException {
		PersonR res = new PersonR();
		res.setId(id);
		res.setPersonR(0);
		res.setPt(0);
		res.setType(type);

		PlayerLogic_db p = new PlayerLogic_db();
		ArrayList<PlayerDataSeason_Avg_Basic> players = p.gets_a_b(id);
		System.out.println(players.size());
		if (players.size() <= 3) {
			System.out.println("数据太少无法分析");
			return res;
		}

		double avg_x = 0.0;
		double avg_y = 0.0;

		for (int i = 0; i < players.size(); i++) {
			// System.out.println(players.get(i).getPts()+"   "+players.get(i).getProperty(type).replaceAll("%",
			// ""));
			if (players.get(i).getPts().equals("")) {
				players.get(i).setPts("0");
			}
			if (players.get(i).getProperty(type).replaceAll("%", "").equals("")) {

			}
		}

		for (int i = 0; i < players.size(); i++) {
			avg_x = avg_x
					+ Double.valueOf(players.get(i).getPts()
							.replaceAll("%", ""));
			if (!players.get(i).getProperty(type).replaceAll("%", "")
					.equals(""))
				avg_y = avg_y
						+ Double.valueOf(players.get(i).getProperty(type)
								.replaceAll("%", ""));
		}
		avg_x = avg_x / players.size();
		avg_y = avg_y / players.size();

		// System.out.println(avg_x + "   " + avg_y);

		double s_x = 0.0;
		double s_y = 0.0;

		for (int i = 0; i < players.size(); i++) {
			s_x = s_x
					+ Math.pow(
							Double.valueOf(players.get(i).getPts()
									.replaceAll("%", ""))
									- avg_x, 2);
			if (!players.get(i).getProperty(type).replaceAll("%", "")
					.equals(""))
				s_y = s_y
						+ Math.pow(
								Double.valueOf(players.get(i).getProperty(type)
										.replaceAll("%", ""))
										- avg_y, 2);
		}
		// System.out.println(s_x+"   "+s_y);

		s_x = Math.sqrt(s_x);
		s_y = Math.sqrt(s_y);

		// System.out.println(s_x+"   "+s_y);

		double r = 0.0;
		for (int i = 0; i < players.size(); i++) {
			if (!players.get(i).getProperty(type).replaceAll("%", "")
					.equals(""))
				r = r
						+ (Double.valueOf(players.get(i).getPts()) - avg_x)
						* (Double.valueOf(players.get(i).getProperty(type)
								.replaceAll("%", "")) - avg_y) / (s_x * s_y);
		}

		// System.out.println("r=  "+r);
		double t_value = r
				* Math.pow((players.size() - 2) / (1 - Math.pow(r, 2)), 0.5);
		// System.out.println("t_value=  "+t_value);
		res.setPersonR(r);
		res.setPt(t_value);

		return res;

	}

	public void inix_y(int id, ArrayList<PersonR> value) throws RemoteException {
		PlayerLogic_db p = new PlayerLogic_db();
		ArrayList<PlayerDataSeason_Avg_Basic> players = p.gets_a_b(id);
		n = players.size();
		m = value.size();
		double[] res2 = new double[n];
		double[][] res = new double[m][n];
		//System.out.println(m+"   "+n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				//System.out.println(j+"  "+i);
				res[i][j] = Double.valueOf(players.get(j)
						.getProperty(value.get(i).getType())
						.replaceAll("%", "").equals("")?"0":players.get(j)
								.getProperty(value.get(i).getType())
								.replaceAll("%", ""));
				res2[j] = Double.valueOf(players.get(i).getPts().equals("")?"0":players.get(j).getPts());
			}
			
		}
		x = res;
		y = res2;
	}

	// dt[0]随即误差q dt[1] 平均标准偏差s  dt[2]返回复相关系数r  dt[3]返回回归偏差平方和u 
	// v[m] 返回m个自变量的偏相关系数

	public RegreResult sqt(int id) throws RemoteException {
		RegreResult res = new RegreResult();
		ArrayList<PersonR> value = getRelation(id);
		inix_y(id, value);
		double[] a = new double[value.size() + 1];
		double[] v = new double[value.size()];
		double[] dt = new double[5];
		sqtRgression.sqt2(x, y, m, n, a, dt, v);
		res.setM(m);
		res.setN(n);
		res.setA(a);
		res.setDt(dt);
		res.setV(v);
		res.setValue(value);
		res.setX(x);
		res.setY(y);
		res.setF(dt[3]/(dt[0]/(n-2)));

		int i;
		// 输出系数方程
		for (i = 0; i <= value.size(); i++) {
			System.out.println("a(" + i + ")=" + a[i]);
		}
		// 输出相关系数等
		System.out.println("随机误差=" + dt[0] + "  平均标准偏差=" + dt[1] + "  r="
				+ dt[2]);
		System.out.println("回归偏差平方和=" + dt[3] + "  总偏差平方和=" + dt[4]);
		for (i = 0; i < value.size(); i++)
			System.out.println("v(" + i + ")=" + v[i] + "    type="
					+ value.get(i).getType());
		System.out.println("u=" + dt[3]);
		System.out.println(res.F);
		return res;
	}

}

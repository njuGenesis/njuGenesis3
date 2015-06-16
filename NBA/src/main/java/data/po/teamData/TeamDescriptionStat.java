package data.po.teamData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;

public class TeamDescriptionStat {
	String shortname;
	String season;
	String isseason;
	String type;
	double avg;
	double median;
	double range;
	double var;
	double varq;
	double c_v;
	double skewness;
	double kurtosis;

	public void setvalue(ArrayList<TeamLData> teams, TeamLData avg,
			final String property) {
		this.setType(property);
		// 均值
		this.setAvg(avg.getproperty(property));
	
		teams.sort(new Comparator<TeamLData>() {
			public int compare(TeamLData o1, TeamLData o2) {
				if (o1.getproperty(property) > o2.getproperty(property)) {
					return 1;
				}
				return -1;
			}
		});
		// 中位数
		if (teams.size() % 2 == 0) {
			this.setMedian(teams.get(teams.size() / 2).getproperty(property));
		} else {
			this.setMedian((teams.get(teams.size() / 2).getproperty(property) + teams
					.get((teams.size() - 1) / 2).getproperty(property)) / 2);
		}
		// 极差
		this.setRange(teams.get(teams.size() - 1).getproperty(property)
				- teams.get(0).getproperty(property));

		// 方差
		this.setVar(0);
		for (int i = 0; i < teams.size(); i++) {
			this.setVar(this.getVar()
					+ Math.pow(
							teams.get(i).getproperty(property) - this.getAvg(),
							2));
		}
		this.setVar(this.getVar() / teams.size());
		// 标准差
		this.setVarq(Math.sqrt(this.getVar()));

		// 变异系数
		this.setC_v(this.getVarq() / this.getAvg());

		// 偏度,峰度
		double B3=0;
		double B4=0;
		for (int i = 0; i < teams.size(); i++) {
			B3=B3+(this.getVar()
					+ Math.pow(
							teams.get(i).getproperty(property) - this.getAvg(),
							3));
			B4=B4+(this.getVar()
					+ Math.pow(
							teams.get(i).getproperty(property) - this.getAvg(),
							4));
		}
		B3=B3/teams.size();
		B4=B4/teams.size();
		this.setSkewness(B3/Math.pow(this.getVar(), 1.5));
		this.setKurtosis(B4/Math.pow(this.getVar(), 2));

	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		BigDecimal bg = new BigDecimal(avg);
		avg = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		this.avg = avg;
	}

	public double getMedian() {
		return median;
	}

	public void setMedian(double median) {
		this.median = median;
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		BigDecimal bg = new BigDecimal(range);
		range = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		this.range = range;
	}

	public double getVar() {
		return var;
	}

	public void setVar(double var) {
		BigDecimal bg = new BigDecimal(var);
		var = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		this.var = var;
	}

	public double getVarq() {
		return varq;
	}

	public void setVarq(double varq) {
		BigDecimal bg = new BigDecimal(varq);
		varq = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		this.varq = varq;
	}

	public double getC_v() {
		return c_v;
	}

	public void setC_v(double c_v) {
		BigDecimal bg = new BigDecimal(c_v);
		c_v = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		this.c_v = c_v;
	}

	public double getSkewness() {
		return skewness;
	}

	public void setSkewness(double skewness) {
		BigDecimal bg = new BigDecimal(skewness);
		skewness = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		this.skewness = skewness;
	}

	public double getKurtosis() {
		return kurtosis;
	}

	public void setKurtosis(double kurtosis) {
		BigDecimal bg = new BigDecimal(kurtosis);
		kurtosis = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		this.kurtosis = kurtosis;
	}
 
}

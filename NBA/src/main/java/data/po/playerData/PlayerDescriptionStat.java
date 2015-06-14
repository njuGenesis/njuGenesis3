package data.po.playerData;

public class PlayerDescriptionStat {
	int id;
	String type;
	double avg;
	double median;
	double range;
	double var;
	double varq;
	double c_v;
	double skewness;
	double kurtosis;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
		this.range = range;
	}
	public double getVar() {
		return var;
	}
	public void setVar(double var) {
		this.var = var;
	}
	public double getVarq() {
		return varq;
	}
	public void setVarq(double varq) {
		this.varq = varq;
	}
	public double getC_v() {
		return c_v;
	}
	public void setC_v(double c_v) {
		this.c_v = c_v;
	}
	public double getSkewness() {
		return skewness;
	}
	public void setSkewness(double skewness) {
		this.skewness = skewness;
	}
	public double getKurtosis() {
		return kurtosis;
	}
	public void setKurtosis(double kurtosis) {
		this.kurtosis = kurtosis;
	}
	
}

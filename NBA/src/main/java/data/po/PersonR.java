package data.po;

import java.math.BigDecimal;

public class PersonR {
	double personR;
	double pt;
	String type;
	int id;

	public double getPersonR() {
		return personR;
	}

	public void setPersonR(double personR) {
		if (personR < 100000 && personR > -1000000) {
			BigDecimal bg = new BigDecimal(personR);
			personR = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		this.personR = personR;
	}

	public double getPt() {
		return pt;
	}

	public void setPt(double pt) {
		if (pt < 100000 && pt > -1000000) {
			BigDecimal bg = new BigDecimal(pt);
			pt = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		this.pt = pt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}

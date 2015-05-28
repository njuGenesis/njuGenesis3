package data.po.playerData;

public class PlayerDetailInfo {
	String name;
	String position;
	String height;
	String weight;
	String birth;//生日
	String borncity;//出生城市
	String number;//球衣号码
	int id;//数据库中唯一标识，超键
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getBorncity() {
		return borncity;
	}
	public void setBorncity(String borncity) {
		this.borncity = borncity;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
}

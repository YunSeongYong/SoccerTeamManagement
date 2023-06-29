import java.util.Objects;

public class Schedule {
	private int num;
	private String date;
	private String startTime;
	private String endTime;
	private String content;
	private String where;
	private String who;
	private String check;
	
	public Schedule(String startTime, String endTime, String content) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.content = content;
	}
	
	public Schedule(String startTime, String endTime, String content, String check) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.content = content;
		this.check = check;
	}

	public Schedule(int num, String date, String startTime, String endTime, String content, String where, String who) {
		super();
		this.num = num;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.content = content;
		this.where = where;
		this.who = who;
	}
	

	

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getWho() {
		return who;
	}

	public void setWho(String who) {
		this.who = who;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(check, content, date, endTime, num, startTime, where, who);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		return Objects.equals(check, other.check) && Objects.equals(content, other.content)
				&& Objects.equals(date, other.date) && Objects.equals(endTime, other.endTime) && num == other.num
				&& Objects.equals(startTime, other.startTime) && Objects.equals(where, other.where)
				&& Objects.equals(who, other.who);
	}

	@Override
	public String toString() {
		return "Schedule [num=" + num + ", date=" + date + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", content=" + content + ", where=" + where + ", who=" + who + ", check=" + check + "]";
	}

	public static void main(String[] args) {

	}
}

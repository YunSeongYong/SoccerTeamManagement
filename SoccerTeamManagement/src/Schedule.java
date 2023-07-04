import java.util.Objects;

public class Schedule {
	private int num;
	private String date;
	private String startTime;
	private String endTime;
	private String content;
	private String who;
	private String check;
	
	public Schedule(int num, String date, String startTime, String endTime, String content, String who, String check) {
		super();
		this.num = num;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.content = content;
		this.who = who;
		this.check = check;
	}

	public Schedule(String startTime, String endTime, String content) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.content = content;
	}

	public Schedule(String starttime, String endtime, String content, String check) {
		this.startTime = starttime;
		this.endTime = endtime;
		this.content = content;
		this.check = check;
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
	public String toString() {
		return "Schedule [num=" + num + ", date=" + date + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", content=" + content + ", who=" + who + ", check=" + check + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((check == null) ? 0 : check.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + num;
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((who == null) ? 0 : who.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Schedule))
			return false;
		Schedule other = (Schedule) obj;
		if (check == null) {
			if (other.check != null)
				return false;
		} else if (!check.equals(other.check))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (num != other.num)
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (who == null) {
			if (other.who != null)
				return false;
		} else if (!who.equals(other.who))
			return false;
		return true;
	}
	
	
}
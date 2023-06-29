public class Comment {
	private int number;
	private String datetime;
	private String schedulecomment;
	private String conditioncomment;
	private String doctorcomment;
	private String who;
	
	Comment(String datetime, String conditioncomment) {
		super();
		this.datetime = datetime;
		this.conditioncomment = conditioncomment;
	}

	public Comment(int number, String datetime, String schedulecomment, String conditioncomment, String doctorcomment,
			String who) {
		super();
		this.number = number;
		this.datetime = datetime;
		this.schedulecomment = schedulecomment;
		this.conditioncomment = conditioncomment;
		this.doctorcomment = doctorcomment;
		this.who = who;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getSchedulecomment() {
		return schedulecomment;
	}

	public void setSchedulecomment(String schedulecomment) {
		this.schedulecomment = schedulecomment;
	}

	public String getConditioncomment() {
		return conditioncomment;
	}

	public void setConditioncomment(String conditioncomment) {
		this.conditioncomment = conditioncomment;
	}

	public String getDoctorcomment() {
		return doctorcomment;
	}

	public void setDoctorcomment(String doctorcomment) {
		this.doctorcomment = doctorcomment;
	}

	public String getWho() {
		return who;
	}

	public void setWho(String who) {
		this.who = who;
	}

	@Override
	public String toString() {
		return "Comment [number=" + number + ", datetime=" + datetime + ", schedulecomment=" + schedulecomment
				+ ", conditioncomment=" + conditioncomment + ", doctorcomment=" + doctorcomment + ", who=" + who + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conditioncomment == null) ? 0 : conditioncomment.hashCode());
		result = prime * result + ((datetime == null) ? 0 : datetime.hashCode());
		result = prime * result + ((doctorcomment == null) ? 0 : doctorcomment.hashCode());
		result = prime * result + number;
		result = prime * result + ((schedulecomment == null) ? 0 : schedulecomment.hashCode());
		result = prime * result + ((who == null) ? 0 : who.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Comment))
			return false;
		Comment other = (Comment) obj;
		if (conditioncomment == null) {
			if (other.conditioncomment != null)
				return false;
		} else if (!conditioncomment.equals(other.conditioncomment))
			return false;
		if (datetime == null) {
			if (other.datetime != null)
				return false;
		} else if (!datetime.equals(other.datetime))
			return false;
		if (doctorcomment == null) {
			if (other.doctorcomment != null)
				return false;
		} else if (!doctorcomment.equals(other.doctorcomment))
			return false;
		if (number != other.number)
			return false;
		if (schedulecomment == null) {
			if (other.schedulecomment != null)
				return false;
		} else if (!schedulecomment.equals(other.schedulecomment))
			return false;
		if (who == null) {
			if (other.who != null)
				return false;
		} else if (!who.equals(other.who))
			return false;
		return true;
	}
	
	
	
	}

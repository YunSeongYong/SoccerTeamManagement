public class Comment {
	private int number;
	private int date;
	private String schedulecomment;
	private String condition;
	private String doctorcomment;
	private String person;
	
	public Comment(int number, int date, String schedulecomment, String condition, String doctorcomment,
			String person) {
		super();
		this.number = number;
		this.date = date;
		this.schedulecomment = schedulecomment;
		this.condition = condition;
		this.doctorcomment = doctorcomment;
		this.person = person;
	}

	
	
	public int getNumber() {
		return number;
	}



	public void setNumber(int number) {
		this.number = number;
	}



	public int getDate() {
		return date;
	}



	public void setDate(int date) {
		this.date = date;
	}



	public String getSchedulecomment() {
		return schedulecomment;
	}



	public void setSchedulecomment(String schedulecomment) {
		this.schedulecomment = schedulecomment;
	}



	public String getCondition() {
		return condition;
	}



	public void setCondition(String condition) {
		this.condition = condition;
	}



	public String getDoctorcomment() {
		return doctorcomment;
	}



	public void setDoctorcomment(String doctorcomment) {
		this.doctorcomment = doctorcomment;
	}



	public String getPerson() {
		return person;
	}



	public void setPerson(String person) {
		this.person = person;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + date;
		result = prime * result + ((doctorcomment == null) ? 0 : doctorcomment.hashCode());
		result = prime * result + number;
		result = prime * result + ((person == null) ? 0 : person.hashCode());
		result = prime * result + ((schedulecomment == null) ? 0 : schedulecomment.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (date != other.date)
			return false;
		if (doctorcomment == null) {
			if (other.doctorcomment != null)
				return false;
		} else if (!doctorcomment.equals(other.doctorcomment))
			return false;
		if (number != other.number)
			return false;
		if (person == null) {
			if (other.person != null)
				return false;
		} else if (!person.equals(other.person))
			return false;
		if (schedulecomment == null) {
			if (other.schedulecomment != null)
				return false;
		} else if (!schedulecomment.equals(other.schedulecomment))
			return false;
		return true;
	}

	

	@Override
	public String toString() {
		return "Comment [number=" + number + ", date=" + date + ", schedulecomment=" + schedulecomment + ", condition="
				+ condition + ", doctorcomment=" + doctorcomment + ", person=" + person + "]";
	}



	public static void main(String[] args) {

	}
}

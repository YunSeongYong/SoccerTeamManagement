public class DoctorAppointment {
	private int backnumber;
	private String playerName;
	private String date;
	private String time;
	private String condition;
	private String doctor;
	private String coach;
	
	public DoctorAppointment(int backnumber, String playerName, String date, String time, String condition,
			String doctor, String coach) {
		super();
		this.backnumber = backnumber;
		this.playerName = playerName;
		this.date = date;
		this.time = time;
		this.condition = condition;
		this.doctor = doctor;
		this.coach = coach;
	}

	public int getBacknumber() {
		return backnumber;
	}

	public void setBacknumber(int backnumber) {
		this.backnumber = backnumber;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getCoach() {
		return coach;
	}

	public void setCoach(String coach) {
		this.coach = coach;
	}

	@Override
	public String toString() {
		return "DoctorAppointment [backnumber=" + backnumber + ", playerName=" + playerName + ", date=" + date
				+ ", time=" + time + ", condition=" + condition + ", doctor=" + doctor + ", coach=" + coach + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + backnumber;
		result = prime * result + ((coach == null) ? 0 : coach.hashCode());
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((doctor == null) ? 0 : doctor.hashCode());
		result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof DoctorAppointment))
			return false;
		DoctorAppointment other = (DoctorAppointment) obj;
		if (backnumber != other.backnumber)
			return false;
		if (coach == null) {
			if (other.coach != null)
				return false;
		} else if (!coach.equals(other.coach))
			return false;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (doctor == null) {
			if (other.doctor != null)
				return false;
		} else if (!doctor.equals(other.doctor))
			return false;
		if (playerName == null) {
			if (other.playerName != null)
				return false;
		} else if (!playerName.equals(other.playerName))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}
	
	
	

}

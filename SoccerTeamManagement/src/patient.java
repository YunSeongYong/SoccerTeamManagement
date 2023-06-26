public class patient {
	private int backNumber;
	private String name;
	private String when;
	private String condition;
	private String doctor;
	
	public patient(int backNumber, String name, String when, String condition, String doctor) {
		super();
		this.backNumber = backNumber;
		this.name = name;
		this.when = when;
		this.condition = condition;
		this.doctor = doctor;
	}

	public int getBackNumber() {
		return backNumber;
	}


	public void setBackNumber(int backNumber) {
		this.backNumber = backNumber;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getWhen() {
		return when;
	}


	public void setWhen(String when) {
		this.when = when;
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

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + backNumber;
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + ((doctor == null) ? 0 : doctor.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((when == null) ? 0 : when.hashCode());
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
		patient other = (patient) obj;
		if (backNumber != other.backNumber)
			return false;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (doctor == null) {
			if (other.doctor != null)
				return false;
		} else if (!doctor.equals(other.doctor))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (when == null) {
			if (other.when != null)
				return false;
		} else if (!when.equals(other.when))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "patient [backNumber=" + backNumber + ", name=" + name + ", when=" + when + ", condition=" + condition
				+ ", doctor=" + doctor + "]";
	}

	public static void main(String[] args) {

	}
}

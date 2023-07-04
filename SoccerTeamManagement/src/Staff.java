import java.awt.Image;

public class Staff {
	private int number;
	private static String name;
	private String birthdate;
	private String role;
	private String career;
	private Image image;
	public Staff() {
		
	}
	
	public Staff(int number, String name, String birthdate, String role, String career, Image image) {
		super();
		this.number = number;
		this.name = name;
		this.birthdate = birthdate;
		this.role = role;
		this.career = career;
		this.image = image;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	@Override
	public String toString() {
		return "Staff [number=" + number + ", birthdate=" + birthdate + ", role=" + role + ", career=" + career
				+ ", image=" + image + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthdate == null) ? 0 : birthdate.hashCode());
		result = prime * result + ((career == null) ? 0 : career.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + number;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		Staff other = (Staff) obj;
		if (birthdate == null) {
			if (other.birthdate != null)
				return false;
		} else if (!birthdate.equals(other.birthdate))
			return false;
		if (career == null) {
			if (other.career != null)
				return false;
		} else if (!career.equals(other.career))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (number != other.number)
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}
}
	
	
	
	
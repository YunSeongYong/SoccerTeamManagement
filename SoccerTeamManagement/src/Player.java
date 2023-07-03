	import java.awt.Image;
	
public class Player {
	private static int backNumber;
	private String name;
	private double height;
	private double weight;
	private int age;
	private String position;
	private String coach;
	private String doctor;
	private Image image;
	private int no;
	
	
	public Player() {
		
	}
	
	

	public Player(String name) {
		this.name = name;
	}



	public Player(int backNumber, String name, double height, double weight, int age, String position, String coach,
			String doctor, Image image, int no) {
		this.backNumber = backNumber;
		this.name = name;
		this.height = height;
		this.weight = weight;
		this.age = age;
		this.position = position;
		this.coach = coach;
		this.doctor = doctor;
		this.image = image;
		this.no = no;
	}
	
	public int getNo() {
		return no;
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

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCoach() {
		return coach;
	}

	public void setCoach(String coach) {
		this.coach = coach;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Player [backNumber=" + backNumber + ", name=" + name + ", height=" + height + ", weight=" + weight
				+ ", age=" + age + ", position=" + position + ", coach=" + coach + ", doctor=" + doctor + ", image="
				+ image + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + backNumber;
		result = prime * result + ((coach == null) ? 0 : coach.hashCode());
		result = prime * result + ((doctor == null) ? 0 : doctor.hashCode());
		long temp;
		temp = Double.doubleToLongBits(height);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Player))
			return false;
		Player other = (Player) obj;
		if (age != other.age)
			return false;
		if (backNumber != other.backNumber)
			return false;
		if (coach == null) {
			if (other.coach != null)
				return false;
		} else if (!coach.equals(other.coach))
			return false;
		if (doctor == null) {
			if (other.doctor != null)
				return false;
		} else if (!doctor.equals(other.doctor))
			return false;
		if (Double.doubleToLongBits(height) != Double.doubleToLongBits(other.height))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}
}

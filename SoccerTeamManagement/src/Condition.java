public class Condition {
	private int number;
	private String playerName;
	private String playercondition;
	private String date;
	
	public Condition(int number, String playerName, String playercondition, String date) {
		super();
		this.number = number;
		this.playerName = playerName;
		this.playercondition = playercondition;
		this.date = date;
	}

	public int getNumber() {
		return number;
	}



	public void setNumber(int number) {
		this.number = number;
	}



	public String getPlayerName() {
		return playerName;
	}



	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}



	public String getPlayercondition() {
		return playercondition;
	}



	public void setPlayercondition(String playercondition) {
		this.playercondition = playercondition;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + number;
		result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
		result = prime * result + ((playercondition == null) ? 0 : playercondition.hashCode());
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
		Condition other = (Condition) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (number != other.number)
			return false;
		if (playerName == null) {
			if (other.playerName != null)
				return false;
		} else if (!playerName.equals(other.playerName))
			return false;
		if (playercondition == null) {
			if (other.playercondition != null)
				return false;
		} else if (!playercondition.equals(other.playercondition))
			return false;
		return true;
	}
		
	
	@Override
	public String toString() {
		return "Condition [number=" + number + ", playerName=" + playerName + ", playercondition=" + playercondition
				+ ", date=" + date + "]";
	}

	public static void main(String[] args) {

	}
}

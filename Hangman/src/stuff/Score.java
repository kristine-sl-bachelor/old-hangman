package stuff;

public class Score implements Comparable<Score> {
	
	public String name; 
	public int wins, losses; 
	
	public Score(String name, int wins, int losses) {
		this.name = name; 
		this.wins = wins; 
		this.losses = losses; 
	}
	
	public int getTotalScore() { 
		return wins - losses; 
	}

	public String toString() {
		return String.format("%-30s %10d %10d %10d", name, wins, losses, getTotalScore());
	}
	
	public boolean equals(Object other) {
		if (other == this) return true;
		if (!(other instanceof Score)) return false;
		
		Score otherScore = (Score) other;
		
		return this.wins == otherScore.wins && this.losses == otherScore.losses;
	}
	
	public int compareTo(Score someScore) {
		
		if (someScore.equals(this)) return 0;
		
		if (this.getTotalScore() < someScore.getTotalScore()) {
			return -1;
		} else if (this.getTotalScore() > someScore.getTotalScore()) {
			return 1;
		} else { // equal total score
			if (this.wins < someScore.wins) return -1;
			else return 1;
		}
	}
}

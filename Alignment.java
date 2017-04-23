public class Alignment {
	
	//----------------------------------------------------------------------------------------\\
	//                                   Fields                                               \\
	//----------------------------------------------------------------------------------------\\
	
	DNA topStrand;
	DNA bottomStrand;
	int row;
	int col;
	
	//----------------------------------------------------------------------------------------\\
	//                                   Constructors                                         \\
	//----------------------------------------------------------------------------------------\\
	
	public Alignment(){
		this.topStrand = new DNA("");
		this.bottomStrand = new DNA("");
		this.row = 0;
		this.col = 0;
	}
	
	public Alignment(DNA a, DNA b){
		this.topStrand = a;
		this.bottomStrand = b;
		this.row = 0;
		this.col = 0;
	}
	
	public Alignment(DNA a, DNA b, int row, int col){
		this.topStrand = a;
		this.bottomStrand = b;
		this.row = row;
		this.col = col;
	}

	//----------------------------------------------------------------------------------------\\
	//                                   Getters and Setters                                  \\
	//---------------------------------------------------------------------------------------\\
	
	public DNA getTopStrand() {
		return topStrand;
	}

	public void setTopStrand(DNA topStrand) {
		this.topStrand = topStrand;
	}

	public DNA getBottomStrand() {
		return bottomStrand;
	}

	public void setBottomStrand(DNA bottomStrand) {
		this.bottomStrand = bottomStrand;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	//----------------------------------------------------------------------------------------\\
	//                                    Methods                                             \\
	//----------------------------------------------------------------------------------------\\

	// Add characters to the end of each of the strands
	public void append (char a, char b){
		this.topStrand.setSequence(topStrand.getSequence()+a);
		this.bottomStrand.setSequence(bottomStrand.getSequence()+b);
	}
	
	// Decrement the column of the alignment
	public void colDec(){
		this.col--;
	}
	
	// Decrement the row of the alignment
	public void rowDec(){
		this.row--;
	}
	
	//----------------------------------------------------------------------------------------\\
	//                               ToString and Equals                                      \\
	//----------------------------------------------------------------------------------------\\
	
	@Override
	public String toString() {
		return "Alignment [topStrand=" + topStrand + ", bottomStrand=" + bottomStrand + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alignment other = (Alignment) obj;
		if (bottomStrand == null) {
			if (other.bottomStrand != null)
				return false;
		} else if (!bottomStrand.equals(other.bottomStrand))
			return false;
		if (topStrand == null) {
			if (other.topStrand != null)
				return false;
		} else if (!topStrand.equals(other.topStrand))
			return false;
		return true;
	}
}
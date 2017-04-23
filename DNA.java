public class DNA {
	
	//----------------------------------------------------------------------------------------\\
	//                                   Fields                                               \\
	//----------------------------------------------------------------------------------------\\
	
	private String sequence;
	
	//----------------------------------------------------------------------------------------\\
	//                                   Constructors                                         \\
	//----------------------------------------------------------------------------------------\\
	
	DNA(String sequence){
		this.sequence = sequence;	
	}
	
	DNA(){
		this.sequence = null;
	}
	
	//----------------------------------------------------------------------------------------\\
	//                                   Getters and Setters                                  \\
	//----------------------------------------------------------------------------------------\\

	public String getSequence() {
		return this.sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
	//----------------------------------------------------------------------------------------\\
	//                                   Basic Methods                                        \\
	//----------------------------------------------------------------------------------------\\
	
	public String toString(){
		return getSequence();
	}
	
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		
		if(obj == null || obj.getClass() != this.getClass())
			return false;
		
		DNA dna = (DNA) obj;
		
		return(dna.sequence == sequence);
	}
	
	//----------------------------------------------------------------------------------------\\
	//                                 Methods                                                \\
	//----------------------------------------------------------------------------------------\\
	
	//Checks DNA and ensures that all characters are valid
	public boolean check(){
		String tmp = "";
		
		for (int i = 0; i < sequence.length(); i++){
			if (sequence.charAt(i) == 'a' || sequence.charAt(i) == 'c' || sequence.charAt(i) == 't' || sequence.charAt(i) == 'g')
				tmp += Character.toUpperCase(sequence.charAt(i));
			if (sequence.charAt(i) == 'A' || sequence.charAt(i) == 'C' || sequence.charAt(i) == 'T' || sequence.charAt(i) == 'G')
				tmp += sequence.charAt(i);
		}
		
		sequence = tmp;
		if (sequence == "")
			System.out.println("\n\tInvalid Input! Please enter a sequence containing the characters A,T,C and G");
		return (sequence != "");
	}	
	
	// Return a string of the reversed strand so that it is aligned appropriately 
	public String reverser(){
		String newString = new String();
		int length = this.getSequence().length();
		for(int i=1; i<=length; i++){
			newString += this.getSequence().charAt(length - i);
		}
		return newString;
	}
	
	//----------------------------------------------------------------------------------------\\
	//                                       Main                                             \\
	//----------------------------------------------------------------------------------------\\
	
	//Tests out every method and constructor
	//Coder:
	
	public static void main(String[] args){
		DNA a = new DNA("Hello");
		DNA b = new DNA("What is up?");
		
		System.out.println("Getting sequence");
		System.out.println(a.getSequence());
		System.out.println(b.getSequence());
		System.out.println("Length of a = " + a.getSequence().length());
	}
}
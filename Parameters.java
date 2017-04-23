
public class Parameters {
	
	//----------------------------------------------------------------------------------------\\
	//                                   Fields                                               \\
	//----------------------------------------------------------------------------------------\\
	
	private Object matchScore;
	private Object mismatchPenalty;
	private Object gapPenalty;
	
	//----------------------------------------------------------------------------------------\\
	//                                   Constructors                                         \\
	//----------------------------------------------------------------------------------------\\
	
	Parameters(double matchScore, double mismatchPenalty, double gapPenalty){
		this.matchScore = new Double(matchScore);
		this.mismatchPenalty = new Double(mismatchPenalty);
		this.gapPenalty = new Double(gapPenalty);
	}
	
	Parameters(int matchScore, int mismatchPenalty, int gapPenalty){
		this.matchScore = new Integer(matchScore);
		this.mismatchPenalty = new Integer(mismatchPenalty);
		this.gapPenalty = new Integer(gapPenalty);
	}
	
	Parameters(){
		this.matchScore = new Integer(1);
		this.mismatchPenalty = new Integer(1);
		this.gapPenalty = new Integer(1);
	}
	
	//----------------------------------------------------------------------------------------\\
	//                                   Getters and Setters                                  \\
	//----------------------------------------------------------------------------------------\\

	public Object getMatchScore() {
		return matchScore;
	}

	public void setMatchScore(double matchScore) {
		this.matchScore = new Double(matchScore);
	}
	
	public void setMatchScore(int matchScore) {
		this.matchScore = new Integer(matchScore);
	}

	public Object getMismatchPenalty() {
		return mismatchPenalty;
	}

	public void setMismatchPenalty(double mismatchPenalty) {
		this.mismatchPenalty = new Double(mismatchPenalty);
	}
	
	public void setMismatchPenalty(int mismatchPenalty) {
		this.mismatchPenalty = new Integer(mismatchPenalty);
	}

	public Object getGapPenalty() {
		return gapPenalty;
	}

	public void setGapPenalty(double gapPenalty) {
		this.gapPenalty = new Double(gapPenalty);
	}
	
	public void setGapPenalty(int gapPenalty) {
		this.gapPenalty = new Integer(gapPenalty);
	}
	
	//----------------------------------------------------------------------------------------\\
	//                                   Basic Methods                                        \\
	//----------------------------------------------------------------------------------------\\
	
	public String toString(){
		return "(Match Score, Mismatch Penalty, Gap Penalty) = (" + getMatchScore() + "," + getMismatchPenalty() + "," + getGapPenalty() + ")";
	}
	
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		
		if(obj == null || obj.getClass() != this.getClass())
			return false;
		
		Parameters parameters = (Parameters) obj;
		
		return((parameters.matchScore == matchScore) && (parameters.mismatchPenalty == mismatchPenalty) && (parameters.gapPenalty == gapPenalty));
	}	
	//----------------------------------------------------------------------------------------\\
	//                                         Methods                                        \\
	//----------------------------------------------------------------------------------------\\
	
	//This function parses a string under the format (MatchScore, Mismatch Penalty, Gap Penalty) [Example: (5.03, 23.04, 10.3)] and sets the parameters to those values
	//This function returns true if no issues were encountered.
	public boolean parse(String string){
		
		//Check the String
		if (checkString(string) == false)
			return false;
		
		//Clean the string
		string = cleanString(string);
		
		//Declare Variables
		int i = 0; //Counter
		int min = 0; //Minimum Index of Number
		int max = 0; //Maximum Index of Number
		boolean isDouble = checkDouble(string); //Flag if the number being checked is a double or integer (false for integer, true for double)
		
		//Parse through first number
		while (string.charAt(i) != ',')
			i++;
		max = i;
		i++;

		//Set first number
		if (isDouble == true)
			setMatchScore(parseDouble(string.substring(min,max)));
		else 
			setMatchScore(parseInt(string.substring(min, max)));
		
		//Parse through second number
		min = max;
		while (string.charAt(i) != ',')
			i++;
		max = i;
		i++;
		
		//Set second number
		if (isDouble == true)
			setMismatchPenalty(-1.0 * parseDouble(string.substring(min,max)));
		else 
			setMismatchPenalty(-1 * parseInt(string.substring(min, max)));
		
		//Parse through third number
		min = max;
		while (string.charAt(i) != ')')
			i++;
		max = i;
		i++;
		
		//Set third number
		if (isDouble == true)
			setGapPenalty(-1.0 * parseDouble(string.substring(min,max)));
		else 
			setGapPenalty(-1 * parseInt(string.substring(min, max)));
		
		return true;
	}
	
	//----------------------------------------------------------------------------------------\\
	//                                  Static Methods                                        \\
	//----------------------------------------------------------------------------------------\\
	
	//This function takes a substring and parses it for a double
	//This function can handle integers, but will return them as doubles
	public static double parseDouble(String string){
		int i = 0;
		int decimal = 1;
		double num = 0;
		
		//Integer Part
		while (i < string.length() && string.charAt(i) != '.'){
			if (string.charAt(i) >= '0' && string.charAt(i) <= '9'){
				num *= 10.0;
				num += Character.getNumericValue(string.charAt(i)) * 1.0;
			}
			i++;
		}
		
		//Decimal
		for (; i < string.length(); i++){
			if (string.charAt(i) >= '0' && string.charAt(i) <= '9'){
				num += (Character.getNumericValue(string.charAt(i)) * 1.0) / (Math.pow(10.0,decimal));				
				decimal++;				
			}
		}
		
		return num;
	}
	
	//This function parses through a string looking for an integer
	public static int parseInt(String string){
		int num = 0;
		
		for (int i = 0; i < string.length(); i++){
			if (string.charAt(i) >= '0' && string.charAt(i) <= '9'){
				num *= 10;
				num += Character.getNumericValue(string.charAt(i));
			}	
		}
		
		return num;
	}
	
	//This functions checks the string to ensure all 9 control characters are present
	public static boolean checkString(String string){
		//Tmp tracks how many of the control characters have been found. 
		//These 9 characters are the control characters: ( 0 , - 0 , - 0 )
		
		int tmp = 0;
		
		for (int i = 0; i < string.length(); i++){
			if (tmp == 0 && string.charAt(i) == '(')
				tmp++;
			if (tmp == 1 && (string.charAt(i) >= '0' && string.charAt(i) <= '9'))
				tmp++;
			if (tmp == 2 && string.charAt(i) == ',')
				tmp++;
			if (tmp == 3 && string.charAt(i) == '-')
				tmp++;
			if (tmp == 4 && (string.charAt(i) >= '0' && string.charAt(i) <= '9'))
				tmp++;
			if (tmp == 5 && string.charAt(i) == ',')
				tmp++;
			if (tmp == 6 && string.charAt(i) == '-')
				tmp++;
			if (tmp == 7 && (string.charAt(i) >= '0' && string.charAt(i) <= '9'))
				tmp++;
			if (tmp == 8 && string.charAt(i) == ')')
				tmp++;		
			if (tmp == 1 && string.charAt(i) == '-')
				return false;
		}
		return (tmp == 9);	
	}
	
	//This method cleans the string to ensure that it follows the ideal format
	public static String cleanString(String string){
	int i = 0;
	String tmp = "(";
	boolean flag = false;
	
	//Parse until '(' character
	while (string.charAt(i) != '(')
		i++;
	
	//Parse until integer
	while (string.charAt(i) < '0' || string.charAt(i) > '9')
		i++;
	
	//Parse through first number
	while (string.charAt(i) != ','){
		if ((string.charAt(i) >= '0' && string.charAt(i) <= '9') || (string.charAt(i) == '.' && flag == false))
			tmp += string.charAt(i);
		if (string.charAt(i) == '.')
			flag = true;
		i++;
	}
	flag = false;
	tmp += ",";
	
	//Parse until negative
	while (string.charAt(i) != '-')
		i++;
	
	//Parse until integer
	while (string.charAt(i) < '0' || string.charAt(i) > '9')
		i++;
	
	//Parse through second number
	while (string.charAt(i) != ','){
		if ((string.charAt(i) >= '0' && string.charAt(i) <= '9') || (string.charAt(i) == '.' && flag == false))
			tmp += string.charAt(i);
		if (string.charAt(i) == '.')
			flag = true;
		i++;
	}
	flag = false;
	tmp += ",";
	
	//Parse until negative
	while (string.charAt(i) != '-')
		i++;
	
	//Parse until integer
	while (string.charAt(i) < '0' || string.charAt(i) > '9')
		i++;
	
	//Parse through third number
	while (string.charAt(i) != ')'){
		if ((string.charAt(i) >= '0' && string.charAt(i) <= '9') || (string.charAt(i) == '.' && flag == false))
			tmp += string.charAt(i);
		if (string.charAt(i) == '.')
			flag = true;
		i++;
	}
	flag = false;
	tmp += ")";
	
		return tmp;
	}
	
	//This function checks the string to see if there are any double values
	public static boolean checkDouble(String string){
		for (int i = 0; i < string.length(); i++)
			if (string.charAt(i) == '.')
				return true;
		return false;
	}

}

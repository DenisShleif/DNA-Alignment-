import java.util.*;
import java.util.concurrent.TimeUnit;

public class NeedleManWunsch {
	
	//----------------------------------------------------------------------------------------\\
	//                                   Fields                                               \\
	//----------------------------------------------------------------------------------------\\
	// Store the needleman-wunsch matrix as either ints or doubles
	private Object array[][];
	// Store values to indicate which way the needleman-wunsch values came from for traceback
	private int directionalArray[][];
	// Remember is the matrices have been filled so that they don't get filled again
	boolean beenFilled;
	
	//----------------------------------------------------------------------------------------\\
	//                                   Constructors                                         \\
	//----------------------------------------------------------------------------------------\\
		
	NeedleManWunsch(int a,int b){
		this.array = new Object[b][a];
		this.directionalArray = new int[b][a];
		this.beenFilled = false;
	}
	
	NeedleManWunsch(){
		this.array = null;
		this.directionalArray = null;
		this.beenFilled = false;
	}
	
	//----------------------------------------------------------------------------------------\\
	//                                   Getters and Setters                                  \\
	//---------------------------------------------------------------------------------------\\
	
	public Object[][] getArray() {
		return array;
	}

	public void setArray(Object[][] array) {
		this.array = array;
	}
		
	//----------------------------------------------------------------------------------------\\
	//                                   Basic Methods                                        \\
	//----------------------------------------------------------------------------------------\\
	
	// Output the Needleman-Wunsch matrix as one of the user options
	public void output(DNA A, DNA B){
		System.out.printf("  ____");
		
		for (int i = 0; i < A.getSequence().length(); i++)
			System.out.printf("_%c__",A.getSequence().charAt(i));
		
		System.out.printf("\n");
		System.out.printf(" |_%d_|",array[0][0]);
		
		for (int i = 1; i <= A.getSequence().length(); i++)
			System.out.printf("_%d_|",array[0][i]);
		
		for (int j = 0; j < B.getSequence().length(); j++){
			System.out.printf("\n%c|",B.getSequence().charAt(j));
			for (int i = 0; i <= A.getSequence().length(); i++)
				System.out.printf("_%d_|",array[j+1][i]);
		}
		System.out.printf("\n");
	}
	
	// Output the directional matrix for debugging purposes
	public void outputDir(DNA A, DNA B){
		System.out.printf("  ____");
		
		for (int i = 0; i < A.getSequence().length(); i++)
			System.out.printf("_%c__",A.getSequence().charAt(i));
		
		System.out.printf("\n");
		System.out.printf(" |_%d_|",directionalArray[0][0]);
		
		for (int i = 1; i <= A.getSequence().length(); i++)
			System.out.printf("_%d_|",directionalArray[0][i]);
		
		for (int j = 0; j < B.getSequence().length(); j++){
			System.out.printf("\n%c|",B.getSequence().charAt(j));
			for (int i = 0; i <= A.getSequence().length(); i++)
				System.out.printf("_%d_|",directionalArray[j+1][i]);
		}
		System.out.printf("\n");
	}
		
	public boolean equals(Object obj){
		if(this == obj)
			return true;
			
		if(obj == null || obj.getClass() != this.getClass())
			return false;
			
		NeedleManWunsch needleMan = (NeedleManWunsch) obj;
			
		return(needleMan.array == array);
	}
	
	//----------------------------------------------------------------------------------------\\
	//                                    Methods                                             \\
	//----------------------------------------------------------------------------------------\\
	
	//initializes all squares in the array to 0
	//public void initialize(){
	//	for (int i = 0; i < array.length; i++)
	//		for (int j = 0; j < array[0].length; j++)
	//			this.array[i][j] = 0;
	//}
	
	//fills in all the squares in the Needleman-Wunsch matrix
	public void fill(DNA A, DNA B, Parameters score){
		if(!beenFilled){
			// Try filling the matrix with doubles
			try{
				// Fill the first row and column with the required values
				for(int i=0; i<=A.getSequence().length(); i++){
					array[0][i] = i*(Double)score.getGapPenalty();
				}
				
				for(int i=0; i<=B.getSequence().length(); i++){
					array[i][0] = i*(Double)score.getGapPenalty();
				}
				
				// Fill the Needleman-Wunsch matrix with the values and fill the directional matrix at the same time
				for(int j=1; j<=A.getSequence().length(); j++){
					for(int i=1; i<=B.getSequence().length(); i++){
						double top = (Double)array[i-1][j] + (Double)score.getGapPenalty();
						double side = (Double)array[i][j-1] + (Double)score.getGapPenalty();
						double diag;
						if(A.getSequence().charAt(j-1) == B.getSequence().charAt(i-1))
							diag = (Double)array[i-1][j-1] + (Double)score.getMatchScore();
						else
							diag = (Double)array[i-1][j-1] + (Double)score.getMismatchPenalty();
						double maxVal = maximum(top, side, diag);
						array[i][j] = maxVal;
						// Add one if it's from the top
						if(maxVal == top)
							directionalArray[i][j] += 1;
						// Add two if it's from the side
						if(maxVal == side)
							directionalArray[i][j] += 2;
						// Add four if it's from the diagonal
						if(maxVal == diag)
							directionalArray[i][j] += 4;
					}
				}
				
				//for(int k=1; k<A.getSequence().length(); k++){
				//	directionalArray[0][k] = 2;
				//}
				//for(int k=1; k<B.getSequence().length(); k++){
				//	directionalArray[k][0] = 1;
				//}
				// Remember if the matrix has been filled
				beenFilled = true;
			}
			// Fill the matrix with integers if the parameters aren't doubles
			catch(ClassCastException e){
				// Fill the first row and column with the required values
				for(int i=0; i<=A.getSequence().length(); i++){
					array[0][i] = i*(Integer)score.getGapPenalty();
				}
				
				for(int i=0; i<=B.getSequence().length(); i++){
					array[i][0] = i*(Integer)score.getGapPenalty();
				}
				// Fill the Needleman-Wunsch matrix with the values and fill the directional matrix at the same time
				for(int j=1; j<=A.getSequence().length(); j++){
					for(int i=1; i<=B.getSequence().length(); i++){
						int top = (Integer)array[i-1][j] + (Integer)score.getGapPenalty();
						int side = (Integer)array[i][j-1] + (Integer)score.getGapPenalty();
						int diag;
						if(A.getSequence().charAt(j-1) == B.getSequence().charAt(i-1))
							diag = (Integer)array[i-1][j-1] + (Integer)score.getMatchScore();
						else
							diag = (Integer)array[i-1][j-1] + (Integer)score.getMismatchPenalty();
						int maxVal = maximum(top, side, diag);
						array[i][j] = maxVal;
						// Add one if the value is from the top
						if(maxVal == top)
							directionalArray[i][j] += 1;
						// Add two if the value is from the side
						if(maxVal == side)
							directionalArray[i][j] += 2;
						// Add four if the value is from the diagonal
						if(maxVal == diag)
							directionalArray[i][j] += 4;
					}
				}
				// Remember if the matrix has been filled
				beenFilled = true;
			}
		}
	}
	//find the max of three doubles
	static double maximum(double a, double b, double c){
		double max = a;
		if(b>max)
			max = b;
		if(c>max)
			max = c;
		return max;
	}
	// Find the max of three integers
	static int maximum(int a, int b, int c){
		int max = a;
		if(b>max)
			max = b;
		if(c>max)
			max = c;
		return max;
	}
	
	//Outputs a single alignment of DNA
	public void outputSequence(DNA A, DNA B){
		// Create an alignment
		Alignment align = new Alignment();
		// Store the value from the directional matrix
		int val;
		// Start at the bottom right corner of the matrix
		int col = A.getSequence().length();
		int row = B.getSequence().length();
		
		// While not at the top left corner
		while(row>0 && col>0){
			val = directionalArray[row][col];
			// If the value came from the diagonal
			if(val - 4 >= 0){
				align.append(A.getSequence().charAt(col - 1), B.getSequence().charAt(row - 1));
				row--;
				col--;
			}
			// If the value came from the side and not the diagonal
			else if(val-2 >= 0){
				align.append(A.getSequence().charAt(col - 1), '_');
				col--;
			}
			// If the value only came from the top
			else if(val!=0){
				align.append('_', B.getSequence().charAt(row - 1));
				row--;
			}		
		}
		
		// Get a string in order for the alignment strands and print them out
		String a = align.topStrand.reverser();
		String b = align.bottomStrand.reverser();
		System.out.println(a);
		System.out.println(b);
	}
	
	//Outputs all alignment of DNA
	public void outputSequences(DNA A, DNA B){
		List<Alignment> alignList = new ArrayList();
		Alignment align = new Alignment();
		
		// Remember how long the list of alignments is
		int listLength = 0;
		// Store the directional value
		int val = 0;
		// Start in the bottom right corner of the matrix
		int col = A.getSequence().length();
		int row = B.getSequence().length();
				
		outputDir(A, B);
		// Only exit when ready
		boolean flag = false;
		
		while(!flag){
			val = directionalArray[row][col];
			//System.out.println("val is " + val + " row is " + row + " col is " + col);
			// If value only from the diagonal
			if(val == 4){
				// Add to the DNA alignent
				align.append(A.getSequence().charAt(col - 1), B.getSequence().charAt(row - 1));
				// Move through the matrix
				row--;
				col--;
			}
			// If value only from the side
			else if(val == 2){
				align.append(A.getSequence().charAt(col - 1), '_');
				col--;
			}
			// If value only from the top
			else if(val == 1){
				align.append('_', B.getSequence().charAt(row - 1));
				row--;
			}
			// If value from the top and side
			else if(val == 3){
				// Copy the alignment and move it in the appropriate direction
				DNA newDnaTop = new DNA(align.getTopStrand().getSequence());
				DNA newDnaBot = new DNA(align.getBottomStrand().getSequence());
				Alignment copy = new Alignment(newDnaTop, newDnaBot, row, col);
				copy.append('_', B.getSequence().charAt(row - 1));
				copy.rowDec();
				// Ad the new alignment to the list 
				alignList.add(copy);
				// Add to the current alignment
				align.append(A.getSequence().charAt(col - 1), '_');
				col--;
				listLength++;
			}
			// If value from the diagonal and top
			else if(val == 5){
				DNA newDnaTop = new DNA(align.getTopStrand().getSequence());
				DNA newDnaBot = new DNA(align.getBottomStrand().getSequence());
				Alignment copy = new Alignment(newDnaTop, newDnaBot, row, col);
				copy.append('_', B.getSequence().charAt(row - 1));
				copy.rowDec();
				alignList.add(copy);
				align.append(A.getSequence().charAt(col - 1), B.getSequence().charAt(row - 1));
				col--;
				row--;
				listLength++;
			}
			// If value from the diagonal and side
			else if(val == 6){
				DNA newDnaTop = new DNA(align.getTopStrand().getSequence());
				DNA newDnaBot = new DNA(align.getBottomStrand().getSequence());
				Alignment copy = new Alignment(newDnaTop, newDnaBot, row, col);
				copy.append(A.getSequence().charAt(col - 1), '_');
				//System.out.println("Top string is " + copy.getTopStrand().getSequence());
				copy.colDec();
				alignList.add(copy);
				align.append(A.getSequence().charAt(col - 1), B.getSequence().charAt(row - 1));
				col--;
				row--;
				listLength++;
			}
			// If the value from the top side and diagonal
			else if(val == 7){
				DNA newDnaTop = new DNA(align.getTopStrand().getSequence());
				DNA newDnaBot = new DNA(align.getBottomStrand().getSequence());
				Alignment copy = new Alignment(newDnaTop, newDnaBot, row, col);
				copy.append(A.getSequence().charAt(col - 1), '_');
				copy.colDec();
				alignList.add(copy);
				
				DNA newDnaTop2 = new DNA(align.getTopStrand().getSequence());
				DNA newDnaBot2 = new DNA(align.getBottomStrand().getSequence());
				Alignment copy2 = new Alignment(newDnaTop2, newDnaBot2, row, col);
				copy2.append('_', B.getSequence().charAt(row - 1));
				copy2.rowDec();
				alignList.add(copy2);
				
				align.append(A.getSequence().charAt(col - 1), B.getSequence().charAt(row - 1));
				col--;
				row--;
				listLength+=2;
			}
			//System.out.println("Made it");
			if(col==0||row==0){
				String a = align.topStrand.reverser();
				String b = align.bottomStrand.reverser();
				System.out.println(a);
				System.out.println(b + "\n");
				
				if(listLength==0)
					flag = true;
				else{
					//align.setTopStrand(alignList.get(listLength-1).getTopStrand());
					//align.setBottomStrand(alignList.get(listLength-1).getBottomStrand());
					//col = alignList.get(listLength-1).getCol();
					//row = alignList.get(listLength-1).getRow();
					//String c = alignList.get(listLength-1).topStrand.reverser();
					//String d = alignList.get(listLength-1).bottomStrand.reverser();
					//System.out.println("Printing after copy\n" + c);
					//System.out.println(d + "\n");
					align = alignList.remove(listLength-1);
					col = align.getCol();
					row = align.getRow();
					listLength--;
					while(col==0||row==0){
						align = alignList.remove(listLength-1);
						col = align.getCol();
						row = align.getRow();
						listLength--;
					}
				}
			}
			//try {
			//    Thread.sleep(1000);
			//} catch(InterruptedException ex) {
			//    Thread.currentThread().interrupt();
			//}
		}
	}
	
	//----------------------------------------------------------------------------------------\\
	//                                       Main                                             \\
	//----------------------------------------------------------------------------------------\\
	
	//Tests out every method and constructor
	public static void main(String [] args){
		
	}
}
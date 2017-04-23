import java.util.Scanner;

public class Main {
	
	//----------------------------------------------------------------------------------------\\
	//                          Static Methods                                                \\
	//----------------------------------------------------------------------------------------\\
	
	//parses the input string for the options
	
	public static int parse(String string){
		for (int i = 0; i < string.length(); i++)
			if(string.charAt(i) >= '1' && string.charAt(i) <= '7')
				return Character.getNumericValue(string.charAt(i));
			
		System.out.println("Invalid Input! Please enter an option between 1 and 7");
		return 0;
	}
	
	//Inputs both DNA sequences and Both Parameters
	public static void input (DNA A, DNA B, Parameters score){
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Input 2 valid sequeces of DNA and the desired scores and penalties:");
		
		//Sequence 1
		do{
		System.out.printf("\tInput Sequence 1 (String of A,T,C and G): ");
		A.setSequence(keyboard.nextLine());
		}while(!A.check());
		
		//Sequence 2
		do{
		System.out.printf("\tInput Sequence 2 (String of A,T,C and G): ");
		B.setSequence(keyboard.nextLine());
		}while(!B.check());
		
		//Parameters
		do{
			System.out.printf("\tInput Scoring Parameters under the format (MatchScore, Mismatch Penalty, Gap Penalty)");
			System.out.printf("\n\tExample: %c(5.03, -23, -10.3)%c ",34,34);
			System.out.printf("\n\tParameters: ");
		}while(!score.parse(keyboard.nextLine()));
	}
	
	//Function outputs all options available in the menu
	public static void printOptions(){
		System.out.println("\nChoose an Option:");
		System.out.println("\t1) Print Both Sequences");
		System.out.println("\t2) Print Initialized Needleman-Wunsch Matrix");
		System.out.println("\t3) Print Results of Needleman-Wunsch Matrix (Entire Matrix)");
		System.out.println("\t4) Print One Optimal Alignment");
		System.out.println("\t5) Print All Optimal Alignments");
		System.out.println("\t6) Enter Two New Sequences of DNA to Score");
		System.out.println("\t7) Exit Program");
		System.out.printf("\tPlease Choose an Option (1 to 7): ");
	}	
	
	//Outputs a menu and scans for an option, returns the option number.
	public static int menu(){
		printOptions();
		String input;
		int option;
		Scanner keyboard = new Scanner(System.in);
		input = keyboard.nextLine();	
		option = parse(input);
		return option;
	}	
	
	//----------------------------------------------------------------------------------------\\
	//                                    Main                                                \\
	//----------------------------------------------------------------------------------------\\

	public static void main(String [] args){
		//Variables
		DNA A = new DNA();
		DNA B = new DNA();
		Parameters score = new Parameters();
		boolean exit = false;
		int option;
		NeedleManWunsch needleMan;
		
		//Initialization
		input(A,B,score);
		needleMan = new NeedleManWunsch(A.getSequence().length() + 1,B.getSequence().length() + 1);
		
		//Menu Loop
		while(!exit){
			//PUT A CLEAR CONSOLE HERE
			option = menu(); //Returns the option picked by the user
			switch(option){
			case 1: //output both sequences
				System.out.println("\nOption 1: Print Both Sequences");
				System.out.println("Sequence 1: " + A.toString());
				System.out.println("Sequence 2: " + B.toString());
				System.out.println(score.toString());
				 break;
			case 2: //reset and print the initialized Needlman_Wunsh Matrix
				System.out.println("\nOption 2: Print Initialized Needleman-Wunsch Matrix");
				//needleMan.initialize();
				needleMan.output(A,B);
				 break;
			case 3: //print the result of the NeedleMan-Winsch Matrix
				System.out.println("\nOption 3: Print Results of Needleman-Wunsch Matrix (Entire Matrix)");
				needleMan.fill(A, B, score);
				needleMan.output(A,B);
				 break;
			case 4: //print 1 optimal allignment
				System.out.println("\nOption 4: Print One Optimal Alignment");
				needleMan.fill(A, B, score);
				needleMan.outputSequence(A, B);
				 break;
			case 5: //print all optimal allignment
				System.out.println("\nOption 5: Print All Optimal Alignments");
				needleMan.fill(A, B, score);
				needleMan.outputSequences(A, B);
				 break;
			case 6: //re-input both sequences
				System.out.println("Option 6: Enter Two New Sequences of DNA to Score");
				input(A,B,score);
				needleMan = new NeedleManWunsch(A.getSequence().length() + 1,B.getSequence().length() + 1);
				 break;
			case 7: //exit program
				//PUT A CLEAR CONSOLE HERE
				System.out.println("Thank you for Using this Tool");
				System.out.println("\nDesigned and Created By:");
				System.out.println("Denis Shleifman - 101001778");
				System.out.println("Joseph Hyland   - 101001778");
				exit = true;
				break;
			default:
				System.out.println("\n\nInvalid Input. Please Enter a Number between 1 and 7");
			}
		}
	}
}
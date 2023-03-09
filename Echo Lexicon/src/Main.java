import java.util.ArrayList;
import java.time.*;
import java.util.Scanner;
import java.io.*;
import java.nio.file.Path;

public class Main {
	public static void ReadNewInfo(Scanner input,Dictionary Lex) {
		String Tag= new String();
		String Buffer= new String();
		input.nextLine();
		
		ArrayList<String> Tags = new ArrayList<String>();
		
		System.out.println("Enter all tags associated with the sentence:");
		Tag= input.nextLine();
		Tags.add(Tag);
		System.out.println("Enter the sentence to store:");
		Buffer = input.nextLine();
		String Split[]= Buffer.split("\\s+");
		
		for(String Current: Split) {
			Lex.addWord(Current, Tags);
		}
	}
	public static void SearchSentence(Scanner input, Dictionary Lex) {
		//search what has been put in
				input.nextLine();
				System.out.println("Please enter a sentence to search:");
				String Buffer = input.nextLine();
				String Split[]= Buffer.split("\\s+");
				String lastTag= new String();
				
				lastTag = "default";
				for(String Current: Split) {
					Buffer = Lex.searchWord(Current,lastTag);
					System.out.print(Buffer.substring(0, Buffer.lastIndexOf(":"))+"\t\t");
					Buffer = Buffer.substring(Buffer.lastIndexOf(":")+1);
					System.out.println(Buffer);
					if(!Buffer.equalsIgnoreCase("Missing")&&!Buffer.equals("")){
						lastTag=Buffer;
					}
					//System.out.println("Updated Last tag is "+lastTag);
				}
	}
	public static void listVoices(Scanner input,Dictionary Lex) {
		input.nextLine();
		System.out.println("What word do you need voices for.");
		String Buffer = input.nextLine();
		String[] Split = Lex.searchWord(Buffer,true).split("%n");
		int i=1;
		for(String Cur: Split) {
			System.out.println(i++ +". "+Cur);
		}
		
		
	}
	private static void saveDictionary(Dictionary lexicon, String FileName){
		
		
		FileWriter Location;
		try {
			Location = new FileWriter(FileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("E.V.E: Save Failed");
			return;
		}
		PrintWriter output = new PrintWriter(Location);
		
		//output.println("Testing Save Function");
		lexicon.saveDict(output);
		output.close();
		System.out.println("E.V.E: Your Dictionary has been saved");
		
	}
	private static Dictionary loadDictionary() throws IOException{
		Dictionary Ret = new Dictionary();
		FileReader Read = new FileReader("TheLexicon.txt");
		
		ArrayList<String> Taglist = new ArrayList<String>();
		
		String SBuffer = new String();
		
		
		char thisChar;
		while((thisChar=(char)Read.read())!=-1){//While not at end of file
			
			if(thisChar=='[') {//Check to see if tag paramenter is found
				while((thisChar=(char)Read.read())!=']') {//Until tag terminates procedurally build tag
					SBuffer= SBuffer.concat(String.valueOf(thisChar));
				}
				//System.out.println("Loading Tag "+SBuffer);
				Taglist.add(SBuffer);//Add tag to taglist.
				SBuffer = "";//Reset SBuffer.				
			}
			else if(thisChar=='\"') {
				while((thisChar=(char)Read.read())!='\"') {//Until word terminates procedurally build word
					SBuffer= SBuffer.concat(String.valueOf(thisChar));
				}
				//System.out.println("Loading Word "+SBuffer);
				Ret.addWord(SBuffer, Taglist);
				SBuffer ="";//Resets string
				Taglist.clear();//Resets tags
			}
			else if(thisChar=='\r'|| thisChar=='\n') {
				
			}
			else {
				System.out.println("E.V.E: Load Complete");
				break;
			}
			
		}//End of file Reached
		Read.close();
		
		
		
		
		return Ret;
		
	}
	private static Dictionary importQuotes(Dictionary Lex) throws IOException {
		Dictionary Ret = Lex;
		
		FileReader Read = new FileReader("QuoteImport.txt");
		
		ArrayList<String> Taglist = new ArrayList<String>();
		
		String SBuffer = new String();
		
		char thisChar;
		while((thisChar=(char)Read.read())!=(char)-1){//While not at end of file
			
			if(thisChar=='[') {//Check to see if tag paramenter is found
				while((thisChar=(char)Read.read())!=']') {//Until tag terminates procedurally build tag
					SBuffer= SBuffer.concat(String.valueOf(thisChar));
				}
				System.out.println("Loading Tag "+SBuffer);
				Taglist.add(SBuffer);//Add tag to taglist.
				SBuffer = "";//Reset SBuffer.				
			}
			else if(thisChar=='\"') {
				while((thisChar=(char)Read.read())!='\"') {//Until word terminates procedurally build word
					SBuffer= SBuffer.concat(String.valueOf(thisChar));
				}
				System.out.println("Loading Quote "+SBuffer);
				String Split[]= SBuffer.split("\\s+");
				
				for(String Current: Split) {
					Ret.addWord(Current, Taglist);
				}
				SBuffer ="";//Resets string
				Taglist.clear();//Resets tags
			}
			else {
				Read.read();
				SBuffer="";
				Taglist.clear();
			}
			
			/**
			 * else if(thisChar=='\r'|| thisChar=='\n'|| thisChar==' ') {
			 *
				
			}
			else {
				System.out.println("E.V.E: Import Complete");
				break;
			}
			*/ 
			
		}//End of file Reached
		Read.close();
		
		
		
		
		
		
		return Ret;
		
	}
	public static void soundManager() {
		System.out.println("Accessing Sound Options");
		System.out.println("\t (1). Find a sound)");
		System.out.println("\t (2). Add a sound)");
		
	}

	public static void main(String[] args) {
				
		//Enter stuff into dictionary
		Scanner input= new Scanner(System.in);
		Dictionary lexicon = new Dictionary();
		int UserChoice = 0;
		
		System.out.println("E.V.E Initializing...");
		try {
			lexicon = loadDictionary();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("E.V.E: Load Failed");
			e1.printStackTrace();
		}
		
		while(UserChoice!=9) {
			System.out.printf("%s%n%s%n%s%n%s%n%s%n%s%n%s%n%n%s%n",
					"------------------------------------------------------------"
					, "Echo Voice Emulator"
					, "Please make a selection"
					, "\t(1).Add Phrase           | (5).Load Dictionary"
					, "\t(2).Search Phrase        | (6).Import Quotes from File"
					, "\t(3).List Voices by Word  | (7).Save Dictionary"
					, "\t(4).Sound Manager        | (8).Create Dictionary Backup"
					, "\t(9).Exit Program"
					);
			UserChoice= input.nextInt();
			switch(UserChoice) {
			case 1:{//Add Phrase
				ReadNewInfo(input,lexicon);
				break;
			}
			
			case 2:{//Search Phrase
				SearchSentence(input,lexicon);
				break;
			}
			case 3:{//List all voices available for a given word
				listVoices(input, lexicon);
				break;
			}
			case 4:{//Sound Options
				soundManager();
						
				break;
			}
			case 5:{//Load Dictionary
				System.out.println("WARNING: Loading a Dictionary will overwrite any unsaved changes. If you are sure you want to load type\"LOAD\" otherwise enter back or cancel");
				if(input.next().equals("LOAD")){//Only lets you Load and overwrite new data if you explicitly type it.
					try {
						lexicon = loadDictionary();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("E.V.E: Failed to Load");
						e.printStackTrace();
					}
				}
				else {
					System.out.println("E.V.E: Returning to Main Menu");
					
				}
				
				break;
			}
			case 6:{//Read in as Quotes
				System.out.println("E.V.E Adding Supplementary content from external file.");
				System.out.println("Remember when designing phrases the proper formatting is.[speaker|modifier]\"Quote\"");
				try {
					lexicon = importQuotes(lexicon);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Import Failed.");
					e.printStackTrace();
				} 
				break;
			}
			case 7:{//Save Dictionary
				saveDictionary(lexicon,"TheLexicon.txt");
				break;
			}
			case 8:{//Create a backup
				
				LocalDate today = LocalDate.now();
				String BackupFormat = "Backup-"+today.getYear()+"-"+today.getMonthValue()+"-"+today.getDayOfMonth()+".txt";
				System.out.println("Backup is named "+BackupFormat);
				saveDictionary(lexicon,BackupFormat);
				
				break;
				
			}
			case 9:{//Exit
				System.out.println("Would you like to save before you exit? Y/N");
				
				if(input.next().equalsIgnoreCase("y")){
					saveDictionary(lexicon,"TheLexicon.txt");
				}
				else {
					System.out.println("WARNING:You have chosen to Exit without saving, any unsaved data will be lost. Type \"EXIT\" to proceed");
					if(input.next().equals("EXIT")){//Only lets you exit without saving if you explicitly save.
						break;
					}
					System.out.println("E.V.E: Returning to main menu.");
					UserChoice=0;
				}				
				break;
			}
			default:{//Inform user they made a mistake
				System.out.println("E.V.E: Invalid User Input, returning to menu");
			}
				
			}
		}
		System.out.println("EVE We will see eachother again Echo"); //I Know
		
		
		input.close();
		
		
		
		
		
		
	}

	

}

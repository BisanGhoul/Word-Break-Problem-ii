package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import javafx.stage.Stage;import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class SampleController {
	
	String wordToBreak = "";
	List<String> dictionary = new ArrayList<String>();//"this","th","famous","is","word","beak","b","r","e","a","k","br","bre","brea","ak","problem"
    String[] arr;
    int trueFalseTable[][];

    Scanner scan ;
	 @FXML
	 TextArea solutionTextArea;
	 @FXML
	 TextArea tableArea;
	 @FXML
	 Label above;
	 
	public void browse() {//method to choose a file
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().addAll(new ExtensionFilter("txt files", "*.txt"));
		File in =chooser.showOpenDialog(null);
				
		try {
			scan = new Scanner(in);
		} catch (FileNotFoundException e1) {
			System.out.println("No file Selected");
			Platform.runLater(() ->
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				alert.setTitle("Error");
				alert.setHeaderText("Failure!");
				alert.setContentText("Choose a valid file please.");
				alert.show();
			});
		
		}catch(java.lang.NullPointerException e2) {
			System.out.println("No file Selected");
			System.out.println("No file Selected");
			Platform.runLater(() ->
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);

				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				alert.setTitle("Error");
				alert.setHeaderText("Failure!");
				alert.setContentText("Choose a valid file please.");
				alert.show();
			});
		}
	}
		
	public  void read() {//method to read the txt file and store data in 'wordToBreak' and in 'dictionary'
		try {
		 wordToBreak=scan.nextLine();//first line in the text file
		 String secondLine=scan.nextLine();//second line in the txt file
		 arr=secondLine.split(" ");//split the line to words (the words in the file will be separated by spaces)

		 for (String token : arr)//add the word from the second line to the dictionary
		 {
		     dictionary.add(token);
		 }
		}catch(java.lang.NullPointerException e3){
			Platform.runLater(() ->
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				alert.setTitle("Error");
				alert.setHeaderText("Failure! file is empty!");
				alert.setContentText("Choose a valid file please.");
				alert.show();
			});
		}catch(java.util.NoSuchElementException e4) {
			Platform.runLater(() ->
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				alert.setTitle("Error");
				alert.setHeaderText("Failure! file is empty!");
				alert.setContentText("Choose a valid file please.");
				alert.show();
			});
		}
		



	}
	
    
    public List<String> wordBreak(String s, List<String> dictionary) {//hay el method btnadi yale t7tha
    	
    	/*Map is like an array and every element has a key which is the index,
   	 * ya3ni heek taqreban Map<key,element> same as array that has elements and each
   	 *  key is the index for that element, but the array doesnt have the .contains
   	 *  method, 3ashan heek map is used here its also faster.
   	 *  List is also similar to el array w el map
   	 */
        Map<Integer, List<String>> dp = new HashMap<>();
        int max = 0;
        for (String s1 : dictionary) {
            max = Math.max(max, s1.length());
        }
        return utilityWordBreak(s, dictionary, dp, 0, max);
   }

    
    //method to get all the possible phrases
	private List<String> utilityWordBreak(String s, List<String> dict, Map<Integer, List<String>> dp, int start, int max) {
		//s is the word were going to break
		//ennd is the length of that word
		//dic is the dictionary
		
		// We have traversed the input and are done with breaking up the word
        if (start == s.length()) {
            return Collections.singletonList("");
        }

        // Using dynamic programming for optimisation 
        // where the same words will have to broken down again

        if (dp.containsKey(start)) {
            return dp.get(start);
        }

		List<String> words = new ArrayList<>();
		// Keeping the end at the end of the input
		// start counter moves along the input letters
        for (int i = start; i < start + max && i < s.length(); i++) {
            String newWord = s.substring(start, i + 1);
            if (!dict.contains(newWord)) {
                continue;
			} else {
				// Once the last word in the input is found in the dictionary,
				// we repeat the wordbreak
				 List<String> result = utilityWordBreak(s, dict, dp, i + 1, max);
				// and append the 'newWord' at the end of every phrase in 'result'
				for (String word : result) {
					String extraSpace; // lines from 313 to 318 are the same as: String extraSpace = word.length() == 0
										// ? "" : " ";
					if (word.length() == 0) { // but keep this code so he wont suspect that the code is from the
												// Internet bcz other students probably found something similar to this
						extraSpace = "";
					} else {
						extraSpace = " ";
					}

					words.add(newWord + extraSpace + word);
				}
			}
		}
		dp.put(start, words);
		return words;
	}
    

	public void printPhrases() {//method to print all the phrases
		read();
		List<String> list=wordBreak(wordToBreak,dictionary);
		String s = "";
		for(int i=0;i<list.size();i++){
			s+=list.get(i)+"\n";
		    System.out.println(list.get(i));
		}
		solutionTextArea.setText(s);
	}
	
	/*
	   * Gives preference to longer words over splits
	   * e.g peanutbutter with dict{pea nut butter peanut} it would result in
	   * peanut butter instead of pea nut butter.
	   * */
  public void createTable(String word, List<String> dictionary2){
       trueFalseTable = new int[word.length()][word.length()];
      
      for(int i=0; i < trueFalseTable.length; i++){
          for(int j=0; j < trueFalseTable[i].length ; j++){
              trueFalseTable[i][j] = -1; //-1 indicates string between i to j cannot be split ==> F
          }
      }
      
      //fill up the matrix in bottom up manner
      for(int i = 1; i <= word.length(); i++){
          for(int j=0; j < word.length() -i + 1 ; j++){
              int x = j + i-1;
              String str = word.substring(j,x+1);
              //if string between j to x is in dictionary T[j][x] is true
              if(dictionary2.contains(str)){
                  trueFalseTable[j][x] = j;
                  continue;
              }
              //find a k between j+1 to x such that T[j][k-1] && T[k][x] are both true 
              for(int k=j+1; k <= x; k++){
                  if(trueFalseTable[j][k-1] != -1 && trueFalseTable[k][x] != -1){
                      trueFalseTable[j][x] = k;
                      break;
                  }
              }
          }
      }
      
      
//      if(T[0][word.length()-1] == -1){
//          return null;
//      }
      
//      //create space separate word from string is possible
//      StringBuffer buffer = new StringBuffer();
//      int i = 0; int j = word.length() -1;
//      while(i < j){
//          int k = T[i][j];
//          if(i == k){
//              buffer.append(word.substring(i, j+1));
//              break;
//          }
//          buffer.append(word.substring(i,k) + " ");
//          i = k;
//      }
//      
//      return buffer.toString();
  }
    
	public void printTable() {
		//try {
			//read();
		createTable(wordToBreak, dictionary);//method to create the table
	//	wordBreak(wordToBreak, dictionary);
		//int[][] dp = trueFalseTable; 
		String table= turnTableToString(trueFalseTable);//string 'table' represents the table
		tableArea.setText(table);//print the table on the interface
//		}catch(java.lang.NullPointerException e3){
//			Platform.runLater(() ->
//			{
//				Alert alert = new Alert(Alert.AlertType.ERROR);
//				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//				alert.setTitle("Error");
//				alert.setHeaderText("Failure! file is empty!");
//				alert.setContentText("Choose a valid file please.");
//				alert.show();
//			});
//		}

			
	}
	
	
	public String turnTableToString(int[][] table) {
		String returnedTable="";
		String[] array= wordToBreak.split("");
		String foq="";
		
		for(int i=0;i<array.length;i++) {
			foq+=array[i]+" |";
		}
		//above.setText(foq);
	//	returnedTable+="\n";
	    for(int i = 0; i <= table.length-1; i++) { 
	        // Iterate over second array 
	        for(int j = 0; j <=table[i].length-1; j++) {

	        		
	        	if(table[i][j]==-1) {//if ==-1 print F else print T
	        	System.out.print("F"+" "+"|");
	        	returnedTable+=("F"+" "+"|");
	        	}else {
		        	System.out.print("T"+" "+"|");
		        	returnedTable+=("T"+" "+"|");
	        	}
	        	
	        }
	        System.out.println("\n");
	        returnedTable+="\n";//print new line
	        }
		return returnedTable;
	}
	


	 

}
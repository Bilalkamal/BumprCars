import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
//		File Paths to read 
		
		File  personsFile = new File("data/Persons.dat");
	
		File  customersFile = new File("data/Customers.dat");
		File  productFile = new File("data/Products.dat");
		
		
//		Calling the readFile method on the files paths
		
		readFile(personsFile);
		System.out.println("\n" + "\n"+ "****************************************************************" +  "\n");
		
		readFile(customersFile);
		System.out.println("\n" + "\n"+ "****************************************************************" +  "\n");
		readFile(productFile);
		System.out.println("\n" + "\n"+ "****************************************************************" +  "\n");
		
	}

	
	
//	Read file function
//	TO-DO:
//	Add returning array of lines
	
	
	
	
	public static void readFile(File file) {


		try (FileInputStream fis = new FileInputStream(file)) {
 			int content;
			while ((content = fis.read()) != -1) {
				// convert to char and display it
				System.out.print((char) content);
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
		
		
		
	}

}

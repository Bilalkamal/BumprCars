package com.bc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/*
 * The WriterFunction class has function(s) that write Strings to .txt file
 */


public class WriterFunction {
	/*
	 * Write function takes String and write it to output.txt file
	 */
	public static void write(String Line) {
		try {
			FileWriter writer = new FileWriter("data/output.txt");
			writer.write(Line);
			writer.close();
		} catch (IOException e) {
			System.out.println("Writer Erorr!!");
			e.printStackTrace();
		} 	
	}
}
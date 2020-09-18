import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JasonWriter {
	public static <T> void printJason(String filePath, ArrayList<T> list)
	{
		GsonBuilder builder= new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();
		
		try {
			PrintWriter out = new PrintWriter(new File(filePath));
			String header= "product";//know what to print
			
			out.write("{\n");
			out.write("\""+header+"\":");
			out.write(gson.toJson(list));
			out.write("}");
			out.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	//remember referash

}

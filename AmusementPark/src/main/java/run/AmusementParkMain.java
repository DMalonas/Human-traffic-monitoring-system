package run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AmusementParkMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("Map/Node_J.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st;
			while((st = br.readLine()) != null) {
				System.out.println(st);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

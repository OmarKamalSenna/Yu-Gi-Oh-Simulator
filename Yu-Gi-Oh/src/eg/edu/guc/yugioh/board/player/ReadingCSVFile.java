package eg.edu.guc.yugioh.board.player;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

public class ReadingCSVFile {
	public static ArrayList <String> readFile(String path) throws Exception{
		String currentLine = "";
		FileReader fileReader= new FileReader(path);
		ArrayList <String> s=new ArrayList<String>();
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			s.add(currentLine);
		}
		return s;
	}
	public static boolean monster(String [] s){
		return s[0].equals("Monster");
	}
	public static boolean spell(String [] s){
		return s[0].equals("Spell");
	}
	
	public static ArrayList <String> cut(ArrayList <String> s, int i){
		int j=0;int m=0;int f;String l=s.get(i);
		if (l.charAt(0)=='S')
			f=3;
		else
			f=6;
		ArrayList <String> x=new ArrayList<String>();
		while (j<f){
			m=0;
			while (m<l.length() && l.charAt(m)!=','){
				m++;
			}
			x.add(l.substring(0,m));
			if (m!=l.length())
			l=l.substring(m+1);
			j++;
		}
		return x;
	}
	
}
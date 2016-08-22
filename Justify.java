import java.util.LinkedList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.StringBuilder;
public class Justify
{
	public static final int MAX_LENGTH = 40;
	public static void main(String[] args)
	{
		//take filename
		String[] input = new String[0];
		try{input = readFile(args[0]);}
		catch(IOException e){e.printStackTrace();System.exit(0);}
		for(String s : input)
		{
			//System.out.println(justify(s));

			System.out.println(spacing(justify(s)));
		}
	}

	public static String justify(String str)
	{
		//Take 1 "line"
		LinkedList<String> lines = new LinkedList<String>();
		lines.add(str);
		int current = 0;
		String overflow = "";
		while(lines.get(current).length() > MAX_LENGTH)
		{
			String l = lines.get(current).trim();
			int breakspot = MAX_LENGTH;
			while(l.charAt(breakspot) != ' ' && breakspot != 0)
			{
				breakspot--;
				//Hyphenate if whole line has no spaces
			}
			if(breakspot==0)
			{
				lines.add(l.substring(MAX_LENGTH-1));
				lines.set(current, (l.substring(0,MAX_LENGTH-1)+"-"));
			}
			else
			{
				lines.add(l.substring(breakspot+1));
				lines.set(current, l.substring(0,breakspot));
			}
			current++;
		}
		return merge(lines.toArray(new String[lines.size()]));
	}

	public static String merge(String[] lines)
	{
		String text = "";
		for(String s : lines)
		{
			text = text + s + "\n";
		}
		return text;
	}

	public static String spacing(String text)
	{
		String[] lines = text.split("\n");
		for(int i = 0; i < lines.length; i++)
		{
			StringBuilder sb = new StringBuilder(lines[i].trim());
			int index = 0;
			while(sb.length() < MAX_LENGTH)
			{
				if(sb.toString().replaceAll("[^ ]", "").length() == 0){break;}
				index = sb.toString().indexOf(" ", index+1); //increment index to next space
				if(index == -1){index = sb.toString().indexOf(" ");} //Reset to first space
				while(sb.toString().charAt(index+1)==' '){index++;} //get last space in group

				sb.insert(index, ' ');
				index++;
			}
			lines[i] = sb.toString();
		}
		return merge(lines);
	}

	public static String[] readFile(String file) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		LinkedList<String> list = new LinkedList<String>();
		String line = br.readLine();
		String block = "";
		while(line != null)
		{
			block = block + line+"\n";
			line = br.readLine();
			if(line!= null && line.length() == 0){list.add(block.replace("\n"," "));block = "";}
			//System.out.println("LINE: "+line);
			//System.out.println("BLOCK: "+block);
		}
		if(block.length() != 0){list.add(block.replace("\n"," "));}
		return list.toArray(new String[list.size()]);
	}

}
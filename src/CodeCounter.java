import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CodeCounter {
	static long commentlines = 0;
	static long blanklines = 0;
	static long codelines = 0;

	public static void main(String[] args) {
		File f = new File("src\\");
		File[] codeFiles = f.listFiles();
		for (File child : codeFiles) {
			if (child.getName().matches(".+\\.java$")) {
				parse(child);
			}
		}

		System.out.println("commentlines:"+commentlines);
		System.out.println("blanklines:"+blanklines);
		System.out.println("codelines:"+codelines);
	}

	private static void parse(File f) {
		BufferedReader br = null;
		boolean comment = false;
		try {
			br = new BufferedReader(new FileReader(f));
			String s = null;
			try {
				while ((s = br.readLine()) != null) {
					s = s.trim();
					if (s.startsWith("/*") && (s.endsWith("*/"))) {
						commentlines++;
					} else if (s.startsWith("//")) {
						commentlines++;
					} else if (s.startsWith("/*") && !s.endsWith("*/")) {
						commentlines++;
						comment = true;
					} else if (true == comment) {
						commentlines++;
						if (s.endsWith("*/")) {
							comment = false;
						}
					} else if (s.matches("^[\\s&&[^\\n]]*$")) {
						blanklines++;
					} else {
						codelines++;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}
}

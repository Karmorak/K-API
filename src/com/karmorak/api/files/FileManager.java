package com.karmorak.api.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.karmorak.api.Main;
import com.badlogic.gdx.Application.ApplicationType;

public class FileManager {
	
	
	
//	static String android_path = "C://Users//" + System.getProperty( "user.name" ) + "//" +Main.PC_PATH;
//	static String desktop_path = "C://Users//" + System.getProperty( "user.name" ) + "//" +Main.PC_PATH;
	
	
	public static ArrayList<String> list = new ArrayList<>();
	
	private static File checkFile(String root) {
		if(root != null && !root.isEmpty()) {
			try {
				File file = new File(root);
				if(!file.exists()) {
					File file2 = new File(file.getParent());			
					file2.mkdirs();
					file.createNewFile();
				}
				return file;
			} catch (IOException e) {
				System.out.println("Error on creating new File");
				e.printStackTrace();
			} catch (Exception e) {
				System.err.println("Failed to load file.");
				e.printStackTrace();
			}		
		}
		return null;
	}
	
	private static ArrayList<String> readFile(File file) {
		ArrayList<String> list = new ArrayList<>();
		
		if(Main.type == ApplicationType.Android) {					
			FileInputStream fis = null;
			
			try {
				fis = new FileInputStream(file);
				
				String line = "";
				int content = -1;
				while ((content = fis.read())  != -1) {
					if((char) content == " ".toCharArray()[0]) {
						line = line + (char) content;
					} else {
						list.add(line);
						line = "";
					}					
				}
				
				fis.close();			
				
			} catch (FileNotFoundException e) {
				System.err.println("Android: Couldnt init FileInputStream!");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Android: FileInputStream couldnt read from file!");
				e.printStackTrace();
			} finally {
				try {
					if(fis != null)	fis.close();
				} catch (IOException e){ 
					System.err.println("Android: Couldnt close FileInputStream");
					e.printStackTrace();
				}
			}
			
		} else if (Main.type == ApplicationType.Desktop) {
			FileReader file_reader = null;
			BufferedReader br = null;
			
			String line = "";			
			
			try {
				file_reader = new FileReader(file);
				br = new BufferedReader(file_reader);

				while ((line = br.readLine()) != null) {
					list.add(line);
				}
				br.close();
			} catch (IOException e) {
				System.err.println("Desktop: Couldnt read line!");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
				} catch (IOException e) {
					System.err.println("FileManager" + "finally:br.close");
					e.printStackTrace();
				}
			}			
		}	
		return list;
	}
	
	public static String getString(String root, String key) {	
		File file = checkFile(root);		
		
				
		ArrayList<String> list = readFile(file);
		
		
		if(Main.type == ApplicationType.Android) {		
			if(!list.isEmpty()) {
				for(String s : list) {
					if(s.split("\t")[0].contains(key)) {
						return s;
					}
				}			
			}
			
		} else if (Main.type == ApplicationType.Desktop) {
			if(!list.isEmpty()) {
				for(String s : list) {
					String[] parts = s.split("\t");
					if(parts.length < 2) {
						parts = s.split(":");
					}			
					if(parts[0].equals(key) || parts[0].equals(key +":")) {
						return parts[1];
					}
				}			
			}			
		}			
		return null;	
	}
	
	public static int getInt(String root, String key) {
		String s = getString(root, key);
		if(s != null && !s.isEmpty()) {
			try {
				int i = Integer.parseInt(s);
				return i;
			} catch (NumberFormatException e) {
				System.err.println("Failed to convert from String to Integer.");
			}
		}
		
		return -1;
		
	}
	public static void setString(String root, String key, String value) {
		File file = checkFile(root);
		ArrayList<String> list = readFile(file);
		
		if(Main.type == ApplicationType.Android) {			
			FileOutputStream writer = null;
			
			try {
				writer = new FileOutputStream(file);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
			try {
				if(!list.isEmpty()) {
					for(String s : list) {
						if(s.split("\t")[0].equals(key) || s.split("\t")[0].equals(key + ":")) {
							writer.write((s.split(":", 1)[0] + value).getBytes());
						} else {
							writer.write(s.getBytes());
						}				
						writer.flush();
					}
				}
			} catch (Exception e) {
				System.err.println("Android: Failed to write on file :(" + root + ")");
				e.printStackTrace();
			} finally {
				try {
					if(writer != null) writer.close();
				} catch (IOException e){
					System.err.println("Android: Couldnt close FileOutputStream");
					e.printStackTrace();
				}
			}				
			
		} else if (Main.type == ApplicationType.Desktop) {
			FileWriter writer = null;			
			boolean found = false;
					
			try {
				writer = new FileWriter(file);
				if(!list.isEmpty()) {
					for(String s : list) {
						if(s.split("\t")[0].equals(key) || s.split("\t")[0].equals(key + ":")) {
							String part1 = s.split("\t")[0];
							writer.write(part1 + "\t" + value + "\n");
							found = true;
						} else {
							writer.write(s + "\n");
							writer.flush();	
						}
						writer.flush();
					}
				}
				
				if(!found) {
					writer.write(key + ":\t" + value + "\n");
					writer.flush();	
				}
				
				if(writer != null) {
					writer.close();
				}				
			} catch (IOException e) {
				System.err.println("Desktop: failed to write!");
				e.printStackTrace();
			}	
		}
	}
	
	public static void write(String line, String path) {
		File file = new File(path);
		FileWriter writer = null;
		
		if(!file.exists()) {
			File file2 = new File(file.getParent());			
			file2.mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("Error on creating new File");
				e.printStackTrace();
			}
			
		}		
		try {
			writer = new FileWriter(file);
			writer.write(line);		
			writer.flush();
			if(writer != null) {
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	

}

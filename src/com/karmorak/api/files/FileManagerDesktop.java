//v 1.1
package com.karmorak.api.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.karmorak.api.Main;

@Deprecated
public class FileManagerDesktop {
	
	
	/**
	 * deviceids: 0 = Desktop, 1 = Android, rest = Desktop
	 * @param name
	 * @param deviceid
	 * 
	 * 
	 */
	

	static String desktop_path = "C://Users//" + System.getProperty( "user.name" ) + "//" +Main.PC_PATH;
	
	
	public static String getString(String name) {
		FileReader file_reader = null;
		String line = "";

		File desktop_file = new File(desktop_path);
		
		BufferedReader br = null;
		if (!desktop_file.exists())
			return null;

		try {
			file_reader = new FileReader(desktop_file);
			br = new BufferedReader(file_reader);

			while ((line = br.readLine()) != null) {
				if (line.contains(name)) {
					return line.split("\t")[1];
				}
			}
			br.close();
		} catch (Exception e) {
			if (e instanceof IOException) {
				Gdx.app.error("ERROR", "ReadLine");
				return null;
			} else if (e instanceof FileNotFoundException) {
				Gdx.app.error("FileManager", "LoadFile", e);
				return null;
			} else {
				Gdx.app.error("FileManager", "", e);
				throw new RuntimeException(e);
			}
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				Gdx.app.error("FileManager", "finally:br.close", e);
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static String getString(String name, String path) {
		FileReader file_reader = null;
		String line = "";

		File desktop_file = new File(path);
		
		BufferedReader br = null;
		if (!desktop_file.exists())
			return null;

		try {
			file_reader = new FileReader(desktop_file);
			br = new BufferedReader(file_reader);

			while ((line = br.readLine()) != null) {
				if (line.contains(name)) {
					return line.split("\t")[1];
				}
			}
			br.close();
		} catch (Exception e) {
			if (e instanceof IOException) {
				Gdx.app.error("ERROR", "ReadLine");
				return null;
			} else if (e instanceof FileNotFoundException) {
				Gdx.app.error("FileManager", "LoadFile", e);
				return null;
			} else {
				Gdx.app.error("FileManager", "", e);
				throw new RuntimeException(e);
			}
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				Gdx.app.error("FileManager", "finally:br.close", e);
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public static void setString(String key, String value) {
			File file = new File(desktop_path);
			FileWriter writer = null;
			Reader rd = null;
			
			try {
				rd = new FileReader(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			BufferedReader reader = new  BufferedReader(rd);
			
			String line = "";
			ArrayList<String> lines = new ArrayList<String>();
			int count = 0;
			int line_number = -1;
			
			try {
				while((line = reader.readLine()) != null){
					lines.add(line);
					if(line.contains(key)) {
						line_number = count;
					}
					count++;	
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
				
			try {	
				if(reader != null) {
				reader.close();
				}
				
				writer = new FileWriter(file);
			
				if(line_number > -1) {
					for(int i = 0; i < lines.size(); i++) {
						
						if(i != line_number) {
							writer.write(lines.get(i) + "\n");
							writer.flush();
						} else {					
							String part1 = lines.get(i).split("\t")[0];
							writer.write(part1 + "\t" + value + "\n");
							writer.flush();				
						}

					}
				} else {
					for(int i = 0; i < lines.size(); i++) {
						writer.write(lines.get(i) + "\n");
						writer.flush();					
					}
					writer.write(key + ":\t" + value + "\n");
					writer.flush();		
				}

	
				if(writer != null) {
					writer.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
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
	
	
	public static void setString(String key, String value, String path) {
		File file = new File(path);
		FileWriter writer = null;
		Reader rd = null;
		
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
			rd = new FileReader(file);	
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new  BufferedReader(rd);
		
		String line = "";
		ArrayList<String> lines = new ArrayList<String>();
		int count = 0;
		int line_number = -1;
		
		try {
			while((line = reader.readLine()) != null){
				lines.add(line);
				if(line.contains(key)) {
					line_number = count;
				}
				count++;	
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
			
		try {	
			if(reader != null) {
			reader.close();
			}
			
			writer = new FileWriter(file);
		
			if(line_number > -1) {
				for(int i = 0; i < lines.size(); i++) {
					
					if(i != line_number) {
						writer.write(lines.get(i) + "\n");
						writer.flush();
					} else {					
						String part1 = lines.get(i).split("\t")[0];
						writer.write(part1 + "\t" + value + "\n");
						writer.flush();				
					}

				}
			} else {
				for(int i = 0; i < lines.size(); i++) {
					writer.write(lines.get(i) + "\n");
					writer.flush();					
				}
				writer.write(key + ":\t" + value + "\n");
				writer.flush();		
			}


			if(writer != null) {
				writer.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	
	
}

//	public static boolean isAndroidStorageWritable() {
//		String state = Envi
//		
//	}
//	
}

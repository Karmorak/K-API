package com.karmorak.api.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Application.ApplicationType;
import com.karmorak.api.Main;
import com.badlogic.gdx.Gdx;

@Deprecated
public class FileManagerAndroid {
	
	public static ArrayList<String> list = new ArrayList<>();
	
	public static void setString(String key, String value) {
		if(Gdx.app.getType() == ApplicationType.Android) {		
			if(Main.file != null) {
				File file = Main.file;
				
				readFile();
				
				FileOutputStream writer = null;
				
				try {				
					try {
						writer = new FileOutputStream(file);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}		
					
					for(String s : list) {
						if(s.contains(key)) {
							writer.write((s.split(":", 1)[0] + value).getBytes());
						} else {
							writer.write(s.getBytes());
						}				
						writer.flush();
					}
					
				
					writer.close();	
					
				} catch (IOException e) {
					System.out.println("AndroidLauncher Failed to write on config.yml");
					e.printStackTrace();
				} finally {
					try {
						if(writer != null) {
							writer.close();
						}
					} catch (IOException e){
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static String getString(String key) {		
		readFile();
		

		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).contains(key+":")) {
				String c_1 = list.get(i).split(" ")[1];
				return c_1;
			}
		}
		
		
		return null;	
	}

	
	private static void readFile() {
		if(Gdx.app.getType() == ApplicationType.Android) {		
			if(Main.file != null) {
				File file = Main.file;
				
				FileInputStream fis = null;
				
				try { 					
					fis = new FileInputStream(file);
					
					System.out.println("Reading File. Size : " + fis.available() + "Bytes");					
					
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
				} catch (IOException e) {
					System.out.println("AndroidLauncher Failed to load from config.yml");
					e.printStackTrace();
				} finally {
					try {
						if(fis != null) {
							fis.close();
						}
					} catch (IOException e){
						e.printStackTrace();
					}
				}
				
			}
		}
	}


	
	
	







}


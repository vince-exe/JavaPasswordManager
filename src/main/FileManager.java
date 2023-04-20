package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {
	public static String appSettingsFilePath; 
	public static String appSettingsDirPath;
	
	public static String ALGORITHM = "AES";
	public static String DEFAULT_SAVE_PATH = "null";
	
	public static final byte FAILED_CREATE_DIR_SETTINGS = 000;
	public static final byte FAILED_CREATE_FILE_SETTINGS = 001;
	public static final byte SUCCESS = 1;
	
	public static void init() {
		appSettingsFilePath = System.getenv("APPDATA") + "\\.PasswordManager\\app_settings.txt";
		appSettingsDirPath = System.getenv("APPDATA") + "\\.PasswordManager";
	}
	
	public static boolean checkPath(String path) {
		return new File(path).exists();
	}
	
	public static int handleSettingsPaths(String algo, String defSavePath) {
		File dir = new File(appSettingsDirPath);
		if(!checkPath(appSettingsDirPath)) {
			if(!dir.mkdirs()) {
				return FAILED_CREATE_DIR_SETTINGS;
			};
		}
		
		if(!checkPath(appSettingsFilePath)) {
			if(!createAppSettingsFile(algo, defSavePath)) {
				return FAILED_CREATE_FILE_SETTINGS;
			}
		}
		
		return SUCCESS;
	}
	
	public static String[] loadFileSettings() {
		File file = new File(appSettingsFilePath);
		String[] s = new String[2];
		
		try {
			Scanner sc = new Scanner(file);
			int i = 0;
			while(sc.hasNextLine()) {
				s[i++] = sc.nextLine();
			}
			
			sc.close();
			return s;
		} 
		catch (FileNotFoundException e) {
			return null;
		}
	}
	
	public static boolean createAppSettingsFile(String algo, String defSavePath) {
		try {
			FileWriter fw = new FileWriter(appSettingsFilePath);
			
			fw.write(algo + "\n");
			fw.write(defSavePath);
			
			fw.close();
			
			return true;
		} 
		catch (IOException e) {
			return false;
		}
	}
}	
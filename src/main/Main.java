package main;

import java.util.Scanner;
import password.*;

public class Main {
	private static final byte NEW_PSW = 1;
	private static final byte VIEW_PSWS = 2;
	private static final byte DEL_PSW = 3;
	private static final byte UPDT_PSW = 4;
	private static final byte DECR_PSW = 5;
	private static final byte CONF_SETTINGS = 6;
	private static final byte EXIT = 0;
	
	public static PasswordManager passwordManger = null;
	
	public static void init() {
		FileManager.init();
		/* checks if the user has already a settings file */
		if(FileManager.checkPath(FileManager.appSettingsFilePath)) {
			String[] args_ = new String[2];
			args_ = FileManager.loadFileSettings();
			if(args_ != null) {
				FileManager.ALGORITHM = args_[0];
				FileManager.DEFAULT_SAVE_PATH = args_[1];
			}
			else {
				System.out.print("\n[ FATAL ERROR ] n008, check the README.md file to manually solve it");
				System.exit(-1);
			}
		}
		else {
			/* try to create a settings file for the user */
			int err = FileManager.handleSettingsPaths(FileManager.ALGORITHM, FileManager.DEFAULT_SAVE_PATH);
			if( err != 1) {
				System.out.print("\n[ FATAl ERROR ] n" + err + ", check the README.md file to manually solve it");
				System.exit(-1);
			}
			System.out.print("\nSoftware: Successfully created the SettingsFile.txt\n\n");
		}
		
		try {
			passwordManger = new PasswordManager(FileManager.ALGORITHM);
		}
		catch(InvalidAlgorithm e) {
			System.out.print(FileManager.ALGORITHM + " isn't a valid algorithm!!");
			System.exit(-1);
		}
	}
	
	public static void main(String[] args) {
		int option = -1;
		Scanner scanner = new Scanner(System.in);
		
		init();
		
		do {	
			System.out.print("\n1)New Password" +
							 "\n2)View Passwords ( Path + Name )" + 
							 "\n3)Delete Password" +
							 "\n4)Update Password" + 
							 "\n5)Decrypt Password" + 
							 "\n6)Config Settings" + 
							 "\n0)Exit" +
							 "\n\nInsert a option ( 0 / 6 ): ");
			
			try {
				option = scanner.nextInt();
				scanner.nextLine();
				
				switch (option) {
				case NEW_PSW:
					break;
					
				case VIEW_PSWS:
					break;
				
				case DEL_PSW:
					break;
				
				case UPDT_PSW:
					break;
					
				case DECR_PSW:
					break;
				
				case CONF_SETTINGS:
					OptionsManager.configSettings(scanner);
					break;
					
				case EXIT:
					break;
					
				default:
					System.out.print("\nInsert a valid option...");
					scanner.nextLine();
					break;
				}
			}
			catch(java.util.InputMismatchException e) {
				System.out.print("\nInsert a valid option...");
				scanner.nextLine();
			}
		}while(option != EXIT);
		
		scanner.close();
		System.out.print("\nBye :)");
	}
}
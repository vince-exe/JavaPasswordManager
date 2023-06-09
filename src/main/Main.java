package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import password.*;

public class Main {
	private static final byte NEW_PSW = 1;
	private static final byte VIEW_PSWS = 2;
	private static final byte DEL_PSW = 3;
	private static final byte UPDT_PSW = 4;
	private static final byte DECR_PSW = 5;
	private static final byte BACKUP = 6;
	private static final byte LOAD_BACKUP = 7;
	private static final byte EXIT_AND_SAVE = 8;
	private static final byte EXIT = 0;
	
	public static PasswordManager passwordManager;
	public static ArrayList<String> backupList;
	
	public static void initBackups() {
		backupList = new ArrayList<String>();
		
		File folder = new File(FileManager.appBackupPath);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
		  if (listOfFiles[i].isFile()) {
			  /* store the file without .txt extension */
			  backupList.add(listOfFiles[i].getName().substring(0, listOfFiles[i].getName().length() - 4));
		  }
		}	
	}
	
	public static void init() {
		if(!FileManager.init()) {
			System.out.print("\n[ System Error ]: 999, the software failed to initialize the components.\nCheck README.md to solve the error.");
			System.exit(-1);
		};
		
		/* check if the .PasswordManager folder exist, else create it. */
		if(!FileManager.checkPath(FileManager.appDirPath)) {
			if(!FileManager.mkdir(FileManager.appDirPath)) {
				System.out.print("\n[ System Error ]: 800, the software failed to create the .PasswordManager folder.\nCheck README.md to solve the error.");
				System.exit(-1);
			};
			if(!FileManager.mkfile(FileManager.appUserPasswords)) {
				System.out.print("\n[ System Error ]: 700, the software failed to create the users_passwords file.\nCheck README.md to solve the error.");
				System.exit(-1);
			}
			System.out.print("\nSuccessfully created System Files!\n");
		}
		else {
			if(!FileManager.checkPath(FileManager.appUserPasswords)) {
				if(!FileManager.mkfile(FileManager.appUserPasswords)) {
					System.out.print("\n[ System Error ]: 700, the software failed to create the users_passwords file.\nCheck README.md to solve the error.");
					System.exit(-1);
				}
				System.out.print("\nSuccessfully created System Files!\n");
			}
		}
		
		/* check if the .Backups folder exist, else create it. */
		if(!FileManager.checkPath(FileManager.appBackupPath)) {
			if(!FileManager.mkdir(FileManager.appBackupPath)) {
				System.out.print("\n[ System Error ]: 400, the software failed to create the .Backups folder.\nCheck README.md to solve the error.");
				System.exit(-1);
			};
			System.out.print("\nSuccessfully created System Backup Folder!\n");
		}
		
		/* get all the backups name in the Folder .Backups */
		initBackups();
		passwordManager = new PasswordManager(FileManager.loadPasswords(FileManager.appUserPasswords));	
	}
	
 	public static void printPasswords() {
 		for(int i = 0; i < Main.passwordManager.getSize(); i++) {
 			Password tmp = Main.passwordManager.getPasswordAt(i);
 			System.out.print("\nIndex: " + i +
 							 "\nTitle: " + tmp.getTitle() + "\n");
 		}
 	}
	
	public static void main(String[] args) {
		int option = -1;
		String tmp;
		Scanner scanner = new Scanner(System.in);
		
		init();
		do {	
			System.out.print("\n1)New Password" +
							 "\n2)View Passwords ( Path + Name )" + 
							 "\n3)Delete Password" +
							 "\n4)Update Password" + 
							 "\n5)Decrypt Password" + 
							 "\n6)Backup Password" +
							 "\n7)Load Backup" + 
							 "\n8)Exit And Save" +
							 "\n0)Exit" + 
							 "\n\nInsert a option ( 0 / 8 ): ");
			
			try {
				option = scanner.nextInt();
				scanner.nextLine();
				
				switch (option) {
				case NEW_PSW:
					OptionsManager.newPassword(scanner);
					break;
					
				case VIEW_PSWS:
					printPasswords();
					break;
				
				case DEL_PSW:
					OptionsManager.deletePassword(scanner);
					break;
				
				case UPDT_PSW:
					OptionsManager.updatePassword(scanner);
					break;
					
				case DECR_PSW:
					OptionsManager.decryptPassword(scanner);
					break;
				
				case BACKUP:
					OptionsManager.backup(scanner);
					break;
				
				case LOAD_BACKUP:
					OptionsManager.loadBackup(scanner);
					break;
					
				case EXIT_AND_SAVE:
					if(!FileManager.storePasswords(Main.passwordManager.getPswList(), FileManager.appUserPasswords)) {
						System.out.print("\nThe software failed to store the last passwords..\nCheck the lastest backup.");
					}
					option = EXIT;
					break;
				
				case EXIT:
					System.out.print("\nAll the changes won't be saved, are you sure? ( yes / no ): ");
					tmp = scanner.nextLine().toLowerCase();
					
					if(!tmp.equals("yes")) {
						option = -1;
					}
					break;
					
				default:
					break;
				}
			}
			catch(java.util.InputMismatchException e) {
				System.out.print("\nInsert a valid option...\n");
				scanner.nextLine();
			}
		}while(option != EXIT);
		
		scanner.close();
		System.out.print("\nBye :)");
	}
}
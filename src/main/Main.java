package main;

import java.util.Scanner;
import password.*;

public class Main {
	private static final byte NEW_PSW = 1;
	private static final byte VIEW_PSWS = 2;
	private static final byte DEL_PSW = 3;
	private static final byte UPDT_PSW = 4;
	private static final byte DECR_PSW = 5;
	private static final byte BACKUP = 6;
	
	private static final byte EXIT = 0;
	
	public static PasswordManager passwordManager;
	
	public static void init() {
		FileManager.init();
		
		/* checks if the folder doesn't exist */
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
		Scanner scanner = new Scanner(System.in);
		
		init();
		do {	
			System.out.print("\n1)New Password" +
							 "\n2)View Passwords ( Path + Name )" + 
							 "\n3)Delete Password" +
							 "\n4)Update Password" + 
							 "\n5)Decrypt Password" + 
							 "\n6)Backup Password" +
							 "\n0)Exit And Save" +
							 "\n\nInsert a option ( 0 / 6 ): ");
			
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
					break;
				
				case UPDT_PSW:
					break;
					
				case DECR_PSW:
					OptionsManager.decryptPassword(scanner);
					break;
				
				case BACKUP:
					break;
					
				case EXIT:
					if(!FileManager.storePasswords(Main.passwordManager.getPswList(), FileManager.appUserPasswords)) {
						System.out.print("\nThe software failed to store the last passwords..\nCheck the lastest backup.");
					}
					break;
					
				default:
					System.out.print("\nInsert a valid option...\n");
					scanner.nextLine();
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
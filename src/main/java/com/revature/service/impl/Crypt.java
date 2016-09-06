package com.revature.service.impl;

public class Crypt {

	private Crypt(){
		throw new IllegalAccessError("Utility class");
	}
	
	/**
	 * 
	 *  This method encrypts the target with the keys.
	 *  
	 * @param target = The string you want encrypted.
	 * @param key1   = The keyword you want the target to cipher against. (Don't pass in the target again!)
	 * @param key2   = The keyword used to vary the output when similiar targets and keywords are utilized.
	 * @return       = Returns the encryped target.
	 */
	public static String encrypt(String target, String key1, String key2){
		
		String temp = target;
		
		for(int i = 0; i < key2.length(); i++){
			
			temp = encrypt(temp, key1);
		}
		
		return temp;
	}
	private static String encrypt(String target, String keyword){
		
		char[] tempPassword = target.toCharArray();
		char[] tempUsername = keyword.toCharArray();
		char[] tempResult = new char[tempPassword.length];
		
		 //Alphabets
        char[] alphabet1 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        char[] alphabet2 = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		
        int keyBase = tempUsername.length;
        int keyCeil = tempUsername.length;
        //Use a new constructor or use as is below
        //Setting to 0 then reassigning is a waste of resources
        int keyIndex;
        
		for(int i = 0; i < tempPassword.length; i++){
			
			if(Character.isAlphabetic(tempPassword[i])){
				
				keyIndex = keyBase % keyCeil;
				
				if(Character.isUpperCase(tempPassword[i]) && Character.isUpperCase(tempUsername[keyIndex]))
				{
					tempResult[i] = cipherLetter(alphabet1, alphabet1, tempPassword[i], keyIndex, tempUsername);
				}
				
				else if(Character.isUpperCase(tempPassword[i]) && Character.isLowerCase(tempUsername[keyIndex])){
					
					tempResult[i] = cipherLetter(alphabet1, alphabet2, tempPassword[i], keyIndex, tempUsername);
				}
				
				else if(Character.isLowerCase(tempPassword[i]) && Character.isUpperCase(tempUsername[keyIndex])){
					
					tempResult[i] = cipherLetter(alphabet2, alphabet1, tempPassword[i], keyIndex, tempUsername);
				}
				
				else {
					
					tempResult[i] = cipherLetter(alphabet2, alphabet2, tempPassword[i], keyIndex, tempUsername);
				}
				
				keyBase++;
			}
			
			else if(Character.isDigit(tempPassword[i])){
				
				
				tempResult[i] = cipherNumber(tempPassword[i]);
			}
			
			else {
				
				tempResult[i] = cipherOther(tempPassword[i]);
			}
		}
		
		return new String(tempResult);
	}
	private static char cipherLetter(char[] alpha1, char[] alpha2, char letter, int keyIndex, char[] keyword){
		
		//Use a new constructor or use as is below
		//Setting to 0 then reassigning is a waste of resources
		int cipherLetter;
		int indexLetter = 0;
		int indexKey = 0;
		
		for(int i = 0; i < 26; i++){
			
			if(letter == alpha1[i]){
				indexLetter = i;
				break;
			}
		}
		
		for(int j = 0; j < 26; j++){
			
			if(keyword[keyIndex] == alpha2[j]){
				
				indexKey = j;
				break;
			}
		}
		
		cipherLetter = indexLetter + indexKey;
		
		while(cipherLetter > 25){
			
			cipherLetter -= 26;
		}
		
		return alpha1[cipherLetter];
	}
	private static char cipherNumber(char number){
		
		int trueNumber = Character.getNumericValue(number);
		
		switch(trueNumber){
		
			case 0:
				return 55;
			case 1:
				return 52;
			case 2:
				return 51;
			case 3:
				return 56;
			case 4:
				return 49;
			case 5:
				return 48;
			case 6:
				return 57;
			case 7:
				return 53;
			case 8:
				return 50;
			case 9:
				return 54;
			default:
				return 48;
		}
	}
	private static char cipherOther(char other){
		
		switch(other){
		
			case 32:  return 45;
			case 33:  return 42;
			case 34:  return 41;
			case 35:  return 33;
			case 36:  return 34;
			case 37:  return 32;
			case 38:  return 92;
			case 39:  return 58;
			case 40:  return 91;
			case 41:  return 123;
			case 42:  return 64;
			case 43:  return 60;
			case 44:  return 36;
			case 45:  return 59;
			case 46:  return 47;
			case 47:  return 61;
			case 58:  return 46;
			case 59:  return 93;
			case 60:  return 125;
			case 61:  return 35;
			case 62:  return 40;
			case 63:  return 62;
			case 64:  return 37;
			case 91:  return 43;
			case 92:  return 124;
			case 93:  return 38;
			case 94:  return 39;
			case 95:  return 126;
			case 96:  return 63;
			case 123: return 44;
			case 124: return 95;
			case 125: return 94;
			case 126: return 96;
			default:  return 77;
		}
	}
	
	// Decryption
	public  static String decrypt(String target, String key1, String key2){
		
		String temp = target;
		
		for(int i = 0; i < key2.length(); i++){
		
			temp = decrypt(temp, key1);
		}
		
		return temp;
	}
	private static String decrypt(String target, String keyword){
		
		char[] tempPassword = target.toCharArray();
		char[] tempUsername = keyword.toCharArray();
		char[] tempResult = new char[tempPassword.length];
		
		 //Alphabets
        char[] alphabet1 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        char[] alphabet2 = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		
        
        int keyBase = tempUsername.length;
        int keyCeil = tempUsername.length;
        //Use a new constructor or use as is below
        //Setting to 0 then reassigning is a waste of resources
        int keyIndex;
        
		for(int i = 0; i < tempPassword.length; i++){
			
			if(Character.isAlphabetic(tempPassword[i])){
				
				keyIndex = keyBase % keyCeil;
				
				if(Character.isUpperCase(tempPassword[i]) && Character.isUpperCase(tempUsername[keyIndex]))
				{
					tempResult[i] = decipherLetter(alphabet1, alphabet1, tempPassword[i], keyIndex, tempUsername);
				}
				
				else if(Character.isUpperCase(tempPassword[i]) && Character.isLowerCase(tempUsername[keyIndex])){
					
					tempResult[i] = decipherLetter(alphabet1, alphabet2, tempPassword[i], keyIndex, tempUsername);
				}
				
				else if(Character.isLowerCase(tempPassword[i]) && Character.isUpperCase(tempUsername[keyIndex])){
					
					tempResult[i] = decipherLetter(alphabet2, alphabet1, tempPassword[i], keyIndex, tempUsername);
				}
				
				else {
					
					tempResult[i] = decipherLetter(alphabet2, alphabet2, tempPassword[i], keyIndex, tempUsername);
				}
				
				keyBase++;
			}
			
			else if(Character.isDigit(tempPassword[i])){
				
				
				tempResult[i] = decipherNumber(tempPassword[i]);
			}
			
			else {
				
				tempResult[i] = decipherOther(tempPassword[i]);
			}
		}
		
		return new String(tempResult);
	}
	private static char decipherLetter(char[] alpha1, char[] alpha2, char letter, int keyIndex, char[] keyword){
		
		//Use a new constructor or use as is below
    //Setting to 0 then reassigning is a waste of resources
		int cipherLetter = 0;
		int indexLetter = 0;
		int indexKey = 0;
		
		for(int i = 0; i < 26; i++){
			
			if(letter == alpha1[i]){
				indexLetter = i;
				break;
			}
		}
		
		for(int j = 0; j < 26; j++){
			
			if(keyword[keyIndex] == alpha2[j]){
				
				indexKey = j;
				break;
			}
		}
		
		cipherLetter = indexLetter - indexKey;
		
		while(cipherLetter < 0){
			
			cipherLetter += 26;
		}
		
		return alpha1[cipherLetter];
	}
	private static char decipherNumber(char number){
		
		int trueNumber = Character.getNumericValue(number);

		switch(trueNumber){
		
			case 0:
				return 53;
			case 1:
				return 52;
			case 2:
				return 56;
			case 3:
				return 50;
			case 4:
				return 49;
			case 5:
				return 55;
			case 6:
				return 57;
			case 7:
				return 48;
			case 8:
				return 51;
			case 9:
				return 54;
			default:
				return 48;
		}
	}
	private static char decipherOther(char other){
		
		switch(other){
		
		case 32: return 37;
		case 33: return 35;
		case 34: return 36;
		case 35: return 61;
		case 36: return 44;
		case 37: return 64;
		case 38: return 93;
		case 39: return 94;
		case 40: return 62;
		case 41: return 34;
		case 42: return 33; 
		case 43: return 91;
		case 44: return 123;
		case 45: return 32;
		case 46: return 58;
		case 47: return 46;
		case 58: return 39;
		case 59: return 45;
		case 60: return 43;
		case 61: return 47;
		case 62: return 63;
		case 63: return 96;
		case 64: return 42; 
		case 91: return 40;
		case 92: return 38;
		case 93: return 59;
		case 94: return 125;
		case 95: return 124;
		case 96: return 126;
		case 123: return 41;
		case 124: return 92;
		case 125: return 60;
		case 126: return 95;
		default: return 77;
		}
	}
}


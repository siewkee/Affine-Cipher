/*
Name: Hung Siew Kee
Student ID: 5986606
*/

import java.util.*;
import java.io.*;
import java.math.*;   

public class Affine
{
	public static void main(String [] args) throws Exception
	{
		
		// loop to request for keys until check valid = true
		boolean valid_keys = false;
		int a, b;
		int mod = 26;
		
		do
		{
			Scanner console = new Scanner(System.in);
			System.out.print("Enter Keys 01: ");
			a = console.nextInt();
			
			System.out.print("Enter Keys 02: ");
			b = console.nextInt();
			
			valid_keys = validateKeys(a, mod);
			if (valid_keys == false)
				System.out.println("Invalid keys entered! Please try again.");	
			else
				System.out.println("Keys accepted");	
		}while(valid_keys == false); 
		
		//request for mode of op 
		String operation;
		boolean valid_mode = false;
		do
		{
			Scanner console = new Scanner(System.in);
			System.out.print("Mode of Operation (Encrypt / Decrypt): ");
			operation = console.nextLine();
			
			if (operation.equalsIgnoreCase("encrypt") || operation.equalsIgnoreCase("decrypt"))
			{
				valid_mode = true;
				System.out.println("Operation mode selected");
			}
			else
				System.out.println("Invalid operation mode entered! Please try again.");
		}while(valid_mode == false);
		
		//request for file name
		Scanner console = new Scanner(System.in);
		System.out.print("Enter file name: ");
		String fileName = console.nextLine();
		
		//map all characters in array
		int lower_case_map_arr[] = new int[mod];
		int upper_case_map_arr[] = new int[mod];
		characterMap(upper_case_map_arr, lower_case_map_arr);
		
		processFile(operation, fileName, a, b, mod, lower_case_map_arr, upper_case_map_arr);

	}
	
	//convert to BigInteger to use gcd method
	//validate gcd(a, mod) = 1
	public static boolean validateKeys(int a, int mod)
	{
    	BigInteger a_bint = new BigInteger(Integer.toString(a));
    	BigInteger mod_bint = new BigInteger(Integer.toString(mod));
    	
    	BigInteger gcd = a_bint.gcd(mod_bint);
    	
    	BigInteger one = new BigInteger("1");
    	
    	if (gcd.equals(one))
    		return true;
    	else 
    		return false;
    
   }
   
   public static int findChar(int x, int char_map_arr[])
    {
    	boolean found = false;
    	int i = 0;
    	
    	while(found == false)
    	{
    		if (char_map_arr[i] == x)    	
    			found = true;
    		else
    			i++;	
    	}
    	
    	return i;
    	
    }
    
    public static void characterMap(int upper_case_map_arr[], int lower_case_map_arr[])
    {
    	int char_a = 97;
    	int char_A = 65;
    	
		 for (int i = 0; i < 26; i++)
		 {
		 	upper_case_map_arr[i] = char_A;
		 	char_A += 1;
		 }

		 for (int i = 0; i < 26; i++)
		 {
		 	lower_case_map_arr[i] = char_a;
		 	char_a += 1;
		}
	}
    
    public static void processFile(String operation, String fileName, int a, int b, int mod, 
    				int lower_case_map_arr[], int upper_case_map_arr[]) throws IOException
    {
    	FileInputStream in = null;
    	FileOutputStream out = null;
    	
    	try
    	{
    		in = new FileInputStream(fileName);
    		
    			if (operation.equalsIgnoreCase("encrypt"))
    			{
    				out = new FileOutputStream("Cipher.txt");
    				int read_char;
    				while ((read_char = in.read()) != -1)
    				{
    					char read_char_c = (char)read_char;
		 				if(Character.isLetter(read_char_c))
		 				{
		 					if (Character.isUpperCase(read_char_c))
		 					{
			 					int index = findChar(read_char, upper_case_map_arr);
				 				int c = encrypt_affine(index, a, b, mod);
				 				out.write((char)upper_case_map_arr[c]);
				 			}
				 			if (Character.isLowerCase(read_char_c))
				 			{
				 				int index = findChar(read_char, lower_case_map_arr);
				 				int c = encrypt_affine(index, a, b, mod);
				 				out.write((char)lower_case_map_arr[c]);
				 			}
			 			}
			 			else
			 				out.write((char)read_char);
			 		}	
    			}
    			else
    			{
    				out = new FileOutputStream("Plaintext.txt");
		 			int read_char;
    				while ((read_char = in.read()) != -1)
    				{
    					char read_char_c = (char)read_char;
		 				if(Character.isLetter(read_char_c))
		 				{
		 					if (Character.isUpperCase(read_char_c))
		 					{
			 					int index = findChar(read_char, upper_case_map_arr);
				 				int c = decrypt_affine(index, a, b, mod);
				 				out.write((char)upper_case_map_arr[c]);
				 			}
				 			else
				 			{
				 				int index = findChar(read_char, lower_case_map_arr);
				 				int c = decrypt_affine(index, a, b, mod);
				 				out.write((char)lower_case_map_arr[c]);
				 			}
			 			}
			 			else
			 				out.write((char)read_char);
			 		}	
    			}		
    		System.out.println("File has been processed!");
    	}
    	finally
    	{
    		if (in != null)
    			in.close();
    		if (out != null)
    			out.close();
    	}
    }
   
    public static int encrypt_affine(int index, int a, int b, int mod)
    {
    	int c = (index * a + b) % mod;
		return c;
    }
    
    public static int decrypt_affine(int index, int a, int b, int mod)
    {
    	int inverse_a = inverseMod(a, mod);
		int m = ((index - b) * inverse_a) % mod;
		
		//for negative values
		if (m < 0)
		 	m += mod;
		
		return m;
    }
    
    public static int inverseMod(int a, int m)
    { 
        int m0 = m; 
        int y = 0, x = 1; 
  
        if (m == 1) 
            return 0; 
  
        while (a > 1) 
        { 
            // q is quotient 
            int q = a / m; 
  
            int t = m; 
  
            // m is remainder now, process 
            // same as Euclid's algo 
            m = a % m; 
            a = t; 
            t = y; 
  
            // Update x and y 
            y = x - q * y; 
            x = t; 
        } 
  
        // Make x positive 
        if (x < 0) 
            x += m0; 
  
        return x; 
    }
}




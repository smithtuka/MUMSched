package mum.swe.mumsched.util;

import java.util.Random;

public class MUtils {

	public static String newPassword(int limit) {
	    Random rand=new Random();
	    String pool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	    
	    StringBuilder res=new StringBuilder();
	    for (int i = 0; i < limit; i++) {
	       int randIndex=rand.nextInt(pool.length()); 
	       res.append(pool.charAt(randIndex));            
	    }
	    return res.toString();		
	}
}

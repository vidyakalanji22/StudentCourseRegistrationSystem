/**
 * 
 */
package com.scr.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.scr.vo.BookVO;
import com.scr.vo.CourseVO;
import com.scr.vo.CurriculumVO;
import com.scr.vo.ScheduleVO;

public class CommonUtils {
	public static java.sql.Timestamp convertUtilDateToSqlDate(java.util.Date date) {
		java.sql.Timestamp sqlDate = (java.sql.Timestamp)new java.sql.Timestamp(date.getTime());
		System.out.println(sqlDate);
		return sqlDate;
	}

	public void printCourseList(CourseVO courseVO){
		if(courseVO != null){
			System.out.println("\n Course Details for Course : "+courseVO.getCourseName());
			System.out.println("Course Description : "+courseVO.getCourseDesc());
			System.out.println("Course Amount : "+courseVO.getCourseAmount());
			System.out.println("Course ID : "+courseVO.getCourseId());
			List<ScheduleVO> scheduleVOs = courseVO.getScheduleList();
			if(scheduleVOs!=null){
				for (ScheduleVO scheduleVO : scheduleVOs) {
					System.out.println(scheduleVO.toString());
				}
			}

			List<BookVO> bookVOs = courseVO.getBooksList();
			if(bookVOs!=null){
				for (BookVO bookVO : bookVOs) {
					System.out.println(bookVO.toString());
				}
			}


			List<CurriculumVO> curriculumVOs = courseVO.getCurriculumList();
			if(curriculumVOs!=null){
				for (CurriculumVO curriculumVO : curriculumVOs) {
					System.out.println(curriculumVO.toString());
				}
			}
		}
	}
	
	/**
	 * Password Encryption
	 */
	public static String encrypt(String key, String initVector, String value) {
//        try {
//            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
//            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
//
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//
//            byte[] encrypted = cipher.doFinal(value.getBytes());
//            System.out.println("encrypted string: "
//                    + Base64.encodeBase64String(encrypted));
//
//            return Base64.encodeBase64String(encrypted);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
		
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     md.update(value.getBytes());
	     
	     byte byteData[] = md.digest();

	     //convert the byte to hex format method 1
	     StringBuffer sb = new StringBuffer();
	     for (int i = 0; i < byteData.length; i++) {
	      sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	     }
	  
	     System.out.println("Hex format : " + sb.toString());
	     
	     //convert the byte to hex format method 2
	     StringBuffer hexString = new StringBuffer();
	 	for (int i=0;i<byteData.length;i++) {
	 		String hex=Integer.toHexString(0xff & byteData[i]);
		     	if(hex.length()==1) hexString.append('0');
		     	hexString.append(hex);
	 	}
	 	System.out.println("Hex format : " + hexString.toString());

        return hexString.toString();
    }

    public static String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
public static void main(String args[]){
	String password = "password1";
	 MessageDigest md = null;
	try {
		md = MessageDigest.getInstance("SHA-256");
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     md.update(password.getBytes());
     
     byte byteData[] = md.digest();

     //convert the byte to hex format method 1
     StringBuffer sb = new StringBuffer();
     for (int i = 0; i < byteData.length; i++) {
      sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
     }
  
     System.out.println("Hex format : " + sb.toString());
     
     //convert the byte to hex format method 2
     StringBuffer hexString = new StringBuffer();
 	for (int i=0;i<byteData.length;i++) {
 		String hex=Integer.toHexString(0xff & byteData[i]);
	     	if(hex.length()==1) hexString.append('0');
	     	hexString.append(hex);
 	}
 	System.out.println("Hex format : " + hexString.toString());
	//System.out.println(decrypt(Constants.KEY, Constants.INTVECTOR, "f0ba0c14c658c08f0695fa0dc1fb55a63166d8bc"));
}

}

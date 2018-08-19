package com.matrix.spring;

import org.springframework.stereotype.Component;

@Component
public class FormatCheck {
	/**파일형식 검사*/
	public boolean isFileFormat(String file) {
		boolean result = false;
		if(file==null) {
			return true;
		}
		file=file.toLowerCase();
		String[] texts= file.split("\\.");
		String format = texts[texts.length-1];
		switch (format) {
		case "pdf" :
		case "doc":
		case "docx":
		case "hwp":
		case "xls":
		case "xlsx":
		case "png":
		case "jpg":
		case "jpeg":
			result=true;
			break;
		}
		return result;
	}
	
	/**번호 형식 검사*/
	public boolean isNumberFormat(String num){
		boolean result=false;
		if(num==null){
			return true;
		}
		try{
			Integer.parseInt(num);
			result = true;
		} catch(NumberFormatException e){
		}
		
		return result;
	}
	
	/**입력값 길이 검사*/
	public boolean isInputLength(String input, int minDigit, int maxDigit) {
		boolean result = false;
		if(input==null) {
			return true;
		}
		if((input.length()>=minDigit)&&(input.length()<=maxDigit)){
			result=true;
		}
		
		return result;
	}
	
	/**이메일 도메인 형식 검사*/
	public boolean isDomainFormat(String emailDomain) {
		boolean result = false;
		if(emailDomain==null){
			return true;
		} 
		emailDomain = emailDomain.toLowerCase();
		String[] texts = emailDomain.split("\\.");
		String domain = texts[texts.length-1];
		switch(domain) {
		case "com":
		case "net":
		case "kr":
			result=true;
			break;
		}
		
		return result;
	}
	
	/**입력받은 문자열의 byte크기 리턴*/
	public int getByteSize(String s) {
	    int count = 0;
	    char[] ch = s.toCharArray();
	    for (int i = 0; i < s.length(); i++) {
	      if (ch[i] >= '\u0020' && ch[i] <= 'z') {
	    	  count++;
	      } else if (ch[i] >= '\uAC00' && ch[i] <= '\uD7A3') {	//한글 가~힣
	    	  count += 3;
	      } else if (ch[i] >= '\u3131' && ch[i] <= '\u3163') {	//한글 자음
	    	  count += 3;
	      } else if (ch[i] > 'z' || ch[i] < '\u0020'){
	    	  throw new RuntimeException("wrong input char type");
	      }
	    }
	    return count;
	}
	
}

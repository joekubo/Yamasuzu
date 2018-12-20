package yamasuzu_myshopsoft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateEmail {
    public static boolean validateEmail(String email){
        boolean status = false;
        
        String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches()){
            status = true;
        }else{
            status = false;
        }
        return status;
    }
    
}

package helper;

import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomNumberGenerator {
    public static int  getRandomNumber(int max) {
        Random random = new Random();
        int answer = random.nextInt(max);
        return answer;
    }
    
    
    public static String getHorodatedFileName() {
    	SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
		Date today = new Date();
    	return "customScenario_"+date.format(today)+".xml";
    }
}

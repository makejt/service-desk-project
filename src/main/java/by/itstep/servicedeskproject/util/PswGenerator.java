package by.itstep.servicedeskproject.util;

import java.security.SecureRandom;
public class PswGenerator {
    public static final String CHARS1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String CHARS2 = "abcdefghijklmnopqrstuvwxyz";
    public static final String CHARS3 = "0123456789";
    public static final String CHARS4 = "~!@#$%^&*_-+='|(){}[]:;<>,.?/";
    public static final int CHARS1_NUMBER = 2;
    public static final int CHARS2_NUMBER = 3;
    public static final int CHARS3_NUMBER = 2;
    public static final int CHARS4_NUMBER = 1;
    public static String generatePsw() {

        SecureRandom random = new SecureRandom();
        StringBuilder psw = new StringBuilder();

        for (int i = 0; i < CHARS1_NUMBER; i++) {
            int randomIndex = random.nextInt(CHARS1.length());
            psw.append(CHARS1.charAt(randomIndex));
        }

        for (int i = 0; i < CHARS2_NUMBER; i++) {
            int randomIndex = random.nextInt(CHARS2.length());
            psw.append(CHARS2.charAt(randomIndex));
        }

        for (int i = 0; i < CHARS3_NUMBER; i++) {
            int randomIndex = random.nextInt(CHARS3.length());
            psw.append(CHARS3.charAt(randomIndex));
        }

        for (int i = 0; i < CHARS4_NUMBER; i++) {
            int randomIndex = random.nextInt(CHARS4.length());
            psw.append(CHARS4.charAt(randomIndex));
        }
        return psw.toString();
    }
}
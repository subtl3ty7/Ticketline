package at.ac.tuwien.sepm.groupphase.backend.util;

import org.springframework.util.StringUtils;

import java.security.SecureRandom;

public abstract class CodeGenerator {
    private static final String DIGITS = "0123456789";
    private static final String CHARACTERS = "ABCDEFGHIJKLMNPQRSTUVWXYZ";

    public static String generateCode(int length, char type) {
        if (length <= 0) {
            return "";
        }
        StringBuilder code = new StringBuilder(length);
        SecureRandom random = new SecureRandom();
        code.append(type);
        random.setSeed(System.nanoTime());
        for (int i = 1; i < 4; i++) {
            int position = random.nextInt(DIGITS.length());
            code.append(DIGITS.charAt(position));
        }
        for (int i = 4; i < length; i++) {
            int position = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(position));
        }
        return code.toString();
    }

    public static String generateUserCode() {
        int userCodeLength = 6;
        char type = 'U';
        return generateCode(userCodeLength, type).toUpperCase();
    }
}
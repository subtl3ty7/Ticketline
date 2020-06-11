package at.ac.tuwien.sepm.groupphase.backend.util;

import org.springframework.util.StringUtils;

import java.security.SecureRandom;

public abstract class CodeGenerator {
    private static final String DIGITS = "0123456789";
    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNPQRSTUVWXYZ";
    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnpqrstuvwxyz";

    public static String generateCode(int length, char type) {
        if (length <= 0) {
            return "";
        }
        StringBuilder code = new StringBuilder(length);
        SecureRandom random = new SecureRandom();
        random.setSeed(System.nanoTime());

        if (type == 'R') {
            String allCharactersAndDigits = DIGITS + UPPERCASE_CHARACTERS +  LOWERCASE_CHARACTERS;
            for (int i = 0; i < length; i++) {
                int position = random.nextInt(allCharactersAndDigits.length());
                code.append(allCharactersAndDigits.charAt(position));
            }
        }

        if (type == 'I') {
            for (int i = 0; i < length; i++) {
                int position = random.nextInt(DIGITS.length());
                code.append(DIGITS.charAt(position));
            }
            return code.toString();
        }

        code.append(type);
        for (int i = 1; i < 4; i++) {
            int position = random.nextInt(DIGITS.length());
            code.append(DIGITS.charAt(position));
        }
        for (int i = 4; i < length; i++) {
            int position = random.nextInt(UPPERCASE_CHARACTERS.length());
            code.append(UPPERCASE_CHARACTERS.charAt(position));
        }
        return code.toString();
    }

    public static String generateUserCode() {
        int userCodeLength = 6;
        char type = 'U';
        return generateCode(userCodeLength, type).toUpperCase();
    }

    public static String generateEventCode() {
        int eventCodeLength = 6;
        char type = 'E';
        return generateCode(eventCodeLength, type).toUpperCase();
    }

    public static String generateTicketCode() {
        int eventCodeLength = 6;
        char type = 'T';
        return generateCode(eventCodeLength, type).toUpperCase();
    }

    public static String generateResetPasswordCode() {
        int resetPasswordCodeLength = 25;
        char type = 'R';
        return generateCode(resetPasswordCodeLength, type);
    }

    public static String generateNewsCode() {
        int newsCodeLength = 6;
        char type = 'N';
        return generateCode(newsCodeLength, type).toUpperCase();
    }

    public static String generateMerchandiseProductCode() {
        int merchandiseProductCodeLength = 6;
        char type = 'M';
        return generateCode(merchandiseProductCodeLength, type).toUpperCase();
    }

    public static String generateInvoiceNumber() {
        int invoiceNumberLength = 6;
        char type = 'I';
        return generateCode(invoiceNumberLength, type);
    }
}
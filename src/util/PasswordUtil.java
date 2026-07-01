package util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    
    /**
     * Menghasilkan hash BCrypt dari teks sandi (plaintext).
     */
    public static String hashPassword(String plainTextPassword) {
        if (plainTextPassword == null) return null;
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
    
    /**
     * Memeriksa apakah teks sandi (plaintext) cocok dengan hash BCrypt yang tersimpan.
     */
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        if (plainTextPassword == null || hashedPassword == null) return false;
        try {
            return BCrypt.checkpw(plainTextPassword, hashedPassword);
        } catch (IllegalArgumentException e) {
            // Jika hash tidak valid (mungkin teks biasa sebelum dienkripsi)
            return false;
        }
    }
}

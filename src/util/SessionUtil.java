package util;

import model.Petugas;

public class SessionUtil {
    private static Petugas currentUser;

    public static void setCurrentUser(Petugas user) {
        currentUser = user;
    }

    public static Petugas getCurrentUser() {
        return currentUser;
    }

    public static void clearSession() {
        currentUser = null;
    }
}

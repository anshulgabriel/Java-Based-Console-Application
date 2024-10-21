package Repository;

import Entity.User;
import java.util.HashSet;
import java.util.Set;

public class UserRepository {

    private static Set<User> userSet = new HashSet<>();

    public static Set<User> getUserSet() {
        return userSet;
    }

    public static boolean setUser(User user)
    {
        return userSet.add(user);
    }

    public static boolean removeUser(User user)
    {
        return userSet.remove(user);
    }
}

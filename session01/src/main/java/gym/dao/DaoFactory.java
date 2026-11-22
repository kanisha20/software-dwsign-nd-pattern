package gym.dao;

public class DaoFactory {

    public static MemberDao getMemberDao(String type) {
        if (type.equalsIgnoreCase("jdbc")) {
            return new MemberDaoJdbc();
        } else if (type.equalsIgnoreCase("memory")) {
            return new MemberDaoMemory();
        } else {
            throw new IllegalArgumentException("Invalid DAO type");
        }
    }
}

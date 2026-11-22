package gym.dao;

import java.util.List;

public class DaoDemo {

    public static void main(String[] args) {
        MemberDao dao = DaoFactory.getMemberDao("jdbc");
        List<Member> list = dao.findByCity("Surat");

        if (list.isEmpty()) {
            System.out.println("No members found");
        } else {
            for (Member m : list) {
                System.out.println(
                    m.getMemberId() + " " +
                    m.getMemberName() + " " +
                    m.getCity() + " " +
                    m.getPlanMonths() + " " +
                    m.getFee()
                );
            }
        }
    }
}

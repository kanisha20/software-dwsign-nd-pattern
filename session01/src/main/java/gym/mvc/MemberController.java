package gym.mvc;

import gym.dao.Member;
import gym.dao.MemberDao;
import gym.dao.DaoFactory;

public class MemberController {

    private MemberView view;
    private MemberDao dao;

    public MemberController(MemberView view) {
        this.view = view;
        dao = DaoFactory.getMemberDao("jdbc");
    }

    public void start() {
        while (true) {
            try {
                view.showMenu();
                int choice = view.getChoice();

                if (choice == 1) {
                    String name = view.getName();
                    String city = view.getCity();
                    int months = view.getMonths();
                    double fee = view.getFee();

                    Member m = new Member(0, name, city, months, fee);
                    dao.addMember(m);
                    view.showMessage("Member added successfully!");
                } else if (choice == 2) {
                    String city = view.getCity();
                    view.displayMembers(dao.findByCity(city));
                } else if (choice == 3) {
                    view.showMessage("Thank you!");
                    break;
                } else {
                    view.showMessage("Invalid choice!");
                }
            } catch (Exception e) {
                view.showMessage("Error: " + e.getMessage());
            }
        }
    }
}

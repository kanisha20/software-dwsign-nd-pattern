package gym.mvc;

import java.util.Scanner;
import java.util.List;
import gym.dao.Member;

public class MemberView {

    private Scanner sc = new Scanner(System.in);

    public void showMenu() {
        System.out.println("\n===== GYM MANAGEMENT =====");
        System.out.println("1. Add Member");
        System.out.println("2. Search Members By City");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    public int getChoice() {
        return Integer.parseInt(sc.nextLine());
    }

    public String getName() {
        System.out.print("Enter member name: ");
        return sc.nextLine();
    }

    public String getCity() {
        System.out.print("Enter city: ");
        return sc.nextLine();
    }

    public int getMonths() {
        System.out.print("Enter plan months: ");
        return Integer.parseInt(sc.nextLine());
    }

    public double getFee() {
        System.out.print("Enter fee: ");
        return Double.parseDouble(sc.nextLine());
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void displayMembers(List<Member> list) {
        if (list.isEmpty()) {
            System.out.println("No members found");
            return;
        }

        System.out.println("\nID  Name  City  Months  Fee");
        for (Member m : list) {
            System.out.println(
                m.getMemberId() + "  " +
                m.getMemberName() + "  " +
                m.getCity() + "  " +
                m.getPlanMonths() + "  " +
                m.getFee()
            );
        }
    }
}

package gym.mvc;

public class GymApp {

    public static void main(String[] args) {
        MemberView view = new MemberView();
        MemberController controller = new MemberController(view);
        controller.start();
    }
}

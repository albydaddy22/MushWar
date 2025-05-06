package Pages;

public class Main {
    public static void main(String[] args) {
        HomePage hp = new HomePage();
        int id = hp.getId();
        Game g = new Game(id);
    }
}
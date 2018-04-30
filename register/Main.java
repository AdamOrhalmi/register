package register;

/**
 * Created by jaro on 3.2.2014.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        register.Register register = new ArrayRegister(20);

        register.addPerson(new register.Person("Janko Hrasko", "0900123456"));

        register.ConsoleUI ui = new register.ConsoleUI(register);
        ui.run();
    }
}

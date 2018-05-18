package register;

import java.sql.DriverManager;

/**
 * Created by jaro on 3.2.2014.
 */
public class Main {
    public static final String URL ="jdbc:postgresql://localhost/personregister";
    public static final String USER="postgres";
    public static final String PASSWORD ="degoran123";

    public static void main(String[] args) throws Exception {
        register.Register register = new ListRegister();
        SORM sorm = new SORM(DriverManager.getConnection(URL, USER, PASSWORD));
        Class<?> clazz = Person.class;

        Person p = new Person("Janko Hrasko", "0900123456");
        register.addPerson(p);


        register.ConsoleUI ui = new register.ConsoleUI(register, sorm);
        ui.run();

    }
}

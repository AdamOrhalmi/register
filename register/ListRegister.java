package register;

import java.io.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListRegister implements Register, Serializable {



    List<Person> listRegister = new ArrayList<>();
    private int count;
    private static final String FILENAME = "data.bin";

    public ListRegister() throws SQLException {

    }

    @Override
    public int getCount() {

        return listRegister.size() + 1;
    }

    @Override
    public int getSize() {
        return listRegister.size();
    }

    @Override
    public Person getPerson(int index) {

        return listRegister.get(index);
            }

    @Override
    public void addPerson(Person person) {
        listRegister.add(person);

    }

    @Override
    public Person findPersonByName(String name) {

        for (Person p : listRegister) {
            if (p.getName().equals(name)) {
                return p;
            }
        }

        return null;
    }

    @Override
    public Person findPersonByPhoneNumber(String phoneNumber) {
        for (Person p : listRegister) {
            if (p.getPhoneNumber().equals(phoneNumber)) {
                return p;
            }
        }

        return null;
    }

    @Override
    public void removePerson(Person person) {
        listRegister.remove(person);
    }

    public void sort() {

        Collections.sort(listRegister);

    }

    public void save() {


        try (FileOutputStream os = new FileOutputStream(FILENAME);
                 ObjectOutputStream oos = new ObjectOutputStream(os);) {
            oos.writeObject(this);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static ListRegister load() {


        try (FileInputStream is = new FileInputStream(FILENAME);
                 ObjectInputStream ois = new ObjectInputStream(is)) {
            return (ListRegister) ois.readObject();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;

        }

    }
}

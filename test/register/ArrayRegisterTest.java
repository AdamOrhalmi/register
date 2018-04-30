package test.register;

import register.ArrayRegister;
import register.Person;

import static org.junit.Assert.*;

public class ArrayRegisterTest {

    @org.junit.Test
    public void getPerson() {
        register.ArrayRegister register = new ArrayRegister(20);
        Person p = new Person ("A B", "0900123549");
        register.addPerson(p);

        assertEquals(p,register.getPerson(0));
    }

    @org.junit.Test
    public void addPerson() {
        register.ArrayRegister register = new ArrayRegister(20);
//        register.addPerson(new Person("A B", "0900123458"));
        assertEquals(0,register.getCount());
        register.addPerson(new Person("A B", "0900123549"));
        assertNotNull(register.getCount());
        assertEquals(1,register.getCount());

    }

    @org.junit.Test
    public void findPersonByName() {
        register.ArrayRegister register = new ArrayRegister(20);
        Person p = new Person ("A B", "0900123549");
        register.addPerson(p);
        assertNull(register.findPersonByName("Janko"));
        assertNotNull(register.findPersonByName("A B"));

        assertEquals(p, register.findPersonByName("A B"));
    }

    @org.junit.Test
    public void findPersonByPhoneNumber() {
        register.ArrayRegister register = new ArrayRegister(20);
        Person p = new Person ("A B", "0900123549");
        register.addPerson(p);
        assertNull(register.findPersonByPhoneNumber("0903951753"));
        assertNotNull(register.findPersonByPhoneNumber("0900123549"));
        assertEquals(p,register.findPersonByPhoneNumber("0900123549"));


    }

    @org.junit.Test
    public void removePerson() {
        register.ArrayRegister register = new ArrayRegister(20);
        register.addPerson(new Person("A B", "0900123549"));
       assertNotNull(register.findPersonByName("A B"));
        register.removePerson(register.getPerson(1));
        assertNull(register.getPerson(1));
    }

}
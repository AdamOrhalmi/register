package test.register;

import register.ArrayRegister;
import register.Person;

import static org.junit.Assert.*;

public class ArrayRegisterTest {

    @org.junit.Test
    public void getPerson() {
    }

    @org.junit.Test
    public void addPerson() {
        register.ArrayRegister register = new ArrayRegister(20);
//        register.addPerson(new Person("A B", "0900123458"));
        register.addPerson(new Person("A B", "0900123549"));
        assertEquals(1,register.getCount());

    }

    @org.junit.Test
    public void findPersonByName() {
        register.ArrayRegister register = new ArrayRegister(20);
        register.addPerson(new Person("A B", "0900123549"));
        assertNotNull(register.findPersonByName("A B"));
    }

    @org.junit.Test
    public void findPersonByPhoneNumber() {
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
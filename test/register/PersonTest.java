package test.register;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import register.Person;

import static org.junit.Assert.*;

public class PersonTest {

    @Test
    public void getName() {
        register.Person p1 = new Person("A B", "0900123456");
        assertEquals("A B", p1.getName());
        assertNotEquals("C D", p1.getName());
    }

    @Test
    public void setName() {
        Person p1 = new Person("A B", "0900123456");
        p1.setName("Johnny");
        assertEquals("Johnny", p1.getName());
    }

    @Test
    public void getPhoneNumber() {
        Person p1 = new Person("A B", "0900123456");
        assertEquals("0900123456", p1.getPhoneNumber());
    }

    @Test
    public void setPhoneNumber() {
        Person p1 = new Person("A B", "0900123456");
        p1.setPhoneNumber("0900654321");
        assertEquals("0900654321", p1.getPhoneNumber());
    }

    @Test
    public void toStringConversion() {
        Person p1 = new Person("A B", "0900123456");
        assertEquals("A B (0900123456)", p1.toString());
    }
}
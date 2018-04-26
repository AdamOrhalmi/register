package register;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User interface of the application.
 */
public class ConsoleUI {
    /** arrayRegister.ArrayRegister of persons. */
    private Register register;
    
    /**
     * In JDK 6 use Console class instead.
     * @see this.readLine()
     */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * Menu options.
     */
    private enum Option {
        PRINT, ADD, UPDATE, REMOVE, FIND, EXIT
    };
    
    public ConsoleUI(Register register) {
        this.register = register;
    }
    
    public void run() {
        while (true) {
            switch (showMenu()) {
                case PRINT:
                    printRegister();
                    break;
                case ADD:
                    addToRegister();
                    break;
                case UPDATE:
                    updateRegister();
                    break;
                case REMOVE:
                    removeFromRegister();
                    break;
                case FIND:
                    findInRegister();
                    break;
                case EXIT:
                    return;
            }
        }
    }
    
    private String readLine() {
        //In JDK 6.0 and above Console class can be used
        //return System.console().readLine();
        
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }
    
    private Option showMenu() {
        System.out.println("Menu.");
        for (Option option : Option.values()) {
            System.out.printf("%d. %s%n", option.ordinal() + 1, option);
        }
        System.out.println("-----------------------------------------------");
        
        int selection = -1;
        do {
            System.out.println("Option: ");
            selection = Integer.parseInt(readLine());
        } while (selection <= 0 || selection > Option.values().length);
        
        return Option.values()[selection - 1];
    }
    

    private void printRegister() {
        for (int i = 0; i< register.getSize(); i++){
            if(register.getPerson(i)!=null) {
                System.out.println(i + 1 + ". " + register.getPerson(i));
            }
        }
    }
    
    private void addToRegister() {
        System.out.println("Enter Name: ");
        String name = readLine();
        System.out.println("Enter Phone Number: ");
        String phoneNumber = readLine();
        
        register.addPerson(new register.Person(name, phoneNumber));
    }
    

    private void updateRegister() {
        System.out.println("Enter index: ");

        int index = Integer.parseInt(readLine());
        if(register.getPerson(index - 1)!=null) {
            register.Person person = register.getPerson(index - 1);

            String number = person.getPhoneNumber();
            String name = person.getName();
            register.Person newPerson = new register.Person(person.getName(), person.getPhoneNumber());
            System.out.println("what do you want to change? 1. Name 2. Number");
            index = Integer.parseInt(readLine());
            if (index == 1) {
                System.out.println("Write new name: ");
                name = readLine();
                newPerson.setName(name);
                register.removePerson(person);
                register.addPerson(newPerson);
            }
            if (index == 2) {
                System.out.println("Write new telephone number: ");
                number = readLine();
                newPerson.setPhoneNumber(number);
                register.removePerson(person);
                register.addPerson(newPerson);
            }
        }
        else System.out.println("wrong index.");


    }
    

    private void findInRegister() {
        System.out.println("How do you want to find the person? 1. by name  2. by telephone number");
       int index = Integer.parseInt(readLine());
        if (index == 1) {
            System.out.println("Write the name: ");
            String name = readLine();
            Person p = register.findPersonByName(name);
            if(p!=null) {
                System.out.println(p.toString());
            }else
                System.out.println("Person not found.");
        }
        if (index == 2) {
            System.out.println("Write the telephone number: ");
            String number = readLine();

            Person p = register.findPersonByPhoneNumber(number);
            if(p!=null) {
                System.out.println(p.toString());
            }else
                System.out.println("Person not found.");

        }

    }
    
    private void removeFromRegister() {
        System.out.println("Enter index: ");
        int index = Integer.parseInt(readLine());
        register.Person person = register.getPerson(index - 1);
        register.removePerson(person);
    }

}

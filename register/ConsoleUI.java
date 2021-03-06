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
    private SORM sorm;
    
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
    }
    
    public ConsoleUI(Register register, SORM sorm) {
        this.register = register;
        this. sorm = sorm;
    }
    
    public void run() {
        Register registerNew = ListRegister.load();

        if (registerNew!=null){
            this.register= registerNew;
        }else{
            System.err.println("no data loaded.");
        }

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
                    try {
                        sorm.truncate(Person.class);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for(int i=0; i<register.getSize(); i++){
                        Person p = register.getPerson(i);
                        try {
                            sorm.insert(p);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    register.save();
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
        register.sort();
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
        try {
            register.addPerson(new register.Person(name, phoneNumber));
        }catch(RuntimeException e){
            System.err.println(e);
        }
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
              try {
                  System.out.println("Write new telephone number: ");
                  number = readLine();
                  newPerson.setPhoneNumber(number);
                  register.removePerson(person);
                  register.addPerson(newPerson);
              }catch( RuntimeException e){
                  System.err.println(e.getMessage());
                  return;
              }
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
                System.err.println("Person not found.");
        }
        if (index == 2) {
            System.out.println("Write the telephone number: ");
            String number = readLine();

            Person p = register.findPersonByPhoneNumber(number);
            if(p!=null) {
                System.out.println(p.toString());
            }else
                System.err.println("Person not found.");

        }

    }
    
    private void removeFromRegister() {
        System.out.println("Enter index: ");

        int index = Integer.parseInt(readLine());
        try {
            register.Person person = register.getPerson(index - 1);
            register.removePerson(person);
        }catch(IndexOutOfBoundsException e){
            System.err.println("wrong index");
            return;
        }



    }

}

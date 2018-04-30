package register;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListRegister implements Register {
   ArrayList<Person> listRegister = new ArrayList<>();
   private int count;
    @Override
    public int getCount() {
        return count;
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

        for (Person p:listRegister){
            if(p.getName().equals(name)){
                return p;
            }
        }

       return null;
    }

    @Override
    public Person findPersonByPhoneNumber(String phoneNumber) {
        for (Person p:listRegister){
            if(p.getPhoneNumber().equals(phoneNumber)){
                return p;
            }
        }

        return null;
    }

    @Override
    public void removePerson(Person person) {
        listRegister.remove(person);
    }
    public void sort(){
        Collections.sort(listRegister);
    }
}

package register;

/**
 * register.Person register.
 */
public class ArrayRegister implements register.Register {
    /** register.Person array. */
    private register.Person[] persons;
    
    /** Number of persons in this register. */
    private int count;
    
    /**
     * Constructor creates an empty register with maximum size specified.
     * @param size maximum size of the register
     */
    public ArrayRegister(int size) {
        persons = new register.Person[size];
        count = 0;

    }
    
    /**
     * Returns the number of persons in this register.
     * @return the number of persons in this register
     */
    @Override
    public int getCount() {
        return count;
    }
    
    /**
     * Returns the maximum number of persons in this register.
     * @return the maximum number of persons in this register.
     */
    @Override
    public int getSize() {
        return persons.length;
    }
    
    /**
     * Returns the person at the specified position in this register.
     * @param index index of the person to return 
     * @return person the person at the specified position in this register 
     */
    @Override
    public register.Person getPerson(int index) {
      return persons[index];

    }

    /**
     * Appends the specified person to the end of this register. 
     * @param person person to append to this register
     */
    @Override
    public void addPerson(register.Person person) {
        for (int i=0; i<persons.length; i++) {
            Person p = persons[i];
            if (p != null) {

            if (p.getPhoneNumber().equals(person.getPhoneNumber())) {
                System.out.println("action failed, this person's telephone number is already in the list.");
                break;
            }
            if (p.getName().equals(person.getName())) {
                System.out.println("action failed, this person's name is already in the list.");
                break;
            }
        }
        }
        persons[count] = person;
        count++;
    }       
    

    /**
     * Returns the person with specified name in this register or <code>null</code>,
     * if match can not be found.
     * @param name name of a person to search for
     * @return person with specified phone number
     */
    @Override
    public register.Person findPersonByName(String name) {

        for (int i=0; i<persons.length; i++){
            Person p = persons[i];
            if(p!=null) {
                if (p.getName().equals(name)) {
                    return p;
                }
            }
        }
        return null;
    }
    

    /**
     * Returns the person with specified phone number in this register or <code>null</code>,
     * if match can not be found.
     * @param phoneNumber phone number of a person to search for
     * @return person with specified phone number
     */
    @Override
    public register.Person findPersonByPhoneNumber(String phoneNumber) {
        for (int i=0; i<persons.length; i++){
            Person p = persons[i];
            if(p!=null) {
                if (p.getPhoneNumber().equals(phoneNumber)) {
                    return p;
                }
            }
        }
        return null;
    }
    

    /**
     * Removes the specified person from the register.
     * @param person person to remove
     */
    @Override
    public void removePerson(register.Person person) {
        register.Person[] newPersons = new register.Person[persons.length];
        int i=0;
        int originalCount = count;
        if(person == null){

                System.err.println("Action failed, person not found");
            return;
        }
        for(int j=0; j<persons.length; j++){
            register.Person p = persons[j];
            if(p != null) {
                if (!(p.equals(person))) {
                    newPersons[i] = p;
                    i++;
                }
            }
        }
        persons = newPersons;
        count--;


    }
    public void sort(){
    }
    public void save(){}
    public static ArrayRegister load(){
        return null;
    }

}

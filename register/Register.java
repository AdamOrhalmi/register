package register;

public interface Register {
    int getCount();

    int getSize();

    register.Person getPerson(int index);

    void addPerson(Person person);

    Person findPersonByName(String name);

    Person findPersonByPhoneNumber(String phoneNumber);

    void removePerson(Person person);
    void sort();
    void save();
    static Register load(){
        return null;
    };
}

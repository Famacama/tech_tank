import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Person {
    private String name;
    private String gender;
    private Date birthDate;

    public Person(String name, String gender, String birthDate) throws ParseException {
        this.name = name;
        this.gender = gender;
        this.birthDate = new SimpleDateFormat("dd/MM/yy").parse(birthDate);
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }
}

class AddressBook {
    private List<Person> persons;

    public AddressBook() {
        persons = new ArrayList<>();
    }

    public void addPerson(Person person) {
        persons.add(person);
    }

    public int countMales() {
        return (int) persons.stream().filter(person -> "Male".equals(person.getGender())).count();
    }

    public Person getOldestPerson() {
        return persons.stream().min((p1, p2) -> p1.getBirthDate().compareTo(p2.getBirthDate())).orElse(null);
    }

    public long ageDifferenceInDays(String person1Name, String person2Name) {
        Person person1 = findPersonByName(person1Name);
        Person person2 = findPersonByName(person2Name);

        if (person1 != null && person2 != null) {
            long differenceInMillis = Math.abs(person1.getBirthDate().getTime() - person2.getBirthDate().getTime());
            return differenceInMillis / (24 * 60 * 60 * 1000); // Convert to days
        } else {
            System.out.println("One or both persons not found in the address book.");
            return -1;
        }
    }

    private Person findPersonByName(String name) {
        return persons.stream().filter(person -> name.equals(person.getName())).findFirst().orElse(null);
    }

    public static AddressBook readAddressBookFromFile(String filePath) throws FileNotFoundException, ParseException {
        AddressBook addressBook = new AddressBook();
        File file = new File(filePath);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(", ");
                if (parts.length == 3) {
                    String name = parts[0];
                    String gender = parts[1];
                    String birthDate = parts[2];
                    addressBook.addPerson(new Person(name, gender, birthDate));
                }
            }
        }

        return addressBook;
    }
}

public class AddressBookReader {
    public static void main(String[] args) throws ParseException, FileNotFoundException {
        AddressBook addressBook = AddressBook.readAddressBookFromFile("addressbook.txt");

        // 1. How many males are in the address book?
        int maleCount = addressBook.countMales();
        System.out.println("Number of males in the address book: " + maleCount);

        // 2. Who is the oldest person in the address book?
        Person oldestPerson = addressBook.getOldestPerson();
        System.out.println("Oldest person in the address book: " + oldestPerson.getName());

        // 3. How many days older is Bill than Paul?
        long ageDifference = addressBook.ageDifferenceInDays("Bill McKnight", "Paul Robinson");
        if (ageDifference >= 0) {
            System.out.println("Bill is " + ageDifference + " days older than Paul.");
        } else {
            System.out.println("One or both persons not found in the address book.");
        }
    }
}

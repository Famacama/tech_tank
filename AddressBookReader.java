import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    public List<Person> getOldestPersons() {
        // Find the minimum birth date
        Date minBirthDate = persons.stream().map(Person::getBirthDate).min(Date::compareTo).orElse(null);

        // Filter persons with the minimum birth date
        return persons.stream()
                .filter(person -> person.getBirthDate().equals(minBirthDate))
                .toList();
    }

    public String ageDifferenceInDays(String person1Name, String person2Name) {
        Person person1 = findPersonByName(person1Name);
        Person person2 = findPersonByName(person2Name);

        if (person1 != null && person2 != null) {
            long differenceInMillis = Math.abs(person1.getBirthDate().getTime() - person2.getBirthDate().getTime());
            long ageDifferenceInDays = differenceInMillis / (24 * 60 * 60 * 1000); // Convert to days

            // Determine the oldest persons
            List<Person> oldestPersons = getOldestPersons();

            // Build the result string
            StringBuilder result = new StringBuilder();
            result.append("Number of males in the address book: ").append(countMales()).append("\n");

            if (!oldestPersons.isEmpty()) {
                result.append("Oldest persons in the address book: ");
                result.append(oldestPersons.stream().map(Person::getName).collect(Collectors.joining(", ")));
                result.append("\n");
            }

            result.append(person1.getName()).append(" is ").append(ageDifferenceInDays).append(" days older than ").append(person2.getName()).append(".");

            return result.toString();
        } else {
            return "One or both persons not found in the address book.";
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

        // Set default person names (Bill and Paul)
        String person1Name = "Bill McKnight";
        String person2Name = "Paul Robinson";

        // Use command-line arguments if provided
        if (args.length >= 2) {
            person1Name = args[0];
            person2Name = args[1];
        }

        // Get the result string
        String result = addressBook.ageDifferenceInDays(person1Name, person2Name);

        // Print the result
        System.out.println(result);
    }
}

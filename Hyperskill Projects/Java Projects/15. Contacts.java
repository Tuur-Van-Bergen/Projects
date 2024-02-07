package contacts;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        Map<String, Command> commands = new HashMap<>();
        commands.put("add", new AddCommand());
        commands.put("list", new ListCommand());
        commands.put("search", new SearchCommand());
        commands.put("count", new CountCommand());

        Scanner scanner = new Scanner(System.in);
        boolean status = true;
        do {
            System.out.print("Enter action (add, list, search, count, exit) ");
            String action = scanner.next();
            if (action.equals("exit")) {
                status = false;
            } else {
                Command command = commands.get(action);
                command.invoke();
            }
        } while (status);
    }
}

public class RemoveCommand implements Command {
    public void invoke() {
        if (PhoneBook.size() == 0) {
            System.out.println("No records to remove!");
            return;
        }

        new ListCommand().invoke();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a record:");
        int recordIndex = scanner.nextInt() - 1;
        PhoneBook.removeContact(recordIndex);
        System.out.println("The record removed!");
    }
}

public class AddCommand implements Command {
    public void invoke() {
        Scanner sc = new Scanner(System.in);
        Contact contact = new Contact();
        System.out.print("Enter the type (person, organization): ");
        String type = sc.next();
        contact.setIsPerson(type.equals("person"));
        if (contact.getIsPerson()) {
            System.out.print("Enter the name: ");
            String name = sc.next();
            contact.setName(name);

            System.out.print("Enter the surname: ");
            String surname = sc.next();
            contact.setSurname(surname);

            sc.nextLine();
            System.out.print("Enter the birth date: ");
            String birthDay = sc.nextLine();
            contact.setBirthDate(birthDay);

            System.out.print("Enter the gender (M, F): ");
            String gender = sc.nextLine();
            contact.setGender(gender);

            System.out.print("Enter the number: ");
            String number = sc.nextLine();
            contact.setNumber(number);
        } else if (type.equals("organization")) {
            sc.nextLine();
            System.out.print("Enter the organization name: ");
            String name = sc.nextLine();
            contact.setName(name);

            System.out.print("Enter the address: ");
            String address = sc.nextLine();
            contact.setAddress(address);

            System.out.print("Enter the number: ");
            String number = sc.nextLine();
            contact.setNumber(number);
        }

        contact.setCreated();
        contact.setEdited();
        PhoneBook.addContact(contact);
        System.out.println("The record added.\n");
    }
}

public interface Command {
    void invoke();
}

public class Contact {
    private String name;
    private String surname;
    private boolean isPerson = false;
    private String number = "[no data]";
    private String address;
    private String gender = "[no data]";
    private String birthDate = "[no data]";
    private String created;
    private String edited;

    public Contact() {}

    private boolean isValidNumber(String number) {
        Pattern pattern = Pattern.compile("((^\\w*$)|(^\\+\\w*$)|(^\\+\\w\\s\\w{2})|(^\\d{3}\\W\\w{2,3}($|\\W\\w{2,3}$|\\W\\w{2,4}\\W\\w{2}$)))|((^\\(\\d{3}\\)($|\\W\\d{3}($|\\W\\d{3}($|\\W\\d{3}$))))|((^\\+(\\d\\W\\(\\d{3}\\)\\W\\d{3}\\W\\d{3}\\W\\w{2,4}$|\\(\\w*\\)$))|(^\\d{3}\\W\\(\\d{2,3}\\)($|\\W\\d{2,3}($|\\W\\d{2})))))");
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }

    private boolean isValidGender(String gender) {
        Pattern pattern = Pattern.compile("[MF]");
        Matcher matcher = pattern.matcher(gender);

        return matcher.matches();
    }

    private boolean isValidBirthday(String birthDay) {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(birthDay);

        return matcher.matches();
    }

    public boolean getIsPerson() {
        return isPerson;
    }

    public void setIsPerson(boolean isPerson) {
        this.isPerson = isPerson;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        if (isValidBirthday(birthDate)) {
            this.birthDate = birthDate;
        } else {
            this.birthDate = "[no data]";
            System.out.println("Bad birth date!");
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (isValidGender(gender)) {
            this.gender = gender;
        } else {
            this.gender = "[no data]";
            System.out.println("Bad gender!");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.created = now.format(formatter);
    }

    public String getEdited() {
        return edited;
    }

    public void setEdited() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.edited = now.format(formatter);
    }
}

public class PhoneBook {
    private static final List<Contact> contacts = new ArrayList<>();

    public static Contact getContact(int index) {
        return contacts.get(index);
    }

    public static Contact getContactByName(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }

    public static void addContact(Contact contact) {
        contacts.add(contact);
    }

    public static void removeContact(int index) {
        contacts.remove(index);
    }

    public static List<Contact> getAllContacts() {
        return contacts;
    }

    public static int size() {
        return contacts.size();
    }
}

public class ListCommand implements Command {
    public int recordIndex;
    public void invoke() {
        Scanner scanner = new Scanner(System.in);
        new ListContacts().invoke();
        System.out.println();
        System.out.print("Enter action ([number], back): ");
        recordIndex = scanner.nextInt() - 1;
        setRecordIndex(recordIndex);
        scanner.nextLine();
        Contact contact = PhoneBook.getContact(recordIndex);
        if (contact.getIsPerson()) {
            System.out.println("Name: " + contact.getName() + "\n" +
                    "Surname: " + contact.getSurname() + "\n" +
                    "Birth date: " + contact.getBirthDate() + "\n" +
                    "Gender: " + contact.getGender() + "\n" +
                    "Number: " + contact.getNumber() + "\n" +
                    "Time created: " + contact.getCreated() + "\n" +
                    "Time last edit: " + contact.getEdited() + "\n");
        } else {
            System.out.println("Organization name: " + contact.getName() + "\n" +
                    "Address: " + contact.getAddress() + "\n" +
                    "Number: " + contact.getNumber() + "\n" +
                    "Time created: " + contact.getCreated() + "\n" +
                    "Time last edit: " + contact.getEdited() + "\n");
        }
        System.out.print("Enter action (edit, delete, menu): ");
        String action = scanner.next();
        while (!action.equals("menu")) {
            if (action.equals("edit")) {
                new EditCommand().invoke();
            } else {
                new RemoveCommand().invoke();
            }
            System.out.print("Enter action (edit, delete, menu): ");
            action = scanner.next();
        }
        System.out.println();
    }

    public int getRecordIndex() {
        return recordIndex;
    }

    public void setRecordIndex(int recordIndex) {
        this.recordIndex = recordIndex;
    }
}

public class InfoCommand implements Command {
    public void invoke() {


    }
}

public class CountCommand implements Command {
    public void invoke() {
        System.out.println("The Phone Book has " + PhoneBook.size() + " records.");
    }
}

public class EditCommand implements Command {
    public void invoke() {
        if (PhoneBook.size() == 0) {
            System.out.println("No records to edit!");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        ListCommand listCommand = new ListCommand();

        int recordIndex = listCommand.getRecordIndex();

        if (PhoneBook.getContact(recordIndex).getIsPerson()) {
            System.out.print("Select a field (name, surname, birth, gender, number): ");
        } else {
            System.out.print("Select a field (address, number): ");
        }
        String action = scanner.next();

        System.out.println("Enter " + action + ":");
        scanner.nextLine();
        String value = scanner.nextLine();

        switch (action) {
            case "name" -> PhoneBook.getContact(recordIndex).setName(value);
            case "surname" -> PhoneBook.getContact(recordIndex).setSurname(value);
            case "birth" -> PhoneBook.getContact(recordIndex).setBirthDate(value);
            case "gender" -> PhoneBook.getContact(recordIndex).setGender(value);
            case "number" -> PhoneBook.getContact(recordIndex).setNumber(value);
            case "address" -> PhoneBook.getContact(recordIndex).setAddress(value);
        }
        PhoneBook.getContact(recordIndex).setEdited();
        System.out.println("The record updated!\n");
    }
}

public class SearchCommand  implements Command {
    public void invoke() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter search query: ");
        String query = scanner.nextLine();
        List<String> queryResults = new ArrayList<>();
        List<String> fullNames = new ArrayList<>();
        String fullName;
        for (int i = 0; i < PhoneBook.size(); i++) {
            String name = PhoneBook.getContact(i).getName();
            String surname = PhoneBook.getContact(i).getSurname();
            String number = PhoneBook.getContact(i).getNumber();
            if (surname != null) {
                fullName = name + " " + surname;
            } else {
                fullName = name;
            }
            if (fullName.toLowerCase().contains(query.toLowerCase()) || number.contains(query)) {
                queryResults.add(name);
                fullNames.add(fullName);
            }
        }
        if (queryResults.size() == 1) {
            System.out.println("Found " + queryResults.size() + " result:");
        } else {
            System.out.println("Found " + queryResults.size() + " results:");
        }
        for (int i = 0; i < queryResults.size(); i++) {
            System.out.println(i + 1 + ". " + fullNames.get(i));
        }
        System.out.println();
        System.out.println("Enter action ([number], back, again): ");
        String action = scanner.next();
        if (action.equals("again")){
            new SearchCommand().invoke();
        } else if (action.matches("^\\d+$")) {
            Contact contact = PhoneBook.getContactByName(queryResults.get(Integer.parseInt(action)));
            if (contact.getIsPerson()) {
                System.out.println("Name: " + contact.getName() + "\n" +
                        "Surname: " + contact.getSurname() + "\n" +
                        "Birth date: " + contact.getBirthDate() + "\n" +
                        "Gender: " + contact.getGender() + "\n" +
                        "Number: " + contact.getNumber() + "\n" +
                        "Time created: " + contact.getCreated() + "\n" +
                        "Time last edit: " + contact.getEdited() + "\n");
            } else {
                System.out.println("Organization name: " + contact.getName() + "\n" +
                        "Address: " + contact.getAddress() + "\n" +
                        "Number: " + contact.getNumber() + "\n" +
                        "Time created: " + contact.getCreated() + "\n" +
                        "Time last edit: " + contact.getEdited() + "\n");
            }
            System.out.print("Enter action (edit, delete, menu): ");
            String action2 = scanner.next();
            while (!action2.equals("menu")) {
                if (action2.equals("edit")) {
                    new EditCommand().invoke();
                } else {
                    new RemoveCommand().invoke();
                }
                System.out.print("Enter action (edit, delete, menu): ");
                action2 = scanner.next();
            }
            System.out.println();
        }

    }
}

public class ListContacts implements Command {
    public void invoke() {
        for (int i = 0; i < PhoneBook.size(); i++) {
            String name = PhoneBook.getContact(i).getName();
            String surname =  PhoneBook.getContact(i).getSurname();
            if (surname != null) {
                System.out.println(i + 1 + ". " + name + " " + surname);
            } else {
                System.out.println(i + 1 + ". " + name);
            }
        }
    }
}

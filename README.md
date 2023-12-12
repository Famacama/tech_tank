#Tech Tank

# AddressBookReader Java Program

The AddressBookReader Java program is designed to read an address book from an external file, answer specific questions about the data, and provide the results. The address book contains information about individuals, including their name, gender, and birthdate.

## Usage

### 1. Build and Run

Compile the Java program:
  javac AddressBookReader.java
Run the compiled program:
  java AddressBookReader

### 2. Input

2.1 The address book data is stored in an external file named addressbook.txt. Make sure to format the file according to the provided examples:
'''
Bill McKnight, Male, 16/03/77
Paul Robinson, Male, 15/01/85
Gemma Lane, Female, 20/11/91
Sarah Stone, Female, 20/09/80
Wes Jackson, Male, 14/08/74
'''

2.2 Compare Specific "Persons" ages

You can specify which "persons" ages to compare by providing their names as arguments when running the program.
For example:
```java AddressBookReader "Gemma Lane" "Sarah Stone"```

If no persons are specified, the default comparison will be between "Bill McKnight" and "Paul Robinson."

### 3. Output
The program answers the following questions:

How many males are in the address book?
Who is the oldest person in the address book?
How many days older is person 1 than person 2?
The results will be displayed in the console.

Example of output:
Number of males in the address book: 3
Oldest person in the address book: Bill McKnight
Bill McKnight is 2862 days older than Paul Robinson.

### 4. File Structure
AddressBookReader.java: Main Java program file.
addressbook.txt: External file containing the address book data.

Author
Fama Camara

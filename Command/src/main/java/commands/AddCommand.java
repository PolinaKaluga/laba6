package commands;

import collection.*;
import exceptions.CannotExecuteCommandException;
import workWithFile.JsonParser;
import workWithFile.TicketFieldReader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public class AddCommand extends Command {

    private TicketFieldReader ticketFieldReader;

    public AddCommand(CollectionManager collectionManager, TicketFieldReader ticketFieldReader) {
        this.collectionManager = collectionManager;
        this.ticketFieldReader = ticketFieldReader;
    }




//    @Override
//    public void execute(String[] arguments, InvocationStatus invocationStatus, PrintStream printStream) throws CannotExecuteCommandException {
//        if (invocationStatus.equals(InvocationStatus.CLIENT)) {
//            if (arguments.length > 1) {
//                throw new CannotExecuteCommandException("Количество аргументов у данной команды должно быть не более 1.");
//            }
//            if (arguments.length == 1) {
//                if (TicketFieldValidation.validate("id", arguments[0])) {
//                    printStream.println("Введите значения полей для элемента коллекции:\n");
//                    Ticket ticket = ticketFieldReader.read(Integer.parseInt(arguments[0]));
//                    super.result.add(Integer.parseInt(arguments[0])); //Integer id - result(0), ticket - result(1)
//                    super.result.add(ticket);
//                } else
//                    throw new CannotExecuteCommandException("Введены невалидные аргументы: id = " + arguments[0]);
//            } else {
//                printStream.println("Введите значения полей для элемента коллекции:\n");
//                Ticket ticket = ticketFieldReader.read();
//                collectionManager.add(ticket);
//            }
//        } else if (invocationStatus.equals(InvocationStatus.SERVER)) {
//            if (result.size() == 2) {
//                Ticket ticket = (Ticket) this.getResult().get(1);
//                collectionManager.addWithId((Integer) this.getResult().get(0), ticket, printStream);
//            } else {
//                Ticket ticket = (Ticket) this.getResult().get(0);
//                collectionManager.add(ticket);
//            }
//        }
//    }


    @Override
    public void execute(String[] arguments, InvocationStatus invocationStatus, PrintStream printStream) throws CannotExecuteCommandException {
        if (invocationStatus.equals(InvocationStatus.CLIENT)) {
            if (arguments.length > 1) {
                throw new CannotExecuteCommandException("Количество аргументов у данной команды должно быть не более 1.");
            }
            if (arguments.length == 1) {
                if (TicketFieldValidation.validate("id", arguments[0])) {
                    printStream.println("Введите значения полей для элемента коллекции:\n");
                    Ticket ticket = ticketFieldReader.read(Integer.parseInt(arguments[0]));
                    super.result.add(Integer.parseInt(arguments[0])); //Integer id - result(0), ticket - result(1)
                    super.result.add(ticket);
                } else
                    throw new CannotExecuteCommandException("Введены невалидные аргументы: id = " + arguments[0]);
            } else {
                printStream.println("Введите значения полей для элемента коллекции:\n");
                Ticket ticket = ticketFieldReader.read();
                collectionManager.add(ticket);
            }
        } else if (invocationStatus.equals(InvocationStatus.SERVER)) {
            if (result.size() == 2) {
                Ticket ticket = (Ticket) this.getResult().get(1);
                collectionManager.addWithId((Integer) this.getResult().get(0), ticket, printStream);
            } else {
                Ticket ticket = (Ticket) this.getResult().get(0);
                collectionManager.add(ticket);
            }
        }
    }

    private CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }


    @Override
    public String getDescription() {
        return "Добавляет новый элемент в коллекцию";
    }

    // Parsing methods
    private Coordinates parseCoordinates(String arg) {
        String[] parts = arg.split(",");

        Float x = null;
        if (parts[0] != null && !parts[0].isEmpty()) {
            x = Float.parseFloat(parts[0]);
        }

        float y = Float.parseFloat(parts[1]);

        // Creating a new Coordinates object with the parsed values
        return new Coordinates(x, y);
    }

    private ZonedDateTime parseZonedDateTime(String arg) {
        // Implement parsing logic for ZonedDateTime based on your format
        // For example, you can use DateTimeFormatter to parse the string
        return ZonedDateTime.parse(arg);
    }

    private TicketType parseTicketType(String arg) {
        return TicketType.valueOf(arg.toUpperCase());
    }

    private Person parsePerson(String arg) {
        String[] parts = arg.split(",");

        // Assuming that eyeColor and hairColor are enums with values: BLUE, BROWN, GREEN, RED, BLACK
        EyeColor eyeColor = EyeColor.valueOf(parts[0].toUpperCase());
        HairColor hairColor = HairColor.valueOf(parts[1].toUpperCase());

        Long height = null;
        if (parts[2] != null && !parts[2].isEmpty()) {
            height = Long.parseLong(parts[2]);
        }

        Float weight = null;
        if (parts[3] != null && !parts[3].isEmpty()) {
            weight = Float.parseFloat(parts[3]);
        }

        // Creating a new Person object with the parsed values
        return new Person(height, weight, eyeColor, hairColor);
    }
}
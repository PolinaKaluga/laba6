package collection;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicLong;
import java.io.PrintStream;

public class Ticket implements Comparable<Ticket> {

    private static final AtomicLong idGenerator = new AtomicLong(1);
    private long id;
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int price;
    private TicketType type;
    private Person person;
    private int ownerId; // New attribute for ownerId

    public Ticket(String name, Coordinates coordinates, ZonedDateTime zonedDateTime, int price, TicketType type, Person person) {
        this.id = idGenerator.getAndIncrement();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.price = price;
        this.type = type;
        this.person = person;

    }

    public Ticket(int id, String name, LocalDate creationDate, int price, TicketType type, Person person) {
    }

    public Ticket() {

    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {

        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {

        this.price = price;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {

        this.type = type;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person){
        this.person = person;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", type=" + type +
                ", person=" + person +
                '}';
    }

    @Override
    public int compareTo(Ticket otherTicket) {
        // Implement your comparison logic here
        // For example, compare based on a specific field like ID or name
        // Return a negative value if this instance is less than otherTicket,
        // 0 if they are equal, and a positive value if this instance is greater
        return 0;
    }
}
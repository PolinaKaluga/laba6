package workWithFile;

import collection.*;
import exceptions.ValidValuesRangeException;
import io.UserIO;

import java.time.Instant;
import java.time.ZonedDateTime;

public class TicketFieldReader {
    private UserIO userIO;

    public TicketFieldReader(UserIO userIO) {
        this.userIO = userIO;
    }

    public TicketFieldReader() {

    }

    public Ticket read(Integer id) {
        String i = Instant.now().toString();
        return new Ticket(readName(), readCoordinates(), ZonedDateTime.parse(i).plusHours(3), readPrice(), readType(), readPerson());
    }

    public Ticket read() {
        String i = Instant.now().toString();
        return new Ticket(readName(), readCoordinates(), ZonedDateTime.parse(i).plusHours(3), readPrice(), readType(), readPerson());
    }

    public String readName() {
        String str;
        while (true) {
            userIO.printCommandText("name (not null): ");
            str = userIO.readLine().trim();
            if (str.equals("") || str == null)
                userIO.printCommandError("\nЗначение поля не может быть null или пустой строкой\n");
            else
                return str;
        }
    }

    public Coordinates readCoordinates() {
        Float x = Float.valueOf(readCoordinateX());
        float y = readCoordinateY();
        return new Coordinates(x, y);
    }

    public Float readCoordinateX() {
        Float x;
        while (true) {
            try {
                userIO.printCommandText("coordinate_x (Long & x > -985): ");
                String str = userIO.readLine().trim();
                if (str.equals("") || str == null)
                    x = Float.valueOf(0);
                else {
                    x = Float.parseFloat(str);
                    if (x <= -985)
                        throw new ValidValuesRangeException();
                    else
                        return x;
                }
            } catch (ValidValuesRangeException ex) {
                System.out.println("Координата x должна быть больше -985");
            } catch (NumberFormatException ex) {
                System.err.println("Число должно быть типа Long");
            }
        }
    }

    public float readCoordinateY() {
        double y;
        while (true) {
            try {
                userIO.printCommandText("coordinate_y (Double): ");
                String str = userIO.readLine().trim();
                if (str.equals("") || str == null)
                    y = 0;
                else {
                    y = Double.parseDouble(str);
                }
                return (float) y;
            } catch (NumberFormatException ex) {
                System.err.println("Число должно быть типа Double");
            }
        }
    }

    public int readPrice() {
        int price;
        while (true) {
            try {
                userIO.printCommandText("price (Integer & x > 0): ");
                String str = userIO.readLine().trim();
                if (str.equals("") || str == null)
                    price = 0;
                else {
                    price = Integer.parseInt(str);
                    if (price <= 0)
                        throw new ValidValuesRangeException();
                    else
                        return price;
                }
            } catch (ValidValuesRangeException ex) {
                System.out.println("Price должен быть больше 0");
            } catch (NumberFormatException ex) {
                System.err.println("Число должно быть типа Integer");
            }
        }
    }

    public TicketType readType() {
        TicketType type;
        while (true) {
            try {
                userIO.printCommandText("Допустимые значения TicketType :\n");
                for (TicketType val : TicketType.values()) {
                    userIO.printCommandText(val.name() + "\n");
                }
                userIO.printCommandText("TicketType: ");
                type = TicketType.valueOf(userIO.readLine().toUpperCase().trim());
                return type;
            } catch (IllegalArgumentException ex) {
                System.err.println("Значение введенной константы не представлено в перечислении TicketType");
            }
        }
    }



    public Long readPersonHeight() {
        long height;
        while (true) {
            try {
                userIO.printCommandText("height (Long && >0): ");
                String str = userIO.readLine().trim();
                if (str.equals("") || str == null)
                    height = 0;
                else {
                    height = Long.parseLong(str);
                    if (height <= 0)
                        throw new ValidValuesRangeException();
                }
                return height;
            } catch (ValidValuesRangeException ex) {
                System.out.println("height должен быть больше 0");
            } catch (NumberFormatException ex) {
                System.err.println("Вводимое значение должно быть Long");
            }
        }
    }


    public Float readPersonWeight() {
        float weight;
        while (true) {
            try {
                userIO.printCommandText("weight (Float && >0): ");
                String str = userIO.readLine().trim();
                if (str.equals(""))
                    weight = 0;
                else {
                    weight = Float.parseFloat(str);
                    if (weight <= 0)
                        throw new ValidValuesRangeException();
                }
                return weight;
            } catch (ValidValuesRangeException ex) {
                System.out.println("weight должен быть больше 0");
            } catch (NumberFormatException ex) {
                System.err.println("Вводимое значение должно быть Float");
            }
        }
    }

    public EyeColor readPersonEyeColor() {
        EyeColor eyeColor;
        while (true) {
            try {
                userIO.printCommandText("Допустимые значения EyeColor :\n");
                for (EyeColor val : EyeColor.values()) {
                    userIO.printCommandText(val.name() + "\n");
                }
                userIO.printCommandText("EyeColor: ");
                eyeColor = EyeColor.valueOf(userIO.readLine().toUpperCase().trim());
                return eyeColor;
            } catch (IllegalArgumentException ex) {
                System.err.println("Значение введенной константы не представлено в перечислении EyeColor");
            }
        }
    }

    public HairColor readPersonHairColor() {
        HairColor hairColor;
        while (true) {
            try {
                userIO.printCommandText("Допустимые значения HairColor :\n");
                for (HairColor val : HairColor.values()) {
                    userIO.printCommandText(val.name() + "\n");
                }
                userIO.printCommandText("HairColor: ");
                hairColor = HairColor.valueOf(userIO.readLine().toUpperCase().trim());
                return hairColor;
            } catch (IllegalArgumentException ex) {
                System.err.println("Значение введенной константы не представлено в перечислении HairColor");
            }
        }
    }
    public Person readPerson() {
        return new Person(readPersonHeight(), readPersonWeight(), readPersonEyeColor(), readPersonHairColor());
    }



}

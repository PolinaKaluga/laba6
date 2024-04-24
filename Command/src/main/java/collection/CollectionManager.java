package collection;


import workWithFile.FileManager;
import workWithFile.JsonParser;
import workWithFile.TicketFieldReader;

import java.io.PrintStream;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class CollectionManager {
    /**
     * Коллекция, над которой будет осуществляться работа
     */
    static ArrayDeque<Ticket> arrayDeque;
    /**
     * Время инициализации коллекции
     */
    ZonedDateTime collectionInitialization;

    /**
     * Конструктор - создание нового объекта менеджера коллекции
     */
    public CollectionManager() {
        this.arrayDeque = new ArrayDeque<>();
        String i = Instant.now().toString();
        collectionInitialization = ZonedDateTime.parse(i);
    }


    public void add(Ticket ticket) {
        int id = getNewRandomId();
        ticket.setId(id);
        arrayDeque.add(ticket);
    }

    public void addWithId(Integer id, Ticket ticket, PrintStream printStream) {
        boolean idExists = arrayDeque.stream().anyMatch(t -> t.getId() == id);
        if (!idExists) {
            ticket.setId(id);
            arrayDeque.add(ticket);
        } else {
            printStream.println("Элемент с данным ключом уже существует");
        }
    }




    /**
     * Метод, выводящий основную информацию по используемой коллекции
     *
     * @return строка, которая хранит все содержимое данного файла
     */
    public String info() {
        StringBuilder sb = new StringBuilder();
        sb.append("Коллекция ArrayDeque\n");
        sb.append("Тип элементов коллекции: ").append(Ticket.class.getSimpleName()).append("\n");
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(pattern);
        sb.append("Время ининициализации коллекции: ").append(collectionInitialization.plusHours(3).format(europeanDateFormatter)).append("\n");
        sb.append("Количество элементов в коллекции: ").append(arrayDeque.size()).append("\n");
        return sb.toString();
    }


    /**
     * Метод, выводящий информацию по элементам коллекции
     *
     * @return строка, которая хранит все содержимое данного файла
     */
    public String show() {
        StringBuilder sb = new StringBuilder();
        if (arrayDeque.size() == 0) {
            sb.append("Коллекция пуста.");
        } else {
            for (Ticket ticket : arrayDeque) {
                sb.append(ticket.toString()).append("\n");
            }
        }
        return sb.toString();
    }



    /**
     * Метод, возвращающий рандомное значение id (в пределах 10.000)
     *
     * @return рандомный id
     */
    public static int getNewRandomId() {
        int max = 10000;
        int i = (int) (Math.random() * max);
        while (arrayDeque.contains(i)) {
            i = (int) (Math.random() * max);
        }
        return i;
    }

//
//
//    /**
//     * Метод, изменяющий значение элемента коллекции по идентификатору
//     *
//     * @param id       уникальный идентификатор элемента коллекции (ключ)
//     * @param ticket   новый элемент коллекции
//     * @throws NullPointerException исключение, выбрасываемое когда требует инициализации, но не инициализировано
//     * @throws ClassCastException   исключение, выбрасываемое при попытке преобразовать один тип в другой, который не может быть получен из исходного
//     * @throws IllegalArgumentException исключение, выбрасываемое при попытке передать методу недопустимые атрибуты
//     */
//    public void update(Integer id, Ticket ticket) {
//        if (arrayDeque.contains(id)) {
//            arrayDeque.add(id, ticket);
//        } else {
//            throw new IllegalArgumentException("Элемента с таким id не существует");
//        }
//    }


    public boolean containsKey(Integer id) {
        return arrayDeque.contains(id);
    }

    /**
     * Метод, удаляющий элемент из коллекции по его id
     *
     * @param id уникальный идентификатор элемента коллекции (ключ)
     */
    public void removeById(Integer id) {
        if (arrayDeque.contains(id)) {
            arrayDeque.remove(id);
        } else {
            throw new IllegalArgumentException("Элемента с таким id не существует");
        }
    }


    /**
     * Метод, очищающий коллекцию
     */
    public void clear() {
        arrayDeque.clear();
    }

    /**
     * Метод, сохраняющий коллекцию в файл
     *
     * @param filePath путь до файла, куда следует сохранить элементы коллекции
     */
    public void save(String filePath) {
        JsonParser jsonParser = new JsonParser();
        FileManager fileManager = new FileManager();

        Ticket[] tickets = new Ticket[arrayDeque.size()];
        int index = 0;
        for (Ticket ticket : arrayDeque) {
            tickets[index] = ticket;
            index++;
        }

        String str = jsonParser.parseToJson(tickets);
        fileManager.writeToFile(str, filePath);
    }


    /**
     * Метод, выполняющий скрипт
     *
     * @param filePath путь до файла, из которого следует выполнить скрипт
     */
    public void executeScript(String filePath) {
        // implementation for executing script
    }

    /**
     * Метод, удаляющий первый элемент из коллекции
     */
    public void removeFirst() {
        if (arrayDeque.size() > 0) {
            arrayDeque.removeFirst();
        } else {
            throw new RuntimeException("Коллекция пуста");
        }
    }


//    /**
//     * Метод, добавляющий элемент в коллекцию, если он минимальный по заданному критерию
//     * @param ticket элемент коллекции, который требуется добавить
//     */
//    public void addIfMin(Ticket ticket) {
//        if (arrayDeque.isEmpty() || ticket.compareTo(Collections.min(arrayDeque.values())) < 0) {
//            int id = getNewRandomId();
//            ticket.setId(id);
//            arrayDeque.put(id, ticket);
//        }
//    }



    /**
     * Метод, выводящий элементы, значение поля name которых начинается с заданной подстроки
     *
     * @param substring подстрока, наличие которой в значении поля name проверяется у каждого элемента коллекции
     * @return строка, которая содержит все элементы коллекции, у которых значение поля name начинается с заданной подстроки
     */
    public String filterStartsWithName(String substring) {
        List<Ticket> filteredList = new ArrayList<>();
        for (Ticket ticket : arrayDeque) {
            if (Objects.nonNull(ticket.getName()) && ticket.getName().startsWith(substring)) {
                filteredList.add(ticket);
            }
        }
        Collections.sort(filteredList);
        StringBuilder sb = new StringBuilder();
        for (Ticket ticket : filteredList) {
            sb.append(ticket.toString()).append("\n");
        }
        return sb.toString();
    }

//    public void insertWithId(Integer id, Ticket ticket, PrintStream printStream) {
//        boolean idExists = arrayDeque.stream().anyMatch(t -> t.getId() == id);
//        if (idExists) {
//            printStream.println("Элемент с таким ID уже существует в коллекции.");
//        } else {
//            ticket.setId(id);
//            arrayDeque.add(ticket);
//            printStream.println("Элемент успешно добавлен в коллекцию с ID: " + id);
//        }
//    }

//
//    /**
//     * Метод, выводящий значения поля person всех элементов в порядке убывания
//     *
//     * @return строка, которая содержит значения поля person всех элементов в порядке убывания
//     */
//    public String printFieldDescendingPerson() {
//        List<Ticket> sortedList = new ArrayList<>(arrayDeque);
//        Collections.sort(sortedList, (o1, o2) -> o2.getPerson().compareTo(o1.getPerson()));
//        StringBuilder sb = new StringBuilder();
//        for (Ticket ticket : sortedList) {
//            sb.append(ticket.getPerson()).append("\n");
//        }
//        return sb.toString();
//    }

    /**
     * Метод, выводящий значения поля price всех элементов в порядке убывания
     *
     * @return строка, которая содержит значения поля price всех элементов в порядке убывания
     */
    public String printFieldDescendingPrice() {
        List<Ticket> sortedList = new ArrayList<>(arrayDeque);
        Collections.sort(sortedList, (o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice()));
        StringBuilder sb = new StringBuilder();
        for (Ticket ticket : sortedList) {
            sb.append(ticket.getPrice()).append("\n");
        }
        return sb.toString();
    }



}
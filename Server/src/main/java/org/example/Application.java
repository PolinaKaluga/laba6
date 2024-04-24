package org.example;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import collection.CollectionManager;
import collection.Ticket;
import commands.CommandContainer;
import commands.CommandInvoker;
import io.UserIO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import workWithFile.FileManager;
import workWithFile.JsonParser;

public class Application {
    private CollectionManager collectionManager;
    private FileManager fileManager;
    private JsonParser jsonParser;
    private UserIO userIO;
    private CommandInvoker commandInvoker;
    private ServerConnection serverConnection;
    private boolean isConnected;
    private static final Logger rootLogger = LogManager.getRootLogger();

    public Application() {
        collectionManager = new CollectionManager();
        fileManager = new FileManager();
        jsonParser = new JsonParser();
        userIO = new UserIO();
        rootLogger.info("Конструктор класса Application был загружен.");
    }

    public void start(String inputFile) throws IOException {
        try {
            File ioFile = new File(inputFile);
            if (!ioFile.canRead() || ioFile.isDirectory() || !ioFile.isFile()) throw new IOException();
            String file = fileManager.readFromFile(inputFile);


            List<Ticket> tickets = jsonParser.parseToCollection(file);

            for (Ticket ticket : tickets) {
                collectionManager.add(ticket);
            }

            this.commandInvoker = new CommandInvoker(collectionManager, inputFile);

            rootLogger.printf(Level.INFO, "Элементы коллекций из файла %1$s были загружены.", inputFile);

            serverConnection = new ServerConnection();

            Scanner scanner = new Scanner(System.in);

            do {
                System.out.print("Введите порт: ");
                int port = scanner.nextInt();
                if (port <= 0){
                    rootLogger.error("Введенный порт невалиден.");
                }
                else{
                    isConnected = serverConnection.createFromPort(port);
                }
            } while (!isConnected);
            rootLogger.info("Порт установлен.");
        }catch(NoSuchElementException ex){
            rootLogger.error("Аварийное завершение работы");
            System.exit(-1);
        }
        try {
            cycle(commandInvoker);
        } catch (NoSuchElementException | InterruptedException ex) {
            rootLogger.warn(ex.getMessage());
            rootLogger.warn("Работа сервера завершена.");
        }
    }

    private void cycle(CommandInvoker commandInvoker) throws InterruptedException {
        RequestReader requestReader = new RequestReader(serverConnection.getServerSocket());
        ResponseSender responseSender = new ResponseSender(serverConnection.getServerSocket());
        CommandProcessor commandProcessor = new CommandProcessor(commandInvoker);

        while (isConnected) {
            try {
                requestReader.readCommand();
                CommandContainer command = requestReader.getCommandContainer();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream printStream = new PrintStream(baos);

                commandProcessor.executeCommand(command, printStream);

                Thread.sleep(1000);
                responseSender.send(baos.toString(), requestReader.getSenderAddress(), requestReader.getSenderPort());
                rootLogger.info("Пакет был отправлен " + requestReader.getSenderAddress().getHostAddress() + " " + requestReader.getSenderPort());

            } catch (IOException ex) {
                rootLogger.warn("Произошла ошибка при чтении: " + ex.getMessage());
            } catch (ClassNotFoundException ex) {
                rootLogger.error("Неизвестная ошибка: " + ex);
            }
        }
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
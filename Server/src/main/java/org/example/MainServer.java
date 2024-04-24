package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class MainServer {
    private static final Logger rootLogger = LogManager.getRootLogger();

    public static void main(String[] args) {
        Application application = new Application();

        if (args.length > 0) {
            if (!args[0].equals("")) {
                System.out.println("Старт с аргументом");
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    rootLogger.info("Сохранение коллекции в файле.");
                    application.getCollectionManager().save(args[0]);
                    rootLogger.info("Коллекция была сохранена " + args[0]);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        rootLogger.error("Ошибка с потоками: " + ex);
                    }
                    rootLogger.info("Завершение работы сервера.");
                }));
                try {
                    application.start(args[0]);
                } catch (IOException ex) {
                    rootLogger.warn("По указанному адресу нет подходящего файла " + args[0]);
                }
            }
        } else {
            System.out.println("Старт без аргумента");
            String file = "/Users/polinakalugina/IdeaProjects/laba6/inputFiles/testInputFile";
            try {
                application.start(file);
            } catch (IOException ex) {
                rootLogger.warn("По указанному адресу нет подходящего файла " + file);
            }
        }
    }
}
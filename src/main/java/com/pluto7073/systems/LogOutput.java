package com.pluto7073.systems;

import io.pluto.pixelpong.Main;

import javax.swing.*;
import java.beans.BeanProperty;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogOutput {

    private String time;
    private final String path;
    public static final String INFO = "INFO";
    public static final String WARN = "WARN";
    public static final String ERROR = "ERROR";
    private final Main main;

    public LogOutput(String path, Main main) {
        LocalDateTime x = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM.dd.yyyy");
        time = x.format(format);
        System.out.println(time);
        String logDir = path + "\\" + time;
        this.path = logDir;
        File file = new File(logDir);
        file.mkdirs();
        format = DateTimeFormatter.ofPattern("HH.mm.ss");
        time = x.format(format);
        this.main = main;
    }

    public void print(String x) {
        String time;
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
        time = localDateTime.format(format);
        System.out.println("[" + time + "] [main/INFO]: " + x);
        String filePath = path + "\\" + this.time.replace(":", ".");
        File file = new File(filePath + ".log");
        if (!file.exists()) {
            try {
                if(file.createNewFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.append("[").append(time).append("] [main/INFO]: ").append(x).append("\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeanProperty(description = "", enumerationValues = {
            LogOutput.INFO,
            LogOutput.WARN,
            LogOutput.ERROR
    })

    public void print(Object x, String type) {
        if ((!type.equals(INFO))
        && (!type.equals(WARN))
        && (!type.equals(ERROR))) {
            throw new IllegalArgumentException("must be one of: LogOutput.INFO, LogOutput.WARN, LogOutput.ERROR");
        }
        String time;
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
        time = localDateTime.format(format);
        System.out.println("[" + time + "] [main/" + type + "]: " + x);
        String filePath = path + "\\" + this.time.replace(":", ".");
        File file = new File(filePath + ".log");
        if (!file.exists()) {
            try {
                if(file.createNewFile());
            } catch (IOException e) {
                printThrowable(e);
            }
        }
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.append("[").append(time).append("] [main/").append(type).append("]: ").append(x.toString()).append("\n");
            writer.close();
        } catch (IOException e) {
            printThrowable(e);
        }
    }

    private void printError(String error) {
        System.err.println(error);
        String filePatn = path + "\\" + this.time.replace(":", ".");
        File file = new File(filePatn + ".log");
        if (!file.exists()) {
            try {
                if (file.createNewFile());
            } catch (IOException ioException) {
                printThrowable(ioException);
            }
        }
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.append(error).append("\n");
            writer.close();
        } catch (IOException e) {
            printThrowable(e);
        }
    }

    private void printFirstExceptionLine(Exception e) {
        String time;
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
        time = dateTime.format(format);
        System.err.println("[" + time + "] [main/ERROR]: " + e.toString());
        String filePatn = path + "\\" + this.time.replace(":", ".");
        File file = new File(filePatn + ".log");
        if (!file.exists()) {
            try {
                if (file.createNewFile());
            } catch (IOException ioException) {
                printThrowable(ioException);
            }
        }
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.append("[").append(time).append("] [main/ERROR]: ").append(e.toString());
            writer.close();
        } catch (IOException ioException) {
            printThrowable(ioException);
        }
    }

    public void printThrowable(Exception e) {
        printFirstExceptionLine(e);
        StackTraceElement[] stackTrace = e.getStackTrace();
        StringBuilder builder = new StringBuilder();
        builder.append(e.toString()).append("\n");
        for (StackTraceElement traceElement : stackTrace) {
            printError("\tat " + traceElement);
            builder.append("\tat ").append(traceElement).append("\n");
        }
        builder.append("Error Code: ").append(e.hashCode());
        main.currentFrame.setVisible(false);
        JOptionPane.showMessageDialog(null,
                builder.toString(),
                "An Exception Has Occured",
                JOptionPane.ERROR_MESSAGE);
        System.exit(e.hashCode());
    }

}

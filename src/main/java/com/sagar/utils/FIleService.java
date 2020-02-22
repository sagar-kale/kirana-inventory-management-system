package com.sagar.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class FIleService {
    private static AtomicBoolean motor = new AtomicBoolean(false);

    public static void readAndModify(String file) {
        Path path = Paths.get(file);
        try {
            List<String> strings = Files.readAllLines(path);
            String medium = strings.get(0);
            String action = strings.get(1);
            if (medium.toLowerCase().equals("motor")) {
                new FIleService().motorAction(action);
            } else {
                new FIleService().miscAction(action, medium);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                boolean b = Files.deleteIfExists(Paths.get(file));
                System.out.println("file deleted ... in finally " + b + "  name :: " + file);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void motorAction(String action) {
        switch (action.toLowerCase()) {
            case "on":
                if (motor.compareAndSet(false, true)) {
                    System.out.println("motor on success" + motor.get());
                } else
                    System.out.println("motor already running...");
                break;

            case "off":
                if (motor.compareAndSet(true, false))
                    System.out.println("motor off sucess");
                else
                    System.out.println("motor already off .." + motor.get());
                break;
        }

    }

    private void miscAction(String action, String medium) {

        System.out.println("action ::: " + action + " medium:::" + medium);
        if (medium.toLowerCase().equals("computer")) {
            switch (action.toLowerCase()) {
                case "lock":
                    final String path = System.getenv("windir") + File.separator + "System32" + File.separator + "rundll32.exe";
                    buildProcess(path, null);
                    break;
                case "shutdown":
                    String cmd = "shutdown -s -t 02 -c \"shutdown initiated by google assistant\"";
                    buildProcess(null, cmd);
                    System.exit(0);
                    break;

            }
        }
    }

    private static void buildProcess(String path, String cmd) {
        Runtime runtime = Runtime.getRuntime();
        Process pr = null;

        int i = 1;
        try {
            if (path != null)
                pr = runtime.exec(path + " user32.dll,LockWorkStation");
            else if (cmd != null)
                pr = runtime.exec(cmd);
            i = pr.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        if (i == 0) {
            System.out.println("Action performed success...");
            pr.destroy();
        }
    }
}

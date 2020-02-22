package com.sagar.utils;

import java.io.IOException;
import java.nio.file.*;

public class FIleWatcherUtils {
    public static void main(String[] args) throws InterruptedException {
      /*  File dirToWatch = new File("C:\\Users\\sagar\\Dropbox\\automation");
        FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(dirToWatch);
        fileAlterationObserver.addListener(new FileWatcherService());
        FileAlterationMonitor monitor = new FileAlterationMonitor();
        monitor.addObserver(fileAlterationObserver);
        System.out.println("Observation started on :" + dirToWatch.getAbsolutePath());
        try {
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        Path path = Paths.get("C:\\Users\\sagar\\Dropbox\\automation");
        WatchService watchService = null;
        try {
            watchService = path.getFileSystem().newWatchService();
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        WatchKey watchKey;
        System.out.println("watcher started...");
        while (null != (watchKey = watchService.take())) {
            watchKey.pollEvents().stream().forEach(watchEvent -> {
                System.out.println(watchEvent.kind());
                System.out.println(watchEvent.context());
                FIleService.readAndModify(path.toAbsolutePath().toString().concat("\\").concat(watchEvent.context().toString()));
            });
            watchKey.reset();
        }
    }
}

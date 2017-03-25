package ESC.Week9;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by pengpeng on 25/03/17.
 */

public class GDesktop {
    private final static int N_CONSUMERS = 4;

    //it starts here
    public static void startIndexing(File[] roots) {
        Queue<File> queue = new LinkedList<File>();
        FileFilter filter = new FileFilter() {
            public boolean accept(File file) {
                return true;
            }
        };

        for (File root : roots) {
            (new FileCrawler(queue, filter, root)).start();
        }

        for (int i = 0; i < N_CONSUMERS; i++) {
            (new Indexer(queue)).start();
        }
    }
}

class FileCrawler extends Thread {
    private final Queue<File> fileQueue;
    private final FileFilter fileFilter;
    private final File root;

    FileCrawler(Queue<File> queue, FileFilter filter, File root) {
        this.fileQueue = queue;
        this.fileFilter = filter;
        this.root = root;
    }

    public void run() {
        try {
            crawl(root);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void crawl(File root) throws InterruptedException {
        File[] entries = root.listFiles(fileFilter);

        if (entries != null) {
            for (File entry : entries) {
                if (entry.isDirectory()) {
                    crawl(entry);
                } else {
                    fileQueue.offer(entry);
                }
            }
        }
    }
}

class Indexer extends Thread {
    private final Queue<File> queue;

    public Indexer(Queue<File> queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            indexFile(queue.poll());
        }
    }

    private void indexFile(File file) {
        // code for analyzing the context of the file is skipped for simplicity
    }
}

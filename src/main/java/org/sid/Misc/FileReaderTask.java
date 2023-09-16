package org.sid.Misc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class FileReaderTask implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current absolute path is: " + s);
        List<String> fileNames = Arrays.asList("file1.txt", "file2.txt", "file3.txt");
        CountDownLatch latch = new CountDownLatch(fileNames.size());
        List<FileReaderTask> tasks = new ArrayList<>();

        for (String fileName : fileNames) {
            FileReaderTask task = new FileReaderTask(fileName, latch);
            Thread thread = new Thread(task);
            thread.start();
            tasks.add(task);
        }

        latch.await();

        String outputFile = "output.txt";
        try (RandomAccessFile writer = new RandomAccessFile(outputFile, "rw");
             FileChannel channel = writer.getChannel()) {
            for (FileReaderTask task : tasks) {
                ByteBuffer buff = ByteBuffer.wrap(task.getFileContent().concat("\n").getBytes(StandardCharsets.UTF_8));
                channel.write(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private final String filePath;
    private String fileContent;
    private final CountDownLatch latch;

    public FileReaderTask(String filePath, CountDownLatch latch) {
        this.filePath = filePath;
        this.latch = latch;
    }

    @Override
    public void run() {
        try (RandomAccessFile reader = new RandomAccessFile(filePath, "r");
             FileChannel channel = reader.getChannel();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int bufferSize = 1024;
            if (bufferSize > channel.size()) {
                bufferSize = (int) channel.size();
            }
            ByteBuffer buff = ByteBuffer.allocate(bufferSize);
            while (channel.read(buff) > 0) {
                out.write(buff.array(), 0, buff.position());
                buff.clear();
            }

            fileContent = out.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            latch.countDown();
        }
    }

    public String getFileContent() {
        return fileContent;
    }

}
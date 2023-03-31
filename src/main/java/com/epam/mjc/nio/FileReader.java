package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;



public class FileReader {

    public Profile getDataFromFile(File file) {
        try(RandomAccessFile aFile=new RandomAccessFile(file,"r");FileChannel channel=aFile.getChannel()) {
            StringBuilder content = new StringBuilder();
            long fileSize = channel.size();
            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
            channel.read(buffer);
            buffer.flip();
            while (buffer.hasRemaining()) {
                content.append((char) buffer.get());
            }
            String string = content.toString();
            String[] pairs = string.split("\r\n");
            for (int i = 0; i < pairs.length; i++) {
                pairs[i] = pairs[i].split(": ")[1];
            }
            String name = pairs[0];
            int age = Integer.parseInt(pairs[1]);
            String email = pairs[2];
            long phone = Long.parseLong(pairs[3]);
            return new Profile(name, age, email, phone);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String []args){
        FileReader fileReader=new FileReader();
        File f=new File("Profile.txt");
        fileReader.getDataFromFile(f);
    }
}

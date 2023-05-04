package com.commonfunction;

import org.apache.commons.io.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class ReadFileFIS {
    public static void main(String[] args) throws IOException {

    String fileContents =     ReadFileFIS.readFile("src/test/resources/json/sample.json");
        System.out.println("fileContents : "  + fileContents);
    }

    public static String readFile(String filename) throws IOException {
        FileInputStream fis=null;
        File file;
     try {
         file = new File(filename);
         fis = new FileInputStream(file);
         String fileContents = IOUtils.toString(fis, "UTF-8");
         return fileContents;
     }
     finally {
         
         fis.close();
     }
    }
}

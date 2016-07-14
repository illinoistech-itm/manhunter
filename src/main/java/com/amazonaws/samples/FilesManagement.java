/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amazonaws.samples;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author Ygor Santos
 */
public class FilesManagement {
    
    String filesPath;
    ArrayList<String> fileNames=new ArrayList<String>();
    ArrayList<String> MetadataFileNames=new ArrayList<String>();
    ArrayList<File> fileList = new ArrayList<File>();
    ArrayList<File> metadataFileList = new ArrayList<File>();
    File folder = new File(filesPath);
    File listOfFiles[] = folder.listFiles();
    
    public FilesManagement(){
        filesPath="src/data";
        //"C:\\Users\\Ygor Santos\\Desktop\\TestingEnvironment";
        //"C:\\Users\\Ygor Santos\\aws-sdk-java\\aws-java-sample";
    }
    
    public ArrayList<File> sequentialFiles(String type) throws IOException{
        /*
        //String fileContent=null;
        File file = new File(filesPath);
        if(file.exists()){
            if(file.isFile()){
                return file.getName();
            }
        }
        return null;
        */
        
        for (int i=0; i<listOfFiles.length;i++){//File file : listOfFiles) {
            if (listOfFiles[i].isFile()) {
                String filename = listOfFiles[i].getName();
                if(filename.substring(filename.length()-1)=="M"){
                    //Gabriel
                    //formatMetadata(listOfFiles[i]);
                    metadataFileList.add(listOfFiles[i]);
                    //System.out.println(filename);
                }
                else{
                    //readFile(listOfFiles[i]);
                    fileList.add(listOfFiles[i]);
                    
                    //System.out.println(filename);
                }
                
                //System.out.println(listOfFiles[i].getName());
            }
        }
        
        
        
        if(type=="object"){
            return fileList;
        }
        else{
            if(type=="metadata"){
                return metadataFileList;
            }
            else{
                return null;
            }
        }
    }

    
    public void readFile(InputStream file) throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(file));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            System.out.println(everything);
        } finally {
            br.close();
        }
        
    }
    
    public InputStream readMetadataFile(File file) throws IOException{
        InputStream fileContent;
        try{
            fileContent=new FileInputStream(file);
            fileContent.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Metadata File not found!");
            return  null;
        }
        catch(IOException io){
            System.out.println("Error when reading metadata file!");
            return null;
        }
        
        return fileContent;
    }
    
    
    public void readMetadataFile(ObjectMetadata objmetadata, File file) throws IOException{
        
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            //StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                //One option would be:
                //objmetadata.addUserMetadata("manhunter", line);

                line = br.readLine();
            }
            //String everything = sb.toString();
            //System.out.println(everything);
        } finally {
            br.close();
        }
    }
}

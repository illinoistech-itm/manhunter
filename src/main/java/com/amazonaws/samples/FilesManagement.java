/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amazonaws.samples;

import java.io.*;
/**
 *
 * @author Ygor Santos
 */
public class FilesManagement {
    
    public String sequentialFiles(String filesPath) throws IOException{
        String fileContent=null;
        File file = new File(filesPath);
        if(file.exists()){
            if(file.isFile()){
                try{
                    fileContent=readFile(file.getName());
                }
                catch(FileNotFoundException noFile){
                    System.out.println("Error getting file name!");
                }
            }
        }
        return fileContent;
    }
    
    public String readFile(String filename) throws FileNotFoundException, IOException{
        String fileContent=null;
        String line=null;
        
        try{
            FileReader fileReader=new FileReader(filename);
            BufferedReader buffer = new BufferedReader(fileReader);
            
            while((line=buffer.readLine())!=null){
                fileContent=fileContent+line;
            }
            buffer.close();
            
        }
        catch (FileNotFoundException errorCaught){
            System.out.println("Error when trying to open the file: "+filename);
        }
        
        return fileContent;
    }
}

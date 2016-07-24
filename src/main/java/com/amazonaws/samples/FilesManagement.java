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
    
    String filesPath;//="src/encryptedData";
    ArrayList<String> fileNames=new ArrayList<String>();
    ArrayList<String> MetadataFileNames=new ArrayList<String>();
    ArrayList<File> fileList = new ArrayList<File>();
    ArrayList<File> metadataFileList = new ArrayList<File>();
    File folder;
    File listOfFiles[];
    int numberOfFiles;
    
    public FilesManagement(String path){
        filesPath=path;
        folder=new File(filesPath);
        listOfFiles= folder.listFiles();
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
                    //InputStream fileContent = readMetadataFile(listOfFiles[i]);
                    //wordSplit(fileContent);
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
            numberOfFiles=fileList.size();
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
    /*
    public void insertionSort(ArrayList<Integer> vetor) {
        long trocas=0;
        int eleito;
        for (int i = 1; i < vetor.size(); i++) {
            eleito = vetor.get(i);
            int j;
            for (j = i-1; (j >= 0) && (vetor.get(j) > eleito); j--) {
                //vetor[j+1] = vetor[j];
                vetor
                trocas++;
            }
            vetor[j+1] = eleito;
        }
        System.out.println("Número de trocas: "+trocas);
        //return vetor;
    }
*/

    
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
    
    public void writeInFile(String word) throws FileNotFoundException{
        String fileName=word;
        PrintWriter writer = new PrintWriter("src/WordsToCompare/"+word);
        writer.println(word);
        writer.close();
    }
    
    public ArrayList<String> readFilesWithRecentlyEncryptedWords() throws IOException{
        ArrayList<String>temp=new ArrayList<String>();
        File folder = new File("your/path");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String tempWord=readFile(listOfFiles[i].getName());
                temp.add(tempWord);
                //System.out.println("File " + listOfFiles[i].getName());
            } 
        }
        return temp;
    }
    
    public String readFile(String fileName) throws FileNotFoundException, IOException{
        String temp;
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            temp = sb.toString();
        } finally {
            br.close();
        }
        return temp;
    }
    
    /*
    Gabriel's Code
    */
    
    public ArrayList<String[]> wordSplit(InputStream file) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(file));
        ArrayList<String[]> wordList = new ArrayList<String[]>();
        String line = br.readLine(); 
        while (line != null) {
            String[] words = line.split(":");
            wordList.add(words);
        }
      // return(wordList);    
   
         //insertionsort - Sort data 
           
        int i=0;
        int j=0;                   
        int key = 0,keyO = 0;               
        int keyT=0;
        for (i = 1; i < wordList.size() ; i++)    // Start with 1 (not 0)
        {
            String k[] = wordList.get(i);  
            String g[] = wordList.get(i-1);
            key = Integer.parseInt(k[1]);
            for(j = i-1; j>=0; j--)   // Smaller values are moving up
            {
                String t[] = wordList.get(j);
                keyT = Integer.parseInt(t[1]);
                if(key < keyT){
                    wordList.set(j+1, t);
                    if(j == 0 ){
                        wordList.set(0,k);
                    }
                }
                else
                {
                    wordList.set(j+1,k);
                    break;
                }
            }
        }
        return wordList;
    }
}

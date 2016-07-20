/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amazonaws.samples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Ygor Santos
 */
public class Attack {
    
    FilesManagement fmgt = new FilesManagement();
    ArrayList<String> allWords = new ArrayList<String>();
    
    public void invertedMatrix(int numberOfFiles, int numberOfWords, ArrayList<String[]> keypairwords, ArrayList<File> metadataFileList, ArrayList<File> filesList) throws IOException{
        
        for(int i=0; i<metadataFileList.size();i++){
            readFileForAttacks(metadataFileList.get(i));
        
            
            
            /*
            for (int j=0; j<metadataFileList.size();j++){
                
                
                
                //InputStream fileContent = fmgt.readMetadataFile(metadataFileList.get(j));
                for(int y=0;y<keypairwords.size();y++){
                    String [] k=keypairwords.get(y);
                    //if(k[0]==)
                }
                
                //if()
            }*/
        }
        
        int[][] invertedMatrix = new int[allWords.size()][metadataFileList.size()];
        
        for(int row=0;row<allWords.size();row++){
            for(int column=0;column<metadataFileList.size();column++){
                ArrayList<String> wordsInFile = readWordsInFile(metadataFileList.get(column));
                if(wordsInFile.contains(allWords.get(row))){
                    invertedMatrix[row][column]=1;
                }
                else{
                    invertedMatrix[row][column]=0;
                }
            }
        }
        
    }
    
    public String convertInputStreamToString(InputStream is){
        String content = new Scanner(is,"UTF-8").useDelimiter("\\A").next();
        return content;
    }
    
    public void readFileForAttacks(File file) throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String line = br.readLine();

            while (line != null) {
                String[]wordsInLine=line.split(",");
                allWords.add(wordsInLine[0]);
                line = br.readLine();
            }

        } finally {
            br.close();
        }
        
    }
    
    public ArrayList<String> readWordsInFile(File file) throws FileNotFoundException, IOException{
        ArrayList<String> wordsInFile = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String line = br.readLine();

            while (line != null) {
                String[]wordsInLine=line.split(",");
                wordsInFile.add(wordsInLine[0]);
                line = br.readLine();
            }

        } finally {
            br.close();
        }
        return wordsInFile;
    }
    
}

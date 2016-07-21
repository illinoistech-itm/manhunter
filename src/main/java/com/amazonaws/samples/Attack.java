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
import java.util.*;

/**
 *
 * @author Ygor Santos
 */
public class Attack {
    
    FilesManagement fmgt = new FilesManagement();
    ArrayList<String> allWords = new ArrayList<String>();
    int [][] invertedMatrix;
    ArrayList<String[]>wordsInFilesCount = new ArrayList<String[]>();
    
    /*
    This function builds a 2-d array where each row corresponds to an encrypted keyword and each column corresponds to a document. 
    The entries of the matrix will be ‘1’ (or the count) if the word appears in the document, and ‘0’ if it doesn’t.
    */
    
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
        
        invertedMatrix = new int[allWords.size()][metadataFileList.size()];
        
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
    
    /*
    Utility function that returns the number of nonzero columns for any given row.
    This is the result count—the number of documents matching each keyword.
    */
    
    
    //This function must be called from within a loop going through every word in the list of words.
    public int countNonZeroColumns(String word){
        int count=0;
        for(int column=0;column<invertedMatrix[0].length;column++){
            if(invertedMatrix[allWords.indexOf(word)][column]==1){
                count++;
            }
        }
        String [] keywordAndCount = new String[1];
        keywordAndCount[0]=word;
        keywordAndCount[1]=Integer.toString(count);
        wordsInFilesCount.add(keywordAndCount);
        return count;
    }
    
    public void SortingTheList(){
        int max=-1;
        ArrayList<String[]>sortedList=new ArrayList<String[]>();
        String[]y=new String[1];
        String[]higherNumberArray;
        while(wordsInFilesCount.size()!=0){
            for(int i=0;i<wordsInFilesCount.size();i++){
                y=wordsInFilesCount.get(i);
                int yCount=Integer.parseInt(y[1]);
                if(yCount>max){
                    max=yCount;
                    higherNumberArray=y;
                }
            }
            sortedList.add(y);
            wordsInFilesCount.remove(y);
        }
        wordsInFilesCount=sortedList;   
    }
   
    /*
    Functions to support the creation of Matrix 
    */
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

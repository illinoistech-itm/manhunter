/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amazonaws.samples;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 * @author Ygor Santos
 */
public class Attack {
    
    FilesManagement fmgt = new FilesManagement();
    
    public void invertedMatrix(int numberOfFiles, int numberOfWords, ArrayList<String[]> keypairwords, ArrayList<File> metadataFileList, ArrayList<File> filesList) throws IOException{
        int[][] invertedMatrix = new int[numberOfWords][numberOfFiles];
        for(int i=0; i<metadataFileList.size();i++){
            for (int j=0; j<metadataFileList.size();j++){
                //InputStream fileContent = fmgt.readMetadataFile(metadataFileList.get(j));
                for(int y=0;y<keypairwords.size();y++){
                    String [] k=keypairwords.get(y);
                    //if(k[0]==)
                }
                
                //if()
            }
        }
    }
}

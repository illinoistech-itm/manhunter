/*
 * Copyright 2010-2013 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.amazonaws.samples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.UUID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This sample demonstrates how to make basic requests to Amazon S3 using
 * the AWS SDK for Java.
 * <p>
 * <b>Prerequisites:</b> You must have a valid Amazon Web Services developer
 * account, and be signed up to use Amazon S3. For more information on
 * Amazon S3, see http://aws.amazon.com/s3.
 * <p>
 * <b>Important:</b> Be sure to fill in your AWS access credentials in
 * ~/.aws/credentials (C:\Users\USER_NAME\.aws\credentials for Windows
 * users) before you try to run this sample.
 */
public class S3Sample {

    public void printListOfBuckets(AmazonS3 s3){
        try{
            System.out.println("Listing buckets");
            for (Bucket bucket : s3.listBuckets()) {
                System.out.println(" - " + bucket.getName());
                bucketsList.add(bucket.getName());
            }
            System.out.println();
        }catch (AmazonServiceException ase) {
                System.out.println("Caught an AmazonServiceException, which means your request made it "
                        + "to Amazon S3, but was rejected with an error response for some reason.");
                System.out.println("Error Message:    " + ase.getMessage());
                System.out.println("HTTP Status Code: " + ase.getStatusCode());
                System.out.println("AWS Error Code:   " + ase.getErrorCode());
                System.out.println("Error Type:       " + ase.getErrorType());
                System.out.println("Request ID:       " + ase.getRequestId());
            } catch (AmazonClientException ace) {
                System.out.println("Caught an AmazonClientException, which means the client encountered "
                        + "a serious internal problem while trying to communicate with S3, "
                        + "such as not being able to access the network.");
                System.out.println("Error Message: " + ace.getMessage());
            }
    }
    
    Scanner scan=new Scanner(System.in);
    ArrayList<String> bucketsList = new ArrayList<String>();
    
    public void menu(AmazonS3 s3) throws IOException, Exception{
        System.out.println("===========================================");
        System.out.println("Manhunter");
        System.out.println("===========================================\n");
        
        FilesManagement filemgt = new FilesManagement();
        boolean startOver=true;
        
        while(startOver){
        
            int option;
            System.out.println("===========================================");
            System.out.println("Choose your option");
            System.out.println("===========================================");
            System.out.println("1 - Create buckets");
            System.out.println("2 - List buckets");
            System.out.println("3 - Delete buckets");
            System.out.println("4 - Download Object");
            System.out.println("5 - Exit");
            System.out.print("Type your option: ");
            option=scan.nextInt();

            switch(option){
                case 1:
                    ArrayList<File> listOfFiles=filemgt.sequentialFiles("object");
                    if(listOfFiles==null){
                        System.out.println("Error! No files found!");
                    }
                    for(int i=0;i<listOfFiles.size();i++){
                        createBuckets(s3,listOfFiles.get(i),filemgt);
                    }
                break;
                case 2:
                    printListOfBuckets(s3);
                break;
                case 3:
                    deleteBucket(s3); 
                break;
                case 4:
                    downloadObject(s3, filemgt);
                break;
                case 5:
                    System.out.println("Exiting.");
                    startOver=false;
                break;
                default:
                    System.out.println("Invalid Option! Try again.");
                break;
            }
        }
    }
    
    public void deleteBucket(AmazonS3 s3){
        try{
            System.out.print("Please type the bucket name: ");
            String bucketName=scan.next();
            for(int i=0;i<bucketsList.size();i++){
                if(bucketsList.get(i)==bucketName){
                    System.out.println("Deleting an object\n");
                    s3.deleteObject(bucketName, "manhunter");
                    /*
                     * Delete a bucket - A bucket must be completely empty before it can be
                     * deleted, so remember to delete any objects from your buckets before
                     * you try to delete them.
                     */
                    System.out.println("Deleting bucket " + bucketName + "\n");
                    s3.deleteBucket(bucketName);
                    return;
                }
            }
            System.out.println("Bucket not found.");
        }catch (AmazonServiceException ase) {
                System.out.println("Caught an AmazonServiceException, which means your request made it "
                        + "to Amazon S3, but was rejected with an error response for some reason.");
                System.out.println("Error Message:    " + ase.getMessage());
                System.out.println("HTTP Status Code: " + ase.getStatusCode());
                System.out.println("AWS Error Code:   " + ase.getErrorCode());
                System.out.println("Error Type:       " + ase.getErrorType());
                System.out.println("Request ID:       " + ase.getRequestId());
            } catch (AmazonClientException ace) {
                System.out.println("Caught an AmazonClientException, which means the client encountered "
                        + "a serious internal problem while trying to communicate with S3, "
                        + "such as not being able to access the network.");
                System.out.println("Error Message: " + ace.getMessage());
            }
    }
    
    
    public static void main(String[] args) throws IOException, Exception {
        /*
         * Create your credentials file at ~/.aws/credentials (C:\Users\USER_NAME\.aws\credentials for Windows users) 
         * and save the following lines after replacing the underlined values with your own.
         *
         * [default]
         * aws_access_key_id = YOUR_ACCESS_KEY_ID
         * aws_secret_access_key = YOUR_SECRET_ACCESS_KEY
         */
        
        S3Sample s3sample = new S3Sample();
        
        ClientConfiguration opts = new ClientConfiguration();
        opts.setSignerOverride("S3SignerType");
        
        AmazonS3 s3 = new AmazonS3Client(opts);
        
        s3.setEndpoint("http://objectstorage.sat.iit.edu:8773/services/objectstorage/");
        s3.setS3ClientOptions(new S3ClientOptions().withPathStyleAccess( true ) );
        
        s3sample.menu(s3);
    }
    
    public void downloadObject(AmazonS3 s3, FilesManagement mgt) throws IOException{
        String key="manhunter";
        //S3Object object = s3.getObject(new GetObjectRequest("manhunter-s3-bucket-1468091409", "file"));
        
        S3Object object = s3.getObject("manhunter-s3-bucket-1468091409", key);
        BufferedReader reader = new BufferedReader(new InputStreamReader(object.getObjectContent()));
        //File file = new File("localFilename");      
        //Writer writer = new OutputStreamWriter(new FileOutputStream(file));

        while (true) {          
             String line = reader.readLine();           
             if (line == null)
                  break;            
            System.out.println(line + "\n");
        }
        
        /*
        
        //printListOfBuckets(s3);
        
        S3Object object = s3.getObject(new GetObjectRequest(bucketsList.get(27), key));
        //System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
        System.out.println(object.getObjectContent());
        GetObjectRequest rangeObjectRequest = new GetObjectRequest(bucketsList.get(27), key);
        S3Object objectPortion = s3.getObject(rangeObjectRequest);
        InputStream objectData = objectPortion.getObjectContent();
        mgt.readFile(objectData);
        //displayTextInputStream(objectData);
        //objectData.close();
        */
    }
    
    public void createBuckets(AmazonS3 s3, File fileName, FilesManagement filemgt) throws IOException{
        long timestamp = System.currentTimeMillis() / 1000;
        System.out.println("Entering createBuckets function. timestamp inicialized as: "+timestamp);
        String bucketName = "manhunter-s3-bucket-" + timestamp; //UUID.randomUUID();
        String key = "manhunter";//Long.toString(timestamp);
        ObjectMetadata metadata = new ObjectMetadata();
        
            try {
                /*
                 * Create a new S3 bucket - Amazon S3 bucket names are globally unique,
                 * so once a bucket name has been taken by any user, you can't create
                 * another bucket with that same name.
                 *
                 * You can optionally specify a location for your bucket if you want to
                 * keep your data closer to your applications or users.
                 */
                
                timestamp = System.currentTimeMillis() / 1000;
                bucketName = "manhunter-s3-bucket-" + timestamp;
                
                System.out.println("Creating bucket " + bucketName + "\n");
                s3.createBucket(bucketName);
                bucketsList.add(bucketName);

                /*
                 * List the buckets in your account
                 */
                //printListOfBuckets(s3);

                /*
                 * Upload an object to your bucket - You can easily upload a file to
                 * S3, or upload directly an InputStream if you know the length of
                 * the data in the stream. You can also specify your own metadata
                 * when uploading to S3, which allows you set a variety of options
                 * like content-type and content-encoding, plus additional metadata
                 * specific to your applications.
                 */
                ArrayList<String[]> keyPairs = new ArrayList<String[]>();
                System.out.println("Uploading a new object to S3 from a file\n");
                PutObjectRequest por=new PutObjectRequest(bucketName, key, fileName);
                keyPairs=filemgt.wordSplit(filemgt.readMetadataFile(fileName));
               // KeyPairs=filemgt.readMetadataFile(fileName);
               for(int i=0;i<keyPairs.size();i++){
                   metadata.addUserMetadata((keyPairs.get(i))[0], (keyPairs.get(i))[1]);
               }
                por.setMetadata(metadata);//.setMetadata(metadata));
                s3.putObject(por);
                
                //Options to send metadata while also creating the buckets
                
                //s3.putObject(bucketName, key, fileName, objmetadata);
                //s3.putObject(new PutObjectRequest(bucketName,key,fileName));
                //s3.putObject(bucketName, key, input, objmetadata)
                

                /*
                 * Download an object - When you download an object, you get all of
                 * the object's metadata and a stream from which to read the contents.
                 * It's important to read the contents of the stream as quickly as
                 * possibly since the data is streamed directly from Amazon S3 and your
                 * network connection will remain open until you read all the data or
                 * close the input stream.
                 *
                 * GetObjectRequest also supports several other options, including
                 * conditional downloading of objects based on modification times,
                 * ETags, and selectively downloading a range of an object.
                 */
                //System.out.println("Downloading an object");
                

                /*
                 * List objects in your bucket by prefix - There are many options for
                 * listing the objects in your bucket.  Keep in mind that buckets with
                 * many objects might truncate their results when listing their objects,
                 * so be sure to check if the returned object listing is truncated, and
                 * use the AmazonS3.listNextBatchOfObjects(...) operation to retrieve
                 * additional results.
                 */
                
                /*
                ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
                        .withBucketName(bucketName)
                        .withPrefix("My"));
                for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                    System.out.println(" - " + objectSummary.getKey() + "  " +
                            "(size = " + objectSummary.getSize() + ")");
                }
                System.out.println();
                */

                /*
                 * Delete an object - Unless versioning has been turned on for your bucket,
                 * there is no way to undelete an object, so use caution when deleting objects.
                 */
                
            } catch (AmazonServiceException ase) {
                System.out.println("Caught an AmazonServiceException, which means your request made it "
                        + "to Amazon S3, but was rejected with an error response for some reason.");
                System.out.println("Error Message:    " + ase.getMessage());
                System.out.println("HTTP Status Code: " + ase.getStatusCode());
                System.out.println("AWS Error Code:   " + ase.getErrorCode());
                System.out.println("Error Type:       " + ase.getErrorType());
                System.out.println("Request ID:       " + ase.getRequestId());
            } catch (AmazonClientException ace) {
                System.out.println("Caught an AmazonClientException, which means the client encountered "
                        + "a serious internal problem while trying to communicate with S3, "
                        + "such as not being able to access the network.");
                System.out.println("Error Message: " + ace.getMessage());
            }
        }
    
    private static void displayTextInputStream(InputStream input) throws IOException {
        /*
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null){
                System.out.println("Entered if.");
                break;
            }
            System.out.println("I'm here.");
            System.out.println("    " + line);
        }
        System.out.println();
        */
        StringBuilder sb=new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        String read;

        while((read=br.readLine()) != null) {
            //System.out.println(read);
            sb.append(read);   
        }

        br.close();
        System.out.println(sb.toString());
        //return sb.toString();
    }
    
}

    /**
     * Creates a temporary file with text data to demonstrate uploading a file
     * to Amazon S3
     *
     * @return A newly created temporary file with text data.
     *
     * @throws IOException
     */
    
    /*
    private static File createSampleFile() throws IOException {
        File file = File.createTempFile("aws-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("01234567890112345678901234\n");
        writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
        writer.write("01234567890112345678901234\n");
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.close();

        return file;
    }
    */

    /**
     * Displays the contents of the specified input stream as text.
     *
     * @param input
     *            The input stream to display as text.
     *
     * @throws IOException
     */
    


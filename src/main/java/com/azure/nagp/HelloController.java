package com.azure.nagp;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.azure.storage.*;
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.BlobErrorCode;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.BlobStorageException;
import com.azure.storage.blob.specialized.BlockBlobClient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HelloController {

	List<String> files = new ArrayList<String>();
	   private final Path rootLocation = Paths.get("_Path_To_Save_The_File");
	
	// Define the connection-string with your values
	   public static final String storageConnectionString =
	       "DefaultEndpointsProtocol=https;AccountName=storageacchv;AccountKey=DkXmV++w0lyLSJBv0nWVRbcAhVWImWz6uIKZ9AKzgPmwOpSYSv3zEnofTuJ+RkT2p6ixRZLbbUUHqv55N6FWlw==;EndpointSuffix=core.windows.net";
	   
	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}
	
	@CrossOrigin(origins = "http://localhost:4200")	
	@PostMapping("/savefile")
	   public String handleFileUpload(@RequestParam("file") MultipartFile file) {
	      String message;
//	      try {
//	         try {
//	            Files.copy(file.getInputStream(), this.rootLocation.resolve("file_name.pdf"));
//	         } catch (Exception e) {
//	            throw new RuntimeException("FAIL!");
//	         }
//	         files.add(file.getOriginalFilename());
//
//	         message = "Successfully uploaded!";
//	         return ResponseEntity.status(HttpStatus.OK).body(message);
//	      } catch (Exception e) {
//	         message = "Failed to upload!";
//	         return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
//	      }
	      System.out.println("Inside save file method. file-"+file);
	      
	      BlobContainerClient containerClient=null;
	      String yourSasToken = "<insert-your-sas-token>";
	      /* Create a new BlobServiceClient with a SAS Token */
	      //https://storageacchv.blob.core.windows.net/blobcontainerhv
//	      BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
//	          .endpoint("https://storageacchv.blob.core.windows.net")
//	          .buildClient();
	      BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(storageConnectionString).buildClient();
	      
	      containerClient=blobServiceClient.getBlobContainerClient("blobcontainerhv");
	      /* Create a new container client */
	    /*  try {
	          containerClient = blobServiceClient.createBlobContainer("blobcontainerhvv");
	      } catch (BlobStorageException ex) {
	          // The container may already exist, so don't throw an error
	          if (!ex.getErrorCode().equals(BlobErrorCode.CONTAINER_ALREADY_EXISTS)) {
	              throw ex;
	          }
	      } */
	      
	      System.out.println("connection established with the contianer");

	      
	      //List te blob in container 
	      System.out.println("\nListing blobs...");

	   // List the blob(s) in the container.
	   for (BlobItem blobItem : containerClient.listBlobs()) {
	       System.out.println("blob item is  --- " + blobItem.getName());
	   }
	   
	      BlockBlobClient blockBlobClient = containerClient.getBlobClient("myblockblob").getBlockBlobClient();
	      String dataSample = "samples";
	      try (ByteArrayInputStream dataStream = new ByteArrayInputStream(dataSample.getBytes())) {
	          blockBlobClient.upload(dataStream, dataSample.length());
	      } catch (IOException e) {
			// TODO Auto-generated catch block
	    	  System.out.println("errr ereorerehkjfj ");
			e.printStackTrace();
		}
	      
	      /* Upload the file to the container */
//	      BlobClient blobClient = containerClient.getBlobClient("test-remote.jpg");
//	      blobClient.uploadFromFile("./D:/Cheerboard_Sept'2020.pdf");
	    //  blobClient.upload(file, 1);
	      System.out.println("uploaded successfullley");
	      return "success";
	   }


}

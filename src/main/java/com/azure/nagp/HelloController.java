package com.azure.nagp;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.specialized.BlockBlobClient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HelloController {

	List<String> files = new ArrayList<>();

	// Define the connection-string with your values
	
	public static final String STORAGE_CONNECTIONSTRING = "DefaultEndpointsProtocol=https;AccountName=storageaccountnagphv;AccountKey=7H15QHM4WJuMKgVOnJhj0IqO90c3vc03nS6FzePHR5ZXOCKYstp4vBVfkW+n70Dfw6k4UBbcrlD96Fzcde57LA==;EndpointSuffix=core.windows.net";

	public static final String SQL_CONNECTIONSTRING = "jdbc:sqlserver://sqlserverhv.database.windows.net:1433;database=sqldbhv;user=NagpHimanshi@sqlserverhv;password=Nagp@Himanshi;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	// @CrossOrigin(origins = "https://frontendwebapphv.azurewebsites.net/")
	@GetMapping("/getItem/{id}")
	public ResponseEntity<String> getItemFromId(@PathVariable(value = "id") String id) {
		String name = null;
		

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement insertStatement = null;

		try {
			connection = DriverManager.getConnection(SQL_CONNECTIONSTRING);

			String selectSql = "SELECT name FROM dbo.item where id = '" + id + "'";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectSql);

			// Iterate through the result set and print the attributes.
			while (resultSet.next()) {
			
				name = resultSet.getString(1);
			}

			insertStatement = connection.prepareStatement("INSERT INTO dbo.item(id, name) VALUES ('4','D');");

			insertStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections after the data has been handled.

			if (resultSet != null)
				try {
					resultSet.close();
				} catch (Exception e) {
					System.out.println("Exception in closing resultset");
				}
			if (statement != null)
				try {
					statement.close();
				} catch (Exception e) {
					System.out.println("Exception in closing statement");
				}
			if (connection != null)
				try {
					connection.close();
				} catch (Exception e) {
					System.out.println("Exception in closing connection");
				}
			if (insertStatement != null)
				try {
					insertStatement.close();
				} catch (Exception e) {
					System.out.println("Exception in closing insert");
				}
		}

		return new ResponseEntity<>(name, HttpStatus.OK);
	}

	// @CrossOrigin(origins = "https://frontendwebapphv.azurewebsites.net/")
	@PostMapping("/savefile")
	public String handleFileUpload(@RequestParam("file") MultipartFile file) {

		BlobContainerClient containerClient = null;

		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(STORAGE_CONNECTIONSTRING)
				.buildClient();

		containerClient = blobServiceClient.getBlobContainerClient("blobcontainerhv");

		// List the blob(s) in the container.
		for (BlobItem blobItem : containerClient.listBlobs()) {
			System.out.println("blob item is  --- " + blobItem.getName());
		}

		BlockBlobClient blockBlobClient = containerClient.getBlobClient("myblockblob").getBlockBlobClient();
		String dataSample = "samples";
		try (ByteArrayInputStream dataStream = new ByteArrayInputStream(dataSample.getBytes())) {
			blockBlobClient.upload(dataStream, dataSample.length());
		} catch (IOException e) {
			
			
			e.printStackTrace();
		}

		
		return "success";
	}

}

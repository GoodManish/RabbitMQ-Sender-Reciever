package com.javainuse.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javainuse.model.Employee;

@Service
public class RabbitMQListner implements MessageListener {

	public void onMessage(Message message) {
//		String Json = message.getBody().toString();
		System.out.println("Consuming Message - " + new String(message.getBody()));
		
		//Object Mapper instance
		ObjectMapper mapper = new ObjectMapper();
		 
		//Convert JSON to POJO
		Employee emp = null;
		try {
			emp = mapper.readValue(new String(message.getBody()), Employee.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Employee :: "+emp);
	}
	
	public static Object byteToObj(byte[] bytes) throws IOException, ClassNotFoundException {
	    ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
	    ObjectInputStream objStream = new ObjectInputStream(byteStream);

	    return objStream.readObject();
	}

}
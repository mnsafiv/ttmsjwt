package ru.safonoviv.ttms.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class ReadJson {
	public JSONObject read() {
		String file="src/main/resources/openapi/response.json";
		String content="";
		try {
			content= new String(Files.readAllBytes(Paths.get(file)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new JSONObject(content);
	}

}

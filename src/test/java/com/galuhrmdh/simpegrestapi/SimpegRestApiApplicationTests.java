package com.galuhrmdh.simpegrestapi;

import com.galuhrmdh.simpegrestapi.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@SpringBootTest
class SimpegRestApiApplicationTests {

	@Autowired
	private DepartmentService departmentService;

	@Test
	void contextLoads() {
		try {
			String csvResult = departmentService.buildCsv();

			FileWriter myWriter = new FileWriter("/opt/simpeg_files/export/testt.csv");
			myWriter.write(csvResult);
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

}

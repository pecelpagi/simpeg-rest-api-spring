package com.galuhrmdh.simpegrestapi.service;

import com.galuhrmdh.simpegrestapi.entity.Employee;
import com.galuhrmdh.simpegrestapi.enums.RedisKey;
import com.galuhrmdh.simpegrestapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExportService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    private String toEmployeesAsCsv(List<Employee> employees) {
        StringBuilder csvData = new StringBuilder();
        String CSV_HEADER = "No. Urut," +
                "ID," +
                "NIK," +
                "Nama," +
                "Jenis Kelamin," +
                "Unit," +
                "Tgl Masuk," +
                "Alamat," +
                "Kota," +
                "Kota Asal," +
                "Tempat Lahir," +
                "Tgl Lahir," +
                "Jabatan," +
                "Agama," +
                "Warga Negara," +
                "Status Perkawinan," +
                "Status PPH," +
                "Gol. Darah," +
                "No. Telp," +
                "No. BPJS Kesehatan," +
                "No. BPJS Ketenagakerjaan," +
                "No. BPJS Pensiun," +
                "Status Kontrak";

        csvData.append("Daftar Karyawan");
        csvData.append("\n");
        csvData.append("\n");
        csvData.append(CSV_HEADER);
        csvData.append("\n");

        StringBuilder CSV_BODY = new StringBuilder();

        var sequenceNumber = 0;
        for (Employee employee : employees) {
            sequenceNumber++;
            CSV_BODY.append(sequenceNumber).append(",");
            CSV_BODY.append(employee.getId()).append(",");
            CSV_BODY.append(employee.getIdNumber()).append(",");
            CSV_BODY.append(employee.getName()).append(",");
            CSV_BODY.append(employee.getGender()).append(",");
            CSV_BODY.append(employee.getDepartment().getCode()).append(",");
            CSV_BODY.append(employee.getEntryDate()).append(",");
            CSV_BODY.append(employee.getAddress()).append(",");
            CSV_BODY.append(employee.getCity()).append(",");
            CSV_BODY.append(employee.getOriginCity()).append(",");
            CSV_BODY.append(employee.getBirthplace()).append(",");
            CSV_BODY.append(employee.getBirthdate()).append(",");
            CSV_BODY.append(employee.getEmployeePosition().getName()).append(",");
            CSV_BODY.append(employee.getReligion()).append(",");
            CSV_BODY.append(employee.getCitizen()).append(",");
            CSV_BODY.append(employee.getMaritalStatus()).append(",");
            CSV_BODY.append(employee.getIncomeTaxStatus()).append(",");
            CSV_BODY.append(employee.getBloodType()).append(",");
            CSV_BODY.append("-").append(",");
            CSV_BODY.append(employee.getBpjsHealth()).append(",");
            CSV_BODY.append(employee.getBpjsEmployment()).append(",");
            CSV_BODY.append(employee.getBpjsRetirement()).append(",");
            CSV_BODY.append("-");
            CSV_BODY.append("\n");
        }

        csvData.append(CSV_BODY);

        return csvData.toString();
    }

    private String createFileName() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String formattedDate = myDateObj.format(myFormatObj);

        return formattedDate.concat(".csv");
    }

    @Transactional
    public String exportEmployee() {
        List<Employee> employees =  employeeRepository.findAll();

        String fileName = createFileName();

        try {
            String csvResult = toEmployeesAsCsv(employees);

            String CSV_FILENAME = "/opt/simpeg_files/export/employee_".concat(fileName);
            FileWriter myWriter = new FileWriter(CSV_FILENAME);
            myWriter.write(csvResult);
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "employee_".concat(fileName);
    }

    public String getExportEmployeeStatus() {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        String value = operations.get(RedisKey.EXPORT_EMPLOYEE_PROGRESS.toString());

        return value == null ? "NO_EXPORT" : "IS_EXPORTING";
    }
}

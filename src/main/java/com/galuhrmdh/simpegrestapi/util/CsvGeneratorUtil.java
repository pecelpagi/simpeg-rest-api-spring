package com.galuhrmdh.simpegrestapi.util;

import com.galuhrmdh.simpegrestapi.entity.Department;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CsvGeneratorUtil {
    private static final String CSV_HEADER = "Code,Name\n";

    public String generateCsv(List<Department> departments) {
        StringBuilder csvContent = new StringBuilder();
        csvContent.append("Daftar Karyawan\n\n");
        csvContent.append(CSV_HEADER);

        for (Department department : departments) {
            csvContent.append(department.getName()).append(",")
                    .append(department.getName()).append("\n");
        }

        return csvContent.toString();
    }

}

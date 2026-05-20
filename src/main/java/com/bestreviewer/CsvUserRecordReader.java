package com.bestreviewer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** shealth.dat 형식 CSV 읽기 (헤더 1행 스킵). */
final class CsvUserRecordReader {

    List<UserRecord> read(String filename) throws IOException {
        List<UserRecord> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> tokens = splitCsvLine(line, ',');
                if (tokens.isEmpty()) {
                    break;
                }
                String id = tokens.get(0);
                int age = Integer.parseInt(tokens.get(1));
                double weight = Double.parseDouble(tokens.get(2));
                double height = Double.parseDouble(tokens.get(3));
                users.add(new UserRecord(id, age, weight, height));
                if (users.size() >= HealthConstants.MAX_USERS) {
                    break;
                }
            }
        }
        return users;
    }

    private List<String> splitCsvLine(String line, char delimiter) {
        List<String> tokens = new ArrayList<>();
        int start = 0;
        int end = line.indexOf(delimiter);
        while (end != -1) {
            tokens.add(line.substring(start, end));
            start = end + 1;
            end = line.indexOf(delimiter, start);
        }
        tokens.add(line.substring(start));
        return tokens;
    }
}

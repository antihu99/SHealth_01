package com.bestreviewer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** shealth.dat 형식 CSV 읽기 (헤더 1행 스킵). */
final class CsvUserRecordReader {

    List<UserRecord> read(String filename) throws IOException {
        List<UserRecord> userRecords = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            skipHeaderLine(reader);
            readUserRecordLines(reader, userRecords);
        }
        return userRecords;
    }

    private void skipHeaderLine(BufferedReader reader) throws IOException {
        reader.readLine();
    }

    private void readUserRecordLines(BufferedReader reader, List<UserRecord> userRecords) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            UserRecord userRecord = parseUserRecordLine(line);
            if (userRecord == null) {
                break;
            }
            userRecords.add(userRecord);
            if (userRecords.size() >= HealthConstants.MAX_USERS) {
                break;
            }
        }
    }

    private UserRecord parseUserRecordLine(String line) {
        List<String> tokens = splitCsvLine(line, ',');
        if (tokens.isEmpty()) {
            return null;
        }
        String userId = tokens.get(0);
        int age = Integer.parseInt(tokens.get(1));
        double weightKg = Double.parseDouble(tokens.get(2));
        double heightCm = Double.parseDouble(tokens.get(3));
        return new UserRecord(userId, age, weightKg, heightCm);
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

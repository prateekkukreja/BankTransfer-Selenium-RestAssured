package Core;

import ConfigManagers.LocalConfigs;
import org.testng.Assert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HelperActions {

    public void writeToFile(String str) {
        try {
            File file = new File(LocalConfigs.ACCOUNTS_DIR + "Accounts.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
            out.write(str + "\n");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void updateInFile(String str) {
        String existToken = getTokenFromFile();
        String targetFile = LocalConfigs.ACCOUNTS_DIR + "Accounts.txt";
        try (Stream<String> lines = Files.lines(Paths.get(targetFile))) {
            List<String> replaced = lines
                    .map(line -> line.replaceAll("JSESSIONID:" + existToken, str))
                    .collect(Collectors.toList());
            Files.write(Paths.get(targetFile), replaced);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    public List<String> readFile() {
        List<String> list = new ArrayList<>();
        try {
            String fileName = LocalConfigs.ACCOUNTS_DIR + "Accounts.txt";
            try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
                stream.forEach(list::add);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        return list;
    }

    public String getKeyFromFile(String input) {
        String outStr;
        List<String> profile = readFile();
        outStr = profile.stream()
                .filter(i -> i.contains(input))
                .findFirst()
                .orElse(null);
        String[] arr = Objects.requireNonNull(outStr).split(":");
        return arr[1];
    }

    public String getTokenFromFile() {
        String token = null;
        try {
            List<String> list = readFile();
            String[] arr = list.get(0).split(":");
            token = arr[1];
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        return token;
    }

    public String getBaseAcctFromFile() {
        String acct = null;
        try {
            List<String> list = readFile();
            String[] arr = list.get(1).split(":");
            acct = arr[1];
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        return acct;
    }
}
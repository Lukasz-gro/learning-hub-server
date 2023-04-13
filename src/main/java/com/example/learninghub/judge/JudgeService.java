package com.example.learninghub.judge;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class JudgeService {

    private final String dockerPath = "src/main/resources/docker/";
    private final String scriptsPath = dockerPath + "scripts/";
    private final String filesPath = "/tmp/";

    public String runCode(JudgeParams judgeParams) {
        String containerName = "container" + judgeParams.getTestCase();
        String filename = "file" + judgeParams.getTestCase() + ".py";
        String filePath = filesPath + filename;
        makeFile(filePath, judgeParams.getCode());
        execCommand(scriptsPath + "runContainer.sh " + containerName);
        execCommand(scriptsPath + "copyToContainer.sh " + containerName + " " + filePath);
        String output = execCommand("docker exec " + containerName + " python3 " + filename);
        new Thread(() -> {
            try {
                deleteFile(filePath);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            execCommand("docker stop " + containerName);
        }).start();
        return output;
    }

    private String execCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                return output.toString();
            } else {
                System.out.println("Failure!");
                //TODO: handle error
                System.exit(exitVal);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void makeFile(String path, String content) {
        try (PrintWriter out = new PrintWriter(path)) {
            out.println(content);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteFile(String path) throws Exception {
        File myObj = new File(path);
        if (!myObj.delete()) {
            throw new Exception("Cannot delete created file " + path);
        }
    }

}

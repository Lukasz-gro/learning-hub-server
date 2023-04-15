package com.example.learninghub.judge;

import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.io.*;

@Service
public class JudgeService {

    private final String dockerPath = System.getenv("DOCKER_DIR_PATH");
    private final String scriptsPath = dockerPath + "scripts/";
    private final String filesPath = "/tmp/";

    public String runCode(JudgeParams judgeParams) {
        String containerName = "container" + judgeParams.getProblemId();
        String filename = "file" + judgeParams.getTestCase();
        String code = judgeParams.getCode();
        execCommand(scriptsPath + "runContainer.sh " + containerName);
        if (judgeParams.getLanguage().equals("python")) {
            handlePython(containerName, filename, code);
        } else if (judgeParams.getLanguage().equals("cpp")) {
            handleCpp(containerName, filename, code);
        } else if (judgeParams.getLanguage().equals("java")) {
            handleJava(containerName, filename, code);
        } else {
            throw new RuntimeException("Wrong language");
        }
        String output = execCommand("docker exec " + containerName + " cat output");
        new Thread(() -> {
            execCommand("docker stop " + containerName);
        }).start();
        return output;
    }

    private void handlePython(String containerName, String filename, String code) {
        filename += ".py";
        String filePath = filesPath + filename;
        makeFile(filePath, code);
        execCommand(scriptsPath + "copyToContainer.sh " + containerName + " " + filePath);
        execCommand("docker exec " + containerName + " ./scripts/python/run.sh " + filename + " output");
        try {
            deleteFile(filePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleCpp(String containerName, String filename, String code) {
        filename += ".cpp";
        String filePath = filesPath + filename;
        makeFile(filePath, code);
        execCommand(scriptsPath + "copyToContainer.sh " + containerName + " " + filePath);
        execCommand("docker exec " + containerName + " ./scripts/cpp/compile.sh " + filename + " main");
        execCommand("docker exec " + containerName + " ./scripts/cpp/run.sh " + "main " + "output");
        try {
            deleteFile(filePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleJava(String containerName, String filename, String code) {
        //TODO:
        throw new RuntimeException("Java handling isn't implemented yet");
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
                System.out.println("Success! " + command);
                return output.toString();
            } else {
                System.out.println("Failure! " + command);
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

package com.example.learninghub.judge;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class JudgeService {

    private final String dockerPath = System.getenv("DOCKER_DIR_PATH");
    private final String scriptsPath = dockerPath + "scripts/";
    private final String filesPath = "/tmp/";

    public String runCode(JudgeParams judgeParams, Integer submitID, String input) {
        System.out.println("runCode2");
        String containerName = submitID + "_" + judgeParams.getTestCase();
        execCommand(scriptsPath + "runContainer.sh " + containerName);
        String output = switch (judgeParams.getLanguage()) {
            case "python" -> handlePython(containerName, judgeParams.getCode(), input);
            case "cpp" -> handleCpp(containerName, judgeParams.getCode(), input);
            case "java" -> handleJava(containerName, judgeParams.getCode(), input);
            default -> throw new RuntimeException("Wrong language");
        };
        new Thread(() -> {
            execCommand("docker stop " + containerName);
        }).start();
        System.out.println("output from container: " + output);
        return output;
    }

    private String handlePython(String containerName, String code, String input) {
        String codeFileName = containerName + ".py";
        String inputFileName = containerName + ".in";
        makeFile(filesPath + codeFileName, code);
        makeFile(filesPath + inputFileName, input);
        execCommand(scriptsPath + "copyToContainer.sh " + containerName + " " + filesPath + codeFileName);
        execCommand(scriptsPath + "copyToContainer.sh " + containerName + " " + filesPath + inputFileName);
        String output = execCommand("docker exec " + containerName + " ./scripts/python/run.sh " + codeFileName + " " + inputFileName);
        try {
            deleteFile(filesPath + codeFileName);
            deleteFile(filesPath + inputFileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    private String handleCpp(String containerName, String code, String input) {
        String codeFileName = containerName + ".cpp";
        String inputFileName = containerName + ".in";
        makeFile(filesPath + codeFileName, code);
        makeFile(filesPath + inputFileName, input);
        execCommand(scriptsPath + "copyToContainer.sh " + containerName + " " + filesPath + codeFileName);
        execCommand(scriptsPath + "copyToContainer.sh " + containerName + " " + filesPath + inputFileName);
        execCommand("docker exec " + containerName + " ./scripts/cpp/compile.sh " + codeFileName + " main");
        String output = execCommand("docker exec " + containerName + " ./scripts/cpp/run.sh main " + inputFileName);
        try {
            deleteFile(filesPath + codeFileName);
            deleteFile(filesPath + inputFileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    private String handleJava(String containerName, String code, String input) {
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
                return output.toString();
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

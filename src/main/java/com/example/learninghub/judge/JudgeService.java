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
        //        String output = execCommand("docker exec " + containerName + " cat output");
//        new Thread(() -> {
//            execCommand("docker stop " + containerName);
//        }).start();
        return output;
    }

    private String handlePython(String containerName, String code, String input) {
        makeFile(filesPath + "code.py", code);
        makeFile(filesPath + "input.in", input);
        execCommand(scriptsPath + "copyToContainer.sh " + containerName + " " + filesPath + "code.py");
        execCommand(scriptsPath + "copyToContainer.sh " + containerName + " " + filesPath + "input.in");
        System.out.println(code);
        String output = execCommand("docker exec " + containerName + " ./scripts/python/run.sh code.py input.in");
        System.out.println("executed: " + output);
        try {
            deleteFile(filesPath + "code.py");
            deleteFile(filesPath + "input.in");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    private String handleCpp(String containerName, String code, String input) {
        makeFile(filesPath + "code.cpp", code); // UNIKALNA NAZWA
        makeFile(filesPath + "input.in", input);
        execCommand(scriptsPath + "copyToContainer.sh " + containerName + " " + filesPath + "code.cpp");
        execCommand(scriptsPath + "copyToContainer.sh " + containerName + " " + filesPath + "input.in");
        execCommand("docker exec " + containerName + " ./scripts/cpp/compile.sh " + "code.cpp main");
        String output = execCommand("docker exec " + containerName + " ./scripts/cpp/run.sh main input.in");
        try {
            deleteFile(filesPath + "code.cpp");
            deleteFile(filesPath + "input.in");
            deleteFile(filesPath + "main");
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

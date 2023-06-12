package com.example.learninghub.judge;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class JudgeService {

    private final String dockerPath = System.getenv("DOCKER_DIR_PATH");
    private final String scriptsPath = dockerPath + "scripts/";
    private final String filesPath = "/tmp/";
    private String containerName;
    private String output = "";

    public String runCode(JudgeParams judgeParams, Integer submitID, String input, int testCase) {
        try {
            System.out.println("runCode2");
            containerName = judgeParams.getUsername() + "_" + submitID + "_" + testCase;
            execCommand(scriptsPath + "runContainer.sh " + containerName);
            int result = switch (judgeParams.getLanguage()) {
                case "python" -> handlePython(containerName, judgeParams.getCode(), input);
                case "cpp" -> handleCpp(containerName, judgeParams.getCode(), input);
                case "java" -> handleJava(containerName, judgeParams.getCode(), input);
                default -> throw new RuntimeException("Wrong language");
            };
            stopContainer();
            System.out.println("output from container: " + output);
            return result == 1 ? "RTE?*#. " + output : output;
        } catch (Exception e) {
            return "Interrupted";
        }
    }

    public void stopContainer() {
        new Thread(() -> {
            execCommand("docker stop " + containerName);
        }).start();
    }

    private int handlePython(String containerName, String code, String input) {
        String codeFileName = containerName + ".py";
        String inputFileName = containerName + ".in";
        makeFile(filesPath + codeFileName, code);
        makeFile(filesPath + inputFileName, input);
        execCommand(scriptsPath + "copyToContainer.sh " + containerName + " " + filesPath + codeFileName);
        execCommand(scriptsPath + "copyToContainer.sh " + containerName + " " + filesPath + inputFileName);
        execCommand("docker exec " + containerName + " chmod +x ./scripts/python/run.sh");

        output = "";
        int result = execCommand("docker exec " + containerName + " ./scripts/python/run.sh " + codeFileName + " " + inputFileName);
        try {
            deleteFile(filesPath + codeFileName);
            deleteFile(filesPath + inputFileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private int handleCpp(String containerName, String code, String input) {
        String codeFileName = containerName + ".cpp";
        String inputFileName = containerName + ".in";
        makeFile(filesPath + codeFileName, code);
        makeFile(filesPath + inputFileName, input);
        execCommand(scriptsPath + "copyToContainer.sh " + containerName + " " + filesPath + codeFileName);
        execCommand(scriptsPath + "copyToContainer.sh " + containerName + " " + filesPath + inputFileName);
        execCommand("docker exec " + containerName + " chmod +x ./scripts/cpp/compile.sh");
        execCommand("docker exec " + containerName + " chmod +x ./scripts/cpp/run.sh");

        output = "";
        int result = execCommand("docker exec " + containerName + " ./scripts/cpp/compile.sh " + codeFileName + " main");
        if (result == 0) {
            output = "";
            result = execCommand("docker exec " + containerName + " ./scripts/cpp/run.sh main " + inputFileName);
        }
        try {
            deleteFile(filesPath + codeFileName);
            deleteFile(filesPath + inputFileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private int handleJava(String containerName, String code, String input) {
        String codeFileName = containerName + ".java";
        String inputFileName = containerName + ".in";
        makeFile(filesPath + codeFileName, code);
        makeFile(filesPath + inputFileName, input);
        execCommand(scriptsPath + "copyToContainer.sh " + containerName + " " + filesPath + codeFileName);
        execCommand(scriptsPath + "copyToContainer.sh " + containerName + " " + filesPath + inputFileName);
        execCommand("docker exec " + containerName + " chmod +x ./scripts/java/compile.sh");
        execCommand("docker exec " + containerName + " chmod +x ./scripts/java/run.sh");

        output = "";
        int result = execCommand("docker exec " + containerName + " ./scripts/java/compile.sh " + codeFileName);
        if (result == 0) {
            output = "";
            result = execCommand("docker exec " + containerName + " ./scripts/java/run.sh " + containerName + " " + inputFileName);
        }
        try {
            deleteFile(filesPath + codeFileName);
            deleteFile(filesPath + inputFileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private int execCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            try {
                int exitVal = process.waitFor();
                if (exitVal == 0) {
                    System.out.println("Success! " + command);
                    output = sb.toString();
                    return 0;
                } else {
                    System.out.println("Failure! " + command);
                    output = sb.toString();
                    return 1;
                }
            } catch (InterruptedException e) {
                return 2;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
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

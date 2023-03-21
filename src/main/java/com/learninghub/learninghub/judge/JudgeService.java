package com.learninghub.learninghub.judge;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class JudgeService {

    private final String dockerPath = "src/main/java/com/learninghub/learninghub/judge/docker/";
    private final String scriptsPath = dockerPath + "scripts/";

    public void runCode(JudgeParams judgeParams) {
        String containerName = "container" + judgeParams.getTestCase();
//        judgeParams.setCode(getCode("hello.c"));
//        String testIn = getCode("test.in");
//        String testOut = getCode("test.out");
//        makeFile("test.c", judgeParams.getCode());
//        makeFile("test.in", testIn);
//        makeFile("test.out", testOut);
//        execCommand(scriptsPath + "runContainer.sh " + containerName);
//        execCommand("docker cp /tmp/test.c " + containerName + ":/");
//        execCommand("docker cp /tmp/test.in " + containerName + ":/");
//        execCommand("docker cp /tmp/test.out " + containerName + ":/");
//        execCommand("docker exec " + containerName + " gcc /test.c -o main");
//        execCommand("docker exec " + containerName + " bash -c \"/main < test.in > answer\"");
        //docker exec "app_$i" bash -c "echo 'server.url=$server_url' >> /home/app/.app/app.config"
    }

    private void execCommand(String command) {
        try {
            System.out.println(command);
            Process process = Runtime.getRuntime().exec(command);

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
            } else {
                System.out.println("Failure!");
                //TODO: handle error
                System.exit(exitVal);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void makeFile(String filename, String content) {
        try (PrintWriter out = new PrintWriter("/tmp/" + filename)) {
            out.println(content);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // FOR DEBUG
    private String getCode(String file) {
        try {
            return Files.readString(Path.of("/home/michal/Desktop/learning-hub-server/src/main/java/com/learninghub/learninghub/judge/docker/files/" + file), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

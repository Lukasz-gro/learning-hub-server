package com.learninghub.learninghub.judge;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class JudgeService {

    private final String dockerPath = "src/main/java/com/learninghub/learninghub/judge/docker/";
    private final String scriptsPath = dockerPath + "scripts/";

    public void runCode(JudgeParams judgeParams) {
        String containerName = "container" + judgeParams.getTestCase();
        System.out.println(containerName);
        try {
            String content = Files.readString(Path.of("/home/michal/Desktop/learning-hub-server/src/main/java/com/learninghub/learninghub/judge/docker/files/hello.c"), StandardCharsets.UTF_8);
            judgeParams.setCode(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        execCommand(scriptsPath + "runContainer.sh " + containerName);
//        execCommand("docker exec -i container10 sh -c \"echo " + judgeParams.getCode() + " > hello.c\"");
//        execCommand(scriptsPath + "copyToContainer.sh " + containerName + " " + dockerPath + "files");
//        execCommand(scriptsPath + "compileCode.sh " + containerName + " hello.c");
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

}

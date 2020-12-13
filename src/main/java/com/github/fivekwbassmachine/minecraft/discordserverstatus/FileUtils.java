package com.github.fivekwbassmachine.minecraft.discordserverstatus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileUtils {
    public static String readFile(File f) throws IOException {
        if (!f.exists()) {
            f.createNewFile();
            return "";
        }
        Charset charset = StandardCharsets.UTF_8;
        BufferedReader reader = Files.newBufferedReader(f.toPath(), charset);
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) sb.append(line);
        return sb.toString();
    }
    public static void writeFile(File f, String s) throws IOException {
        if (f.exists()) f.createNewFile();
        Charset charset = StandardCharsets.UTF_8;
        BufferedWriter writer = Files.newBufferedWriter(f.toPath(), charset);
        writer.write(s, 0, s.length());
    }
}

package at.ac.tuwien.sepm.groupphase.backend.util;

import com.google.common.io.CharStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class Resources {
    private ResourceLoader resourceLoader;

    @Autowired
    public Resources(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String getImageEncoded(String imgName) {
        try {
            InputStream inputStream = resourceLoader.getResource("classpath:images/" + imgName).getInputStream();
            String encodedString = Base64.getEncoder().encodeToString(inputStream.readAllBytes());
            encodedString = "data:image/" + getFileExtension(imgName) + ";base64," + encodedString;
            return encodedString;
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load Image File.", e);
        } catch (NullPointerException e) {
            throw new RuntimeException("Couldn't load Image File.", e);
        }
    }

    public String getText(String fileName) {
        try {
            InputStream inputStream = resourceLoader.getResource("classpath:text/" + fileName).getInputStream();
            StringBuilder textBuilder = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader
                (inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c = 0;
                while ((c = reader.read()) != -1) {
                    textBuilder.append((char) c);
                }
                String ret = textBuilder.toString();
                return ret;
            }
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load Text File.", e);
        } catch (NullPointerException e) {
            throw new RuntimeException("Couldn't load Text File.", e);
        }
    }

    private String getFileExtension(String imgName) {
        if(!imgName.contains(".")) {
            throw new RuntimeException("Could not retrieve file extension of filename ." + imgName);
        }
        int dotIndex = imgName.indexOf('.');
        String fileExtension = imgName.substring(dotIndex+1);
        return fileExtension;
    }
}

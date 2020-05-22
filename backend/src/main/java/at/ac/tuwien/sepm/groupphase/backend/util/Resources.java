package at.ac.tuwien.sepm.groupphase.backend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();
            String str;
            while((str = reader.readLine())!= null){
                sb.append(str);
            }
            return sb.toString();
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

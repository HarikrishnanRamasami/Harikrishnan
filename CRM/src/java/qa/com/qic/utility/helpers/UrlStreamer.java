/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.utility.helpers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.UUID;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.exceptions.TransactionException;

/**
 * A utility that downloads a file from a URL
 *
 * @author ravindar.singh
 */
public class UrlStreamer {

    private static final Logger logger = LogUtil.getLogger(UrlStreamer.class);

    private String resource;
    private String contentType;
    private String filename;
    private String fileExtension;
    private byte redirect = 0;
    private Credential credentials;

    public String getResource() {
        return resource;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFilename() {
        return filename;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public UrlStreamer(String resource) {
        this.resource = resource;
    }

    public Credential getCredentials() {
        return credentials;
    }

    public void setCredentials(Credential credentials) {
        this.credentials = credentials;
    }

    public StreamingOutput resolve() throws TransactionException {
        StreamingOutput stream = null;
        try {
            logger.info("URL : " + getResource());
            InputStream in = process();
            stream = (OutputStream os) -> {
                byte[] buff = new byte[5242880];
                int bytesRead;
                try {
                    while (-1 != (bytesRead = in.read(buff, 0, buff.length))) {
                        os.write(buff, 0, bytesRead);
                    }
                } catch (Exception e) {
                    throw new WebApplicationException(e);
                } finally {
                    if (os != null) {
                        os.close();
                    }
                    in.close();
                }
            };
        } catch (Exception e) {
            logger.error("", e);
            throw new TransactionException("Unable to stream the file");
        } finally {
        }
        return stream;
    }

    public void resolveAndStore(String destPath, String fileName) throws TransactionException {
        resolveAndStore(destPath, fileName, null);
    }

    public void resolveAndStore(String destPath, String fileName, String fileExtn) throws TransactionException {
        try {
            InputStream in = process();
            File f = new File(destPath);
            if (!f.exists()) {
                f.mkdirs();
            }
            if(StringUtils.isNotBlank(fileExtn)) {
                fileExtension = fileExtn;
            }
            if(StringUtils.isNotBlank(fileName)) {
                filename = StringUtils.isNotBlank(fileExtension) ? fileName + "." + fileExtension : fileName;
            } else if(StringUtils.isNotBlank(getFilename())) {
                filename += "." + fileExtension;
            } else {
                filename = UUID.randomUUID().toString() + "." + fileExtension;
            }
            File destFile = new File(destPath, getFilename());
            logger.info("File Path: {}", destFile.getPath());
            try {
                //FileUtils.copyInputStreamToFile(in, destFile);
                Files.copy(in, destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                logger.error("", ex);
            } finally {
                IOUtils.closeQuietly(in);
            }
        } catch (Exception e) {
            logger.error("", e);
            throw new TransactionException("Unable to store the file");
        } finally {
        }
    }

    private InputStream process() throws MalformedURLException, IOException {
        InputStream in = null;
        logger.info("URL : " + getResource());
        URL url = new URL(getResource());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (credentials != null) {
            String encoded = Base64.getEncoder().encodeToString((credentials.username + ":" + credentials.password).getBytes(StandardCharsets.UTF_8));
            connection.setRequestProperty("Authorization", "Basic " + encoded);
        }
        connection.setRequestMethod("GET");
        connection.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
        connection.addRequestProperty("Connection", "keep-alive");
        connection.addRequestProperty("Accept", "*/*");
        connection.addRequestProperty("User-Agent", "Mozilla/5.0");
        connection.connect();
        contentType = connection.getContentType();
        if (null != contentType) {
            if (contentType.contains(";")) {
                contentType = contentType.split(";")[0];
            }
        }
        String disposition = connection.getHeaderField("Content-Disposition"); // "attachment; filename=ravindar.jpg"
        if (disposition != null && disposition.contains("=")) {
            filename = disposition.split("=")[1]; //getting value after '='
            fileExtension = FilenameUtils.getExtension(filename);
        } else {
            // Random generated file name
            switch (contentType) {
                case "image/jpeg":
                    fileExtension = "jpeg";
                    break;
                case "image/png":
                    fileExtension = "png";
                    break;
                case "application/pdf":
                    fileExtension = "pdf";
                    break;
                case "application/vnd.ms-excel":
                    fileExtension = "xls";
                    break;
                case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                    fileExtension = "xlsx";
                    break;
                case "application/msword":
                    fileExtension = "doc";
                    break;
                case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                    fileExtension = "docx";
                    break;
                case "application/vnd.ms-powerpoint":
                    fileExtension = "ppt";
                    break;
                case "application/vnd.openxmlformats-officedocument.presentationml.presentation":
                    fileExtension = "pptx";
                    break;
                case "audio/ogg":
                    fileExtension = "ogg";
                    break;
                case "audio/mpeg":
                    fileExtension = "mp3";
                    break;
                case "video/mp4":
                    fileExtension = "mp4";
                    break;
            }
        }
        int responseCode = connection.getResponseCode();
        logger.debug("Response code of the {} is {}", new Object[]{getResource(), responseCode});
        if (responseCode == HttpURLConnection.HTTP_OK) {
            logger.info("Connected to {}, , Content Type: {}", new Object[]{getResource(), contentType});
            in = connection.getInputStream();
            if (in == null) {
                logger.fatal("Unable to load the URL. URL: {}", new Object[]{getResource()});
                throw new IOException("Unable to load the URL. URL: " + getResource());
            }
        } else if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP
                || responseCode == HttpURLConnection.HTTP_MOVED_PERM
                || responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
            this.resource = connection.getHeaderField("Location");
            logger.warn("Redirecting to {}", new Object[]{getResource()});
            if (redirect == 3) {
                logger.fatal("Too many redirects. URL: {}", new Object[]{getResource()});
                throw new IOException("Too many redirects. URL: " + getResource());
            }
            ++redirect;
            return process();
        }
        return in;
    }

    public class Credential {

        private final String username;
        private final String password;

        public Credential(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}

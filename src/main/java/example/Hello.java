package example;

import com.amazonaws.services.lambda.runtime.Context; 
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import uk.me.mjt.broadside.TestResult;
import uk.me.mjt.broadside.TestSettings;

public class Hello implements RequestHandler<TestSettings, TestResult> {
    
    public TestResult handleRequest(TestSettings ts, Context context) {
        long testStartTime = System.currentTimeMillis();
        
        StringBuilder sb = new StringBuilder();
        //sb.append("Hello class: ").append(Hello.class.getResource("/example/Hello.class")).append("\n");
        
        Path p = null;
        long s3DownloadCompleteTime = -1;
        try {
            p = Files.createTempDirectory("broadsidebundle");
            InputStream is = getFromS3Url(ts.getTestCodeBundle());
            unzipTo(is, p.toFile());
            //sb.append("Unzipped OK to ").append(p.toString()).append("\n");
            s3DownloadCompleteTime = System.currentTimeMillis();
            
            File reportsFolder = p.resolve("target/surefire-reports").toFile();
            reportsFolder.mkdirs();
            
            Properties props = new Properties();
            props.load(new StringReader(ts.getSurefirePropertiesFile()));
            props.setProperty("reportsDirectory", reportsFolder.getAbsolutePath());
            
            File settingsFile = p.resolve("surefireRun.properties").toFile();
            props.store(new FileOutputStream(settingsFile), "asdf");
            // Log back settings:
            /*StringWriter sw = new StringWriter();
            props.store(sw, "broadside");
            sb.append("Using surefire settings: ").append(sw.toString()).append("\n");*/
            
            //sb.append("ps fax:").append(execReturn("ps -fax")).append("\n\n");
            
            ArrayList<String> javaCommand = new ArrayList();
            javaCommand.add("java");
            javaCommand.addAll(ts.getArglineParameters());
            javaCommand.add("-classpath");
            javaCommand.add(p.toString() + "/.:" + p.toString() + "/*");
            javaCommand.add("org.apache.maven.surefire.booter.ForkedBooter");
            javaCommand.add(settingsFile.toString());
            
            sb.append("Running command: ").append(javaCommand).append("\n");
            sb.append("Command output: ").append(execReturn(javaCommand)).append("\n");
        
        } catch (Throwable t) {
            StringWriter sw = new StringWriter(1024);
            t.printStackTrace(new PrintWriter(sw));
            sb.append("Exception: ").append(sw).append("\n");
        } finally {
            if (p!=null && p.toFile().exists()) {
                p.toFile().delete();
            }
        }
        
        TestResult tr = new TestResult();
        tr.setSurefireBooterStdout(sb.toString());
        tr.setAgentStartTimeUnixMillis(testStartTime);
        tr.setSourceBundleDownloadCompleteMillis(s3DownloadCompleteTime);
        tr.setAwsRequestReference(context.getAwsRequestId());
        tr.setTestEndTimeUnixMillis(System.currentTimeMillis());
        return tr;
    }
    
    private static void unzipTo(InputStream is, File outputFolder) throws IOException {
        if (!outputFolder.isDirectory()) throw new IllegalArgumentException("Target folder must exist.");
        
        //get the zip file content
    	ZipInputStream zis = null;
        try {
            zis = new ZipInputStream(is);
            
            ZipEntry ze = zis.getNextEntry();
            
            while (ze != null) {
                File destination = new File(outputFolder.getAbsolutePath()+File.separator+ze.getName());
                if (!destination.exists()) {
                    destination.getParentFile().mkdirs();
                    Files.copy(zis, destination.toPath());
                }
                ze = zis.getNextEntry();
            }
            
        } finally {
            if (zis != null) {
                zis.close();
            }
        }
    }
    
    static String execReturn(List<String> command) {
        try {
            ProcessBuilder pb = new ProcessBuilder(command.toArray(new String[0]));
            pb.redirectErrorStream(true);
            Process p = pb.start();
            InputStream is = p.getInputStream();
            String result = convertStreamToString(is).trim();
            return result;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    static String convertStreamToString(java.io.InputStream is) throws IOException {
        // From https://stackoverflow.com/a/5445161/1367431
        try {
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        } finally {
            is.close();
        }
    }
    
    public InputStream getFromS3Url(String url) {
        Matcher m = Pattern.compile("^s3://([^/]+)/(.+)$").matcher(url);
        if (!m.matches()) throw new IllegalArgumentException("Invalid S3 url, "+url);
        
        AmazonS3 s3Client = new AmazonS3Client();
        S3Object object = s3Client.getObject(
                new GetObjectRequest(m.group(1), m.group(2)));
        InputStream objectData = object.getObjectContent();
        return objectData;
    }
    
}
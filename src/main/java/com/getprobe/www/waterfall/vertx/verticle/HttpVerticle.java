package com.getprobe.www.scope.vertx.verticle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

public class HttpVerticle extends Verticle {
    private final String WEB_CONF_NAME = "webModConf.json";
    private final String WEB_MODULE_NAME = "io.vertx~mod-web-server~2.0.0-final";
    private final String JSON_FIELDNAME_PORT = "port";
    private final int DEFAULT_PORT = 80;

    @Override
    public void start(){
        
        JsonObject conf = null;
        try {
            // print zip status
            System.out.println("Zip path: " + getPath("zip"));
            String confString = getConf(getPath("zip"));
            // print conf
            System.out.println("Conf: " + confString);
            
            conf = new JsonObject(confString);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Build Path: " + getPath("build"));
            // print build path
            //TODO
        }
        
        // Deploy worker verticle
        container.deployModule(WEB_MODULE_NAME , conf);


        int port = getPort(conf);

        // GET: the status of the processors
        HttpServer server = vertx.createHttpServer();
        server.requestHandler(new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest request) {
                request.response();
                // get master or worker id
                //TODO
                // return the status as json type
                //TODO
            }
        }).listen(port);

    }
    
    /**
     * @return json type string 
     * @throws IOException 
     * @throws ZipException 
     * */
    private String getConf(String dir) throws ZipException, IOException{
        String conf = "";
        
        //find 
        ZipFile zipFile = new ZipFile(new File(dir));
        ZipInputStream zipInput = new ZipInputStream(new FileInputStream( dir ));
        ZipEntry zipEntry;
        boolean exists = false;
        
        try {
            System.out.println(" Now Searching " + WEB_CONF_NAME + "...");

            while( (zipEntry = zipInput.getNextEntry()) != null){
                if(!zipEntry.isDirectory() 
                        || !zipEntry.getName().contains(".class")){
                    System.out.println("  "+ zipEntry.getName() );
                }
                String entryPath = zipEntry.getName();
                if( entryPath.contains(WEB_CONF_NAME)){
                    exists = true;
                    
                    InputStream input = zipFile.getInputStream(zipEntry);
                    BufferedReader br = new BufferedReader(new InputStreamReader(input, "UTF-8"));
                    String line = null;
                    while((line = br.readLine())!=null){
                        conf = conf + line;
                    }                    
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        if(!exists){
            throw new FileNotFoundException(WEB_CONF_NAME + " does not found.");
        }
        
        zipFile.close();
        zipInput.close();
        
        return conf;
    }
        
    private String getPath(String type){
        final String BUILD_PATH = System.getProperty("user.dir") + "/";
        String ZIP_NAME = "scope-0.0.1-SNAPSHOT.zip";
        
        if(type.equals("build")){
            return BUILD_PATH;
        }else if(type.equals("module") || type.equals("zip")){
            return BUILD_PATH + ZIP_NAME;
        }
        return null;
    }

    private int getPort(JsonObject webModConf){
        //TODO
        int port;

        if((webModConf == null) ||
            (port = webModConf.getInteger(JSON_FIELDNAME_PORT).intValue()) == 0 ){
            port = DEFAULT_PORT;
        }

        return port;
    }
    
}

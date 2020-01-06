package uk.ac.ucl.jsh;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * searches for files with matching names
 */
class Find implements AppCase {
    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException {

        if (appArgs.size() < 2) {
            throw new RuntimeException("find: missing arguments");
        }
    
        // Without pathname
        if (appArgs.size() == 2) {
            if (!appArgs.get(0).equals("-name")) {
                throw new RuntimeException("find: invalid arguments");
            }


            File currDir = new File(currentDirectory);
            String pattern = appArgs.get(1);
            
            Globbing glob = new Globbing();
            glob.writeMatchedFiles(currDir, currDir, pattern, output);

        } 
        
        // With specified pathname
        else if (appArgs.size() == 3) {
            if (!appArgs.get(1).equals("-name")) {
                throw new RuntimeException("find: invalid arguments");
            }
            File currDir = new File(currentDirectory + "/" + appArgs.get(0));
            String pattern = appArgs.get(2);

            Globbing glob = new Globbing();
            glob.writeMatchedFiles(currDir, currDir, pattern, output);
        }

        
        
        
        else{
            throw new RuntimeException("find: too many arguments");
        }
    }



}
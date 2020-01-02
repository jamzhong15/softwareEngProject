package uk.ac.ucl.jsh;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;

public class Globbing
{
    ArrayList<String> matchedFiles = new ArrayList<>();

    public ArrayList<String> expand(String directory, String argument)
    {
        String pattern = directory + "/" + argument;
        File dir = new File(directory);

        findFiles(dir, dir, pattern);

        //Add the original argument if none matched
        if(matchedFiles.size() == 0)
        {
            matchedFiles.add(argument);
        }

        return matchedFiles;
    }
    
    public void findFiles(File baseDir, File dir, String pattern) {
        // Create a matcher for glob patterns
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        
        if (matcher.matches(dir.toPath())) 
        {
            // Write the relative pathname of the file if it matches the pattern
            Path relative = baseDir.toPath().relativize(dir.toPath());
            matchedFiles.add(relative.toString());
        }

        if (dir.isDirectory()) {
            //Don't include hidden files
            File[] files = dir.listFiles();

            // Recursively search through the subdirectories
            for (File file : files) {
                findFiles(baseDir, file, pattern);
            }

        }
    }

    // Used for find command
    public void searchAllFiles(File baseDirectory, File currDirectory, String pattern, OutputStream output) throws IOException{
        // Create a matcher for glob patterns
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        if (currDirectory.isDirectory()) {
            File[] subDir = currDirectory.listFiles();

            // Recursively search through the subdirectories
            for (File file : subDir) {
                searchAllFiles(baseDirectory, file, pattern, output);
            }
        } else if (matcher.matches(currDirectory.toPath().getFileName())) {
            // Write the relative pathname of the file if it matches the pattern
            
                OutputStreamWriter writer = new OutputStreamWriter(output);

                String base = baseDirectory.getCanonicalPath();
                String current = currDirectory.getCanonicalPath();
                String relative = current.substring(base.length());

                writer.write(relative + "\n");
                writer.flush();
        }
    }
}
package uk.ac.ucl.jsh;

class AppFactory {
    public static AppCase createApp(String appName) 
    {
        switch (appName) 
        {
        case "cd":
            return new cd();
        case "pwd":
            return new pwd();
        case "ls":
            return new ls();
        case "cat":
            return new cat();
        case "echo":
            return new echo();
        case "head":
            return new head();
        case "tail":
            return new tail();
        case "grep":
            return new grep();
        case "sed":
            return new sed();
        case "find":
            return new find();
        case "wc":
            return new wc();
        default:
            throw new RuntimeException(appName + ": unknown application");
        }
        
    }
}
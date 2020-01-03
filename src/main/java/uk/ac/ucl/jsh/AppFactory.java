package uk.ac.ucl.jsh;

class AppFactory {
    public static AppCase createApp(String appName) 
    {
        switch (appName) 
        {
        case "cd":
            return new Cd();
        case "pwd":
            return new Pwd();
        case "ls":
            return new Ls();
        case "cat":
            return new Cat();
        case "echo":
            return new Echo();
        case "head":
            return new Head();
        case "tail":
            return new Tail();
        case "grep":
            return new Grep();
        case "sed":
            return new Sed();
        case "find":
            return new Find();
        case "wc":
            return new Wc();
        default:
            throw new RuntimeException(appName + ": unknown application");
        }
        
    }
}
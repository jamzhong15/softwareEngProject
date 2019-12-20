package uk.ac.ucl.jsh;

class AppFactory {
    public static AppCase createApp(String appName) {
        if (appName.equals("cd")) {
            return new cd();
        }
        else if (appName.equals("pwd")) {
            return new pwd();
        }
        else if (appName.equals("ls")) {
            return new ls();
        }
        else if (appName.equals("cat")) {
            return new cat();
        }
        else if (appName.equals("echo")) {
            return new echo();
        }
        else if (appName.equals("head")) {
            return new head();
        }
        else if (appName.equals("tail")) {
            return new tail();
        }
        else if (appName.equals("grep")) {
            return new grep();
        }
        else if (appName.equals("find")) {
            return new find();
        }
        else {
            throw new RuntimeException(appName + ": unknown application");
        }
    }
}
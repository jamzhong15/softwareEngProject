package uk.ac.ucl.jsh;

import java.util.ArrayList;

public class CmdExtractor
{
    String cmdline;

    public CmdExtractor(String cmdline){
        this.cmdline = cmdline;
    }

    public ArrayList<String> readInput()
    {
        ArrayList<String> rawCommands = new ArrayList<String>();

    

        int closingPairIndex, prevDelimiterIndex = 0, splitIndex = 0;
        

		for (splitIndex = 0; splitIndex < cmdline.length(); splitIndex++) {
			char ch = cmdline.charAt(splitIndex);
			if (ch == ';') {
                //extraction of first command, from inputs that uses ; as a delimiter
				String command = cmdline.substring(prevDelimiterIndex, splitIndex).trim();
				rawCommands.add(command);
				prevDelimiterIndex = splitIndex + 1;
            } 
            else if (ch == '\'' || ch == '\"') 
            {
                // indexof(arg1, arg2)
                // arg1 = character you want to find
                // arg2 = start searching after this indexed position
				closingPairIndex = cmdline.indexOf(ch, splitIndex + 1);
				if (closingPairIndex == -1) {
					continue;
				} else {
					splitIndex = closingPairIndex;
				}
			}
        }
        


		if (!cmdline.isEmpty() && prevDelimiterIndex != splitIndex) {
			String command = cmdline.substring(prevDelimiterIndex).trim();
			if (!command.isEmpty()) {
				rawCommands.add(command);
			}
        }
        
        return rawCommands;
    }
    
}
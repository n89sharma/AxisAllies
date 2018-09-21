package axisallies.inter;

import picocli.CommandLine;

import java.io.File;

public class CommandLineInterface {
    public CommandLineInterface() {
        String[] args = {"-v", "InputFile1"};
        Example example = CommandLine.populateCommand(new Example(), args);
    }
    private class Example {
        @CommandLine.Option(names = {"-v", "--verbose"}, description  = "Be verbose.")
        private boolean verbose = false;

        @CommandLine.Parameters(arity = "1..*", paramLabel = "FILE", description = "File(s) to process.")
        private File[] inputFiles;
    }
}

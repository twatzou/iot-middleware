package at.ac.tuwien.infosys.iotclient.utils;


import at.ac.tuwien.infosys.iotclient.model.CmdOptions;
import org.apache.commons.cli.*;

public class ArgumentHandler {

    private Options options;
    private CommandLineParser parser;
    private CommandLine cmdLine;
    private HelpFormatter formatter;

    public ArgumentHandler() {
        options = new Options();
        parser = new DefaultParser();
        formatter = new HelpFormatter();
    }

    private void initOptions() {
        options.addRequiredOption("f", "filename", true, "Name of the csv log file. Without suffix.");
        options.addRequiredOption("d", "duration", true, "Duration (long value in ms) how long the client listens to different channels");
        options.addOption("t", "topics", true, "Amount of autogenerated topics of the Virtual Driver");
        options.addOption("i", "id", true, "Id which is used by the Virtual Driver");
    }

    public CmdOptions parse(String[] args) {

        initOptions();
        CmdOptions cmdOptions = new CmdOptions();

        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException e) {
            formatter.printHelp("iotclient", options);
            throw new IllegalArgumentException();
        }

        cmdOptions.setFileName(cmdLine.getOptionValue("filename"));

        String d = cmdLine.getOptionValue("duration");
        try {
            long duration = Long.parseLong(d);
            cmdOptions.setDuration(duration);
        } catch (NumberFormatException e) {

            formatter.printHelp("iotclient", options);
            throw new IllegalArgumentException();
        }

        if(cmdLine.hasOption("i")){
            cmdOptions.setId(cmdLine.getOptionValue("i"));
        }

        if(cmdLine.hasOption("t")){
            try {
                int amount = Integer.parseInt(cmdLine.getOptionValue("t"));
                cmdOptions.setAmount(amount);
            } catch (NumberFormatException e) {
                formatter.printHelp("iotclient", options);
                throw new IllegalArgumentException();
            }
        }

        return cmdOptions;
    }

}

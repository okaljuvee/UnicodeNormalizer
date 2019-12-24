package info.kaljuvee;

import info.kaljuvee.model.UserRecord;
import info.kaljuvee.processor.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Normalizer {

    private final static Logger log = Logger.getLogger(Normalizer.class.getName());

    /**
     * Main program for the normalizer.
     *
     * @param args Program arguments containing the input file name and output directory.  Example:
     *
     * > java Normalizer sample.csv output.csv
     *
     */
    public static void main(String... args) {
        if(args.length != 2) {
            log.info("Please provide arguments for input and output file names. Exiting.");
            System.exit(-1);
        }
        String inputFile = args[0];
        String outputFile = args[1];

        List<FieldProcessor>  processors = new ArrayList<>(Arrays.asList(
                new TimestampProcessor("Timestamp"),
                new AddressProcessor("Address"),
                new ZipCodeProcessor("ZIP"),
                new NameProcessor("FullName"),
                new FooDurationProcessor("FooDuration"),
                new BarDurationProcessor("BarDuration"),
                new TotalDurationProcessor("TotalDuration"),
                new NotesProcessor("Notes")));

        new Normalizer(inputFile, outputFile, processors).normalize();
    }

    private String inputFile;
    private String outputFile;
    private List<String> headers;
    private List<UserRecord> records;
    private List<FieldProcessor> processors;

    public Normalizer(String inputFile, String outputFile, List<FieldProcessor> processors) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.processors = processors;
        headers = processors.stream().map(FieldProcessor::getKey).collect(Collectors.toList());
        records = new LinkedList<>();
    }

    public void normalize() {
        parseInput();
        writeOutput();
    }

    /**
     * Parses input file and stores to internal collection of records.
     */
    public void parseInput() {
        parseInput(CSVFormat.DEFAULT);
    }

    /**
     * Parses input file and stores to internal collection of records.
     *
     * @param format Specifies the input file format.
     */
    public void parseInput(CSVFormat format) {
        Iterable<CSVRecord> csv = null;

        try {
            csv = format.withHeader(headers.stream().toArray(String[]::new)).withFirstRecordAsHeader().parse(
                    new BufferedReader(
                            new InputStreamReader(
                                    // $Assumption 1: the entire CSV is in the UTF-8 character set
                                    new FileInputStream(inputFile), StandardCharsets.UTF_8)));
        } catch(FileNotFoundException e) {
            log.info(String.format("Input file %s not found", inputFile));
            e.printStackTrace();
            return;
        } catch(IOException e) {
            log.info("General IO exception occurred while reading the input file");
            e.printStackTrace();
            return;
        }

        for(CSVRecord csvRecord : csv) {
            UserRecord userRecord = new UserRecord();

            for(FieldProcessor processor : processors) {
                try {
                    processor.parseInto(csvRecord.get(processor.getKey()), userRecord);
                } catch (Exception e) {
                    log.severe("Unable to parse the record, dropping: " + csvRecord + " with exception: " + e.getMessage());
                }
            }
            records.add(userRecord);
        }

        log.info("Parsed " + records.size() + " records");
    }

    private void writeOutput() {
        PrintWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");

        try {
            fileWriter = new PrintWriter(outputFile, StandardCharsets.UTF_8.toString());
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
            csvFilePrinter.printRecord(headers);

            for(UserRecord record : records) {
                Iterator<FieldProcessor> processorIterable = processors.iterator();
                List<String> line = new LinkedList<>();

                while(processorIterable.hasNext()) {
                    line.add(processorIterable.next().normalize(record));
                }
                csvFilePrinter.printRecord(line);
            }
        } catch (Exception e) {
            log.info("Error while while writing the CSV output");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
                csvFilePrinter.close();
            } catch (IOException e) {
                log.info("Error while closing IO resources");
                e.printStackTrace();
            }
        }
        log.info("Completed output: " + outputFile);
    }
}

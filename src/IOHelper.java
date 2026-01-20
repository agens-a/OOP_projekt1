import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.util.Collections;

public class IOHelper {
    static FileContent readFile(String filename, Logger logger) throws IOException {
        String path="inputData/";

        BufferedReader br = new BufferedReader(new FileReader(path+filename));
        String line;
        int noOfInvalidRecords =0;
        ArrayList<Sensor> sensors = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            try{
                line = line.trim();
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    double value = Double.parseDouble(parts[0]);
                    String uuid = parts[1].replace("id:", "").trim();
                    String sensorName = parts[2];
                    Readout readout = new ReadoutWithUuid(value, uuid);
                    addReadoutToSensor(sensors, sensorName, readout);
                }else if (parts.length == 2) {
                    double value = Double.parseDouble(parts[0].trim());
                    String uuid = parts[1].replace("id:", "").trim();
                    addReadoutToSensor(sensors,"<N/A>",new ReadoutWithUuid(value, uuid));
                }else if (parts.length == 1) {
                    double value = Double.parseDouble(line);
                    addReadoutToSensor(sensors,"<N/A>",new Readout(value));
                }
            } catch (NumberFormatException e){
                noOfInvalidRecords+=1;
                logger.log(Logger.Level.ERROR, "Faulty record in " + filename + ": " + line  );
            }
        }
        br.close();
        return new FileContent(sensors,noOfInvalidRecords,filename);
    }

    private static void addReadoutToSensor(ArrayList<Sensor> sensorsList, String sensorName, Readout readout) {
        for (Sensor s : sensorsList) {
            if (s.getName().equals(sensorName)) {
                s.addReadout(readout);
                return;
            }
        }
        Sensor newSensor = new Sensor(sensorName);
        sensorsList.add(newSensor);
        newSensor.addReadout(readout);

    }

    static String getOutputInfo(FileContent fContent, String title, Logger logger){
        int noOfInvalidRecords=fContent.getNoOfInvalidRecords(logger);
        String filename=fContent.getFileName();

        String separator = "\n-~-~-~-~-~-~-~-~-~-~-~-~-~-~-\n";
        String small_separator = "\n~-~-~-~\n";

        String str = title +
                "\nAnna G" + separator +
                "Data filename: " + filename;

        ArrayList<Sensor> sensors = fContent.getSensors();
        Collections.sort(sensors);

        for (Sensor sensor: sensors){
            ArrayList<Readout> central = sensor.getCentralElements();
            Readout max = sensor.getMax();
            Readout min = sensor.getMin();

            for (Readout r : central) {
                logger.log(Logger.Level.CENTRAL_ELEM,
                        "Central element for sensor [" + sensor.getName()+"]: "  + r);
            }
            logger.log(Logger.Level.MAX_ELEM,
                    "Max. element for sensor [" + sensor.getName() + "]: " + max);
            logger.log(Logger.Level.MIN_ELEM,
                    "Min. element for sensor [" + sensor.getName() + "]: " + min);

            str +=  small_separator+
                    "Sensor name: "+ sensor.getName() +
                    "\nLength of the series: " + sensor.getLengthOfData() +
                    "\nMax value: " + sensor.getMax().toString() +
                    "\nMin value: "+ sensor.getMin().toString() +
                    String.format("\nMean value: %.3f", sensor.getMean()) +
                    "\nMedian " + sensor.getMedian().toString()  +
                    "\nNumber of central elements: " + sensor.noOfCentralElements(logger);
        }
        if(noOfInvalidRecords==0) {
            str += separator;
            return str;
        }
        else{
            str=str+small_separator+"Number of invalid records: " + noOfInvalidRecords + separator;
            return str;
        }
    }

    static void processOneFile(String filename, String logFilename, String title) throws IOException{
        Logger logger = new LoggerFile(logFilename);
        logger.log(Logger.Level.INFO, "Start processing file: " + filename);
        FileContent fContent = IOHelper.readFile(filename, logger);
        String output = getOutputInfo(fContent, title, logger);
        try (PrintWriter out = new PrintWriter("outputData/out" + filename.replace("data", "") + ".txt")) {
            out.print(output);
        }
        logger.log(Logger.Level.INFO,
                "Finished processing file: " + filename);
        logger.flush();
    }
}
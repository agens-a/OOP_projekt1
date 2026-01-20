import java.util.ArrayList;
import java.util.Collections;

public class Sensor implements Comparable<Sensor> {
    private String name;
    private ArrayList<Readout> data = new ArrayList<>();

    public Sensor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addReadout(Readout readout) {
        data.add(readout);
    }

    public Readout getMax() {
        Collections.sort(data);
        return data.getLast();
    }

    public Readout getMin() {
        Collections.sort(data);
        return data.getFirst();
    }

    public double getMean() {
        double sum = 0;
        for (Readout datum : data) {
            sum += datum.getValue();
        }
        return sum / data.size();
    }

    public MedianWrapper getMedian() {
        Collections.sort(data);
        int n = data.size();
        if (n % 2 == 0) {
            return new MedianWrapper(data.get(n / 2 - 1), data.get(n / 2));
        } else {
            return new MedianWrapper(data.get(n / 2));
        }
    }

    public int noOfCentralElements(Logger logger) {
        double mean = getMean();
        int numberOf = 0;
        double epsilon = (getMax().getValue() - getMin().getValue()) / 100;
        for (Readout datum : data) {
            if (datum.getValue() - mean < epsilon && datum.getValue() - mean > -epsilon) {
                numberOf += 1;
            }
        }
        return numberOf;
    }

    public ArrayList<Readout> getCentralElements() {
        ArrayList<Readout> result = new ArrayList<>();
        double mean = getMean();
        double epsilon = (getMax().getValue() - getMin().getValue()) / 100;
        for (Readout datum : data) {
            if (Math.abs(datum.getValue() - mean) < epsilon) {
                result.add(datum);
            }
        }
        return result;
    }

    public int getLengthOfData() {
        return data.size();
    }

    @Override
    public int compareTo(Sensor other) {
        return this.name.compareTo(other.getName());
    }
}

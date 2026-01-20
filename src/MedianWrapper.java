import java.util.ArrayList;
public class MedianWrapper {
    private ArrayList<Readout> medianElems = new ArrayList<>();
    private double medianVal;

    //the case of the odd length
    public MedianWrapper(Readout elem) {
        medianElems.add(elem);
        medianVal=elem.getValue();
    }
    //the case of the even length
    public MedianWrapper(Readout elem1, Readout elem2) {
        if (elem1.getValue() < elem2.getValue()) { //sort the elements
            medianElems.add(elem1);
            medianElems.add(elem2);
        } else {
            medianElems.add(elem2);
            medianElems.add(elem1);
        }
        medianVal=(elem1.getValue()+elem2.getValue())/2;
    }
    @Override
    public String toString(){
        String medianStr =String.format("%.3f source:  ", medianVal);
        medianStr += (medianElems.size() > 1) ?
                medianElems.get(0)+"::"+medianElems.get(1):
                medianElems.get(0);
        return medianStr;
    }
}
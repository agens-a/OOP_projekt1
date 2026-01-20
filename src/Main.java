import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException{
        // task I.1
        IOHelper.processOneFile("data_a1.txt","data_a.log","Task I.1");
        // task I.2
        IOHelper.processOneFile("data_b1.txt","data_b.log","Task I.2");
        // task II.2
        IOHelper.processOneFile("data_c1.txt","data_c.log","Task II.2");
        // task III.2
        IOHelper.processOneFile("data_d1.txt","data_d.log","Task III.2");
        // task III.4
        IOHelper.processOneFile("data_e1.txt","data_e.log","Task III.4");
    }
}
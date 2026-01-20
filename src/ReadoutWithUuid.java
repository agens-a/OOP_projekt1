public class ReadoutWithUuid extends Readout {
    private String uuid;
    public ReadoutWithUuid(double value, String uuid){
        super(value);
        this.uuid=uuid;
    }

    @Override
    public String toString() {
        return super.toString()+" ["+uuid+"]";
    }
}

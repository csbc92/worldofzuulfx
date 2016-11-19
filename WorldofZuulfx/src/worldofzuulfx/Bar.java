package worldofzuulfx;

import java.util.ArrayList;
import worldofzuulfx.Interfaces.BarValueListener;

public class Bar {

    private int max;
    private int min;
    private int currentValue;
    private ArrayList<BarValueListener> listeners;
    
    public Bar(int mini, int maxi, int curentValue) {
        this.min = mini;
        this.max = maxi;
        this.currentValue = curentValue;
        this.listeners = new ArrayList<>();
    }
    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public void setValue(int value) {
        this.currentValue = value;
//        onBarValueChanged();
    }

    public int getValue() {
        return currentValue;
    }

    private void onBarValueChanged(){
        for (BarValueListener listener: listeners) {
            listener.barValueChanged(this);
        }
    }
    
    public void addBarValueListener(BarValueListener listener){
        this.listeners.add(listener);
    }

}

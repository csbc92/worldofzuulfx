package worldofzuulfx;

import java.util.ArrayList;
import worldofzuulfx.Interfaces.BarValueListener;

public class Bar {

    private int max;
    private int min;
    private int currentValue;
    private ArrayList<BarValueListener> listeners;

    public Bar(int min, int max) {
        this.min = min;
        this.max = max;
        this.currentValue = 0;
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

        notifyBarValueChanged();
    }

    /**
     * Increase the player's energi-level by adding x amount of energy to the
     * current energy-level.
     *
     * @param energyAmount
     */
    public void increaseEnergy(int amount) {
        if (amount > 0) {
            int currentVal = getValue();
            int newVal = currentVal + amount;

            // Set the energy to max if the new value would otherwise
            // be higher than what the Bar allows.
            if (newVal > this.getMax()) {
                this.setValue(this.getMax());
            } else {
                this.setValue(newVal);
            }
        }
    }

    public int getValue() {
        return currentValue;
    }

    private void notifyBarValueChanged() {
        for (BarValueListener listener : listeners) {
            listener.barValueChanged(this);
        }
    }

    public void addBarValueListener(BarValueListener listener) {
        this.listeners.add(listener);
    }

}

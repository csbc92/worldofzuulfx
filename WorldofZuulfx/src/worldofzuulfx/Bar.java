package worldofzuulfx;

import java.util.ArrayList;
import worldofzuulfx.Interfaces.BarValueListener;

public class Bar {

    private int max;
    private int min;
    private int currentValue;
    private ArrayList<BarValueListener> listeners;

    /**
     * Create an instance of the Bar class, which contains min, max and current value.
     * @param min The bar's minimum value
     * @param max The bar's maximum value
     */
    public Bar(int min, int max) {
        this.min = min;
        this.max = max;
        this.currentValue = 0;
        this.listeners = new ArrayList<>();
    }
  
    /**
     *
     * @return The bar's minimum value
     */
    public int getMin() {
        return min;
    }

    /**
     *
     * @return The bar's maximum value
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the current value of the bar and notifies all BarValueListeners.
     * @param value The new current value of the bar.
     */
    public void setValue(int value) {
        this.currentValue = value;
        notifyBarValueChanged();
    }

    /**
     * Increase the player's energy-level by adding x amount of energy to the
     * current energy-level.
     *
     * @param amount The amount of energy to be added.
     */
    public void increaseEnergy(int amount) {
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

    /**
     *
     * @return The current value of the bar
     */
    public int getValue() {
        return currentValue;
    }

    private void notifyBarValueChanged() {
        for (BarValueListener listener : listeners) {
            listener.barValueChanged(this);
        }
    }

    /**
     * Adds a given listener to the bar's list of listeners.
     * @param listener
     */
    public void addBarValueListener(BarValueListener listener) {
        this.listeners.add(listener);
    }

}

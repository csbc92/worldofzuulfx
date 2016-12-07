
package worldofzuulfx.Interfaces;
import worldofzuulfx.Bar;

/**
 * An interface which gives the functionality to listen for any changes
 * in the bar value. barValuChanged describes what happens when the value changes.
 */
public interface BarValueListener {
    public void barValueChanged(Bar bar);
        
}

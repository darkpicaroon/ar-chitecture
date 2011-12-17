package tutorial.vortex.camera;

/**
 * There are different reasons, why an AndARException may occur.
 * Though they are rather unlikely.
 * Nevertheless they should be catched, so that the user might be informed.
 * If they occur they are rather severe, which means the library doesn't work. 
 * Therefor they inherit from RuntimeExecptions.
 * This also means, that they may be thrown at any time.
 * @author Tobi
 *
 */
public class AndARException extends Exception {
	public AndARException(String msg) {
		super(msg);
	}
}
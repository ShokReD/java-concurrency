package name.shokred;

import java.util.concurrent.atomic.AtomicBoolean;

public class Test {
    private ComplexObject state;
    private String version;
    private final AtomicBoolean locked = new AtomicBoolean(false);

    public void updateState(ComplexObject newState) {
        String newVersion;
        do {
            newVersion = getVersion();
        } while (locked.compareAndSet(false, true));
        this.state = newState;
        this.version = newVersion;
        this.locked.set(false);
    }

    public String getVersion() {
        throw new UnsupportedOperationException();
    }
}

package name.shokred;

import java.util.concurrent.atomic.AtomicReference;

public class VersionBasedOnComplexObjectTest {
    private final AtomicReference<StateWithVersion> state;

    public VersionBasedOnComplexObjectTest(final ComplexObject state, final String version) {
        this.state = new AtomicReference<>(new StateWithVersion(state, version));
    }

    public void updateState(ComplexObject newState) {
        while (true) {
            final String newVersion = getVersion();
            if (state.compareAndSet(state.get(), new StateWithVersion(newState, newVersion))) {
                break;
            }
        }
    }

    public String getVersion() {
        final StateWithVersion stateWithVersion = this.state.get();
        final String currentVersion = stateWithVersion.version;
        return String.valueOf(parseOrZero(currentVersion) + 1);
    }

    private int parseOrZero(final String stringInt) {
        try {
            return Integer.parseInt(stringInt);
        } catch (final NumberFormatException exception) {
            return 0;
        }
    }

    private static class StateWithVersion {
        final ComplexObject state;
        final String version;

        private StateWithVersion(final ComplexObject state,
                                 final String version) {
            this.state = state;
            this.version = version;
        }
    }
}

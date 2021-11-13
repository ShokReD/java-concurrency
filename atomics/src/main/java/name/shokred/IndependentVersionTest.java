package name.shokred;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class IndependentVersionTest {
    private final AtomicReference<StateWithVersion> state;

    public IndependentVersionTest(final ComplexObject state, final String version) {
        this.state = new AtomicReference<>(new StateWithVersion(state, version));
    }

    public void updateState(ComplexObject newState) {
        while (true) {
            if (state.compareAndSet(state.get(), new StateWithVersion(newState, getVersion()))) {
                break;
            }
        }
    }

    public String getVersion() {
        return UUID.randomUUID().toString();
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

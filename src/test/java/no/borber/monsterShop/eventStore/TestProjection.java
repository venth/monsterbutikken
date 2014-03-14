package no.borber.monsterShop.eventStore;

import java.util.ArrayList;
import java.util.List;

public class TestProjection extends Projection {
    private List<Event> testEvents = new ArrayList<>();

    public TestProjection(EventStore eventStore) {
        super(eventStore);
    }

    @Override
    public void handleEvent(Event event) {
        testEvents.add(event);
    }

    public List<Event> getTestEvents() {
        return testEvents;
    }
}

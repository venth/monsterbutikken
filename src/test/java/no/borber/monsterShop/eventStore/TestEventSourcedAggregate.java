package no.borber.monsterShop.eventStore;

public class TestEventSourcedAggregate extends EventSourcedAggregate {
    public TestEventSourcedAggregate(EventStore eventStore) {
        super(eventStore);
    }

    @Override
    public void handleCommand(Command command) {
        eventStore.store(new TestEvent("testAggregateId"));
    }

    @Override
    public void handleEvent(Event event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

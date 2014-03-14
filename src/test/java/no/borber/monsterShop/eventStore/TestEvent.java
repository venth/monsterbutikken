package no.borber.monsterShop.eventStore;

public class TestEvent extends Event{
    public TestEvent(String aggregateId) {
        super(aggregateId);
    }

    @Override
    public String getAggregateType() {
        return "TEST";
    }
}

package no.borber.monsterShop.eventStore;

import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class EventStoreTest {

    @Test
    public void test_that_the_event_store_can_receive_events_and_store_aggregates() throws Exception {
        EventStore eventStore = new EventStore();
        eventStore.store(new TestEvent("aggregateId"));
        List<Event> aggregateEvents = eventStore.getAggregateEvents("aggregateId");
        assertNotNull(aggregateEvents);
        assertEquals(1, aggregateEvents.size());
        assertEquals("aggregateId", aggregateEvents.get(0).getAggregateId());
    }

    @Test
    public void test_that_projections_can_subscribe_to_es_and_receive_events() throws Exception {
        EventStore eventStore = new EventStore();
        TestProjection projection = new TestProjection(eventStore);
        eventStore.store(new TestEvent("aggregateId"));
        List<Event> testEvents = projection.getTestEvents();
        assertNotNull(testEvents);
        assertEquals(1, testEvents.size());
    }

    @Test
    public void test_that_aggregates_can_handle_commands_and_dispatch_to_es() throws Exception {
        EventStore eventStore = new EventStore();
        EventSourcedAggregate testAggregate = new TestEventSourcedAggregate(eventStore);
        testAggregate.handleCommand(new TestCommand());
        List<Event> aggregateEvents = eventStore.getAggregateEvents("testAggregateId");
        assertNotNull(aggregateEvents);
        assertEquals(1, aggregateEvents.size());
    }


}

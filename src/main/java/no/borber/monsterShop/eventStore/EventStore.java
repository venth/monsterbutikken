package no.borber.monsterShop.eventStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventStore {

    public List<Event> events = new ArrayList<>();
    private Map<Projection, String> subscribers = new HashMap<>();

    public void store(Event event) {
        events.add(event);
        publishEvent(event);
    }

    private void publishEvent(Event event) {
        for (Map.Entry<Projection, String> subscriber : subscribers.entrySet()){
            if (event.getAggregateType().equals(subscriber.getValue()))
                publish(subscriber.getKey(), event);
        }
    }

    private void publish(Projection projection, Event event){
            projection.handleEvent(event);
    }

    public List<Event> getAggregateEvents(String aggregateId) {
        List<Event> aggregateEvents = new ArrayList<>();
        for (Event event : events){
            if (event.getAggregateId().equals(aggregateId))
                aggregateEvents.add(event);
        }
        return aggregateEvents;
    }

    public void subscribe(Projection projection, String aggregateType) {
        for (Event event : events){
            if (event.getAggregateType().equals(aggregateType))
                publish(projection, event);
        }
        subscribers.put(projection, aggregateType);
    }
}

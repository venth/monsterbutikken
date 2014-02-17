package no.borber.monsterbutikken.ordrer;

import akka.actor.UntypedActor;
import akka.japi.Procedure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HotSwapActor extends UntypedActor {
    public static final Logger log = LoggerFactory.getLogger(HotSwapActor.class);

    Procedure<Object> angry = new Procedure<Object>() {
        @Override
        public void apply(Object message) {
            if (message.equals("bar")) {
                log.info("already Angry!");
                getSender().tell("I am already angry?", getSelf());
            } else if (message.equals("foo")) {
                log.info("Switching from angry to happy!");
                getContext().become(happy);
            }
        }
    };

    Procedure<Object> happy = new Procedure<Object>() {
        @Override
        public void apply(Object message) {
            if (message.equals("bar")) {
                log.info("already happy!");
                getSender().tell("I am already happy :-)", getSelf());
            } else if (message.equals("foo")) {
                log.info("Switching from happy to angry!");
                getContext().become(angry);
            }
        }
    };

    public void onReceive(Object message) {
        log.info("got message");
        if (message.equals("bar")) {
            log.info("bar: becoming angry");
            getContext().become(angry);
        } else if (message.equals("foo")) {
            log.info("foo: becoming happy");
            getContext().become(happy);
        } else {
            log.info("Unhandled!");
            unhandled(message);
        }
    }
}
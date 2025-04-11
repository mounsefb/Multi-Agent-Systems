import java.util.*;

class EventComparator implements Comparator<Event>{
    public int compare(Event s1, Event s2) {
        if (s1.getDate()< s2.getDate()) return 1;
        return 0;
    }
}

import java.util.Iterator;
import java.util.PriorityQueue;

public class EventManager {
    private long currentDate;
    private PriorityQueue<Event> listEvent;

    public EventManager(){
        currentDate=0;
        listEvent = new PriorityQueue<Event>(new EventComparator());
    }

    public void addEvent(Event e){
        listEvent.add(e);
    }

    public void execute(){
        System.out.println("pas de fonction execute specifié pour cet evenement");
    }

    public void next(){
        Iterator<Event> iterator = listEvent.iterator();
        int ok=0;
        while(ok==0){
            while (iterator.hasNext()){
                Event e = iterator.next();
                if(e.getDate()==currentDate){
                    e.execute();
                    listEvent.remove(e);
                    iterator = listEvent.iterator();
                    break;
                }
            }
            if (iterator.hasNext()==false){
                ok=1;
            }
        }
        currentDate++;
    }

    public boolean isFinished(){
        if(listEvent.size()==0){
            return true;
        }
        return false;
    }

    public void restart(){
        listEvent.clear();
        
        currentDate=0;
    }

    @Override
    public String toString(){
        String s = new String();
        Iterator<Event> iterator = listEvent.iterator();
        int i=0;
        while (iterator.hasNext()){
            Event e = iterator.next();
            s+="Element n°"+i+" en date : "+e.getDate()+" ";
            i++;
        }
        return s;
    }

    public long getCurDate(){
        return currentDate;
    }
}

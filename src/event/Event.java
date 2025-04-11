/**
 * La classe Event est une classe abstraite représentant un événement dans la simulation.
 */
public abstract class Event {
    /** La date à laquelle l'événement est planifié. */
    private long date;

    /**
     * Obtient la date de l'événement.
     *
     * @return La date de l'événement.
     */
    public long getDate() {
        return date;
    }

    /**
     * Constructeur de la classe Event.
     *
     * @param date La date à laquelle l'événement est planifié.
     */
    public Event(long date) {
        this.date = date;
    }

    /**
     * Méthode abstraite pour exécuter l'événement.
     */
    public abstract void execute();
}

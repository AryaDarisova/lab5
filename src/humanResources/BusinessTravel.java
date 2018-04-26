package humanResources;

import java.time.LocalDate;

public final class BusinessTravel {
    private final int compensation;
    private final LocalDate startTrip;
    private final LocalDate endTrip;
    private final String description;
    private final String destination;

    private static final int COMPENSATION = 0;
    private static final String DESCRIPTION = "";
    private static final String DESTINATION = "";
    private static final LocalDate START_TRIP = LocalDate.now();
    private static final LocalDate END_TRIP = LocalDate.now().plusDays(1);

    /*
    Конструкторы:
    - Без параметров. Поля инициируются: строки – пустыми, числа – 0.
    - Принимающий 4 параметра: название города, число дней (проведенных в
командировке), сумма компенсации, описание
     */

    public BusinessTravel () {
        this(DESTINATION, START_TRIP, END_TRIP, COMPENSATION, DESCRIPTION);
    }

    public  BusinessTravel (String destination, LocalDate startTrip, LocalDate endTrip, int compensation, String description) {
        if (endTrip.isBefore(startTrip))
            throw new java.lang.IllegalArgumentException("Final date of trip can't be earlier than the beginning!");
        if (compensation < 0)
            throw new java.lang.IllegalArgumentException("Compensation can't be less than zero!");
        this.destination = destination;
        this.startTrip = startTrip;
        this.endTrip = endTrip;
        this.compensation = compensation;
        this.description = description;
    }

    /*
    Методы:
    - возвращающий название города.
    - возвращающий число дней.
    - возвращающий сумму компенсации.
    - возвращающий описание.
     */

    public int getCompensation() {
        return compensation;
    }

    public LocalDate getStartTrip() {
        return startTrip;
    }

    public LocalDate getEndTrip() {
        return endTrip;
    }

    public int getDaysCount() {
        return endTrip.getDayOfYear() - startTrip.getDayOfYear() + 1;
    }

    public String getDescription() {
        return description;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        //“<city> <daysCount> (<compensation>). <description>”
        return String.format("%s %s-%s (%d). %s", destination, startTrip.toString(), endTrip.toString(), compensation, description);
    }

    @Override
    public boolean equals(Object obj)  {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(this.getClass() == obj.getClass())) {
            return false;
        }
        BusinessTravel equalsTravel = (BusinessTravel) obj;
        return (this.compensation == equalsTravel.compensation) &&
                (this.startTrip == equalsTravel.startTrip) &&
                (this.endTrip == equalsTravel.endTrip) &&
                (this.description.equals(equalsTravel.description)) &&
                (this.destination.equals(equalsTravel.destination));
    }

    @Override
    public int hashCode() {
        return compensation ^ startTrip.hashCode() ^ endTrip.hashCode() ^ description.hashCode() ^ destination.hashCode();
    }
}

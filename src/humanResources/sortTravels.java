package humanResources;

import java.util.Comparator;

public class sortTravels implements Comparator<BusinessTravel>{
    @Override
    public int compare(BusinessTravel o1, BusinessTravel o2) {
        return (o1.getEndTrip().getDayOfYear() - o2.getStartTrip().getDayOfYear());
    }
}

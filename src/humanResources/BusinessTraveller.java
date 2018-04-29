package humanResources;

import humanResources.exceptions.IllegalDatesException;
import java.time.LocalDate;
import java.util.Set;

public interface BusinessTraveller extends Set<BusinessTravel> {

    /*
    2 метода:
    - добавляющий информацию о командировке (в качестве параметра принимает ссылку
на экземпляр класса BusinessTravel).
   - возвращающий массив командировок.
     */

    boolean add(BusinessTravel travel);
    BusinessTravel[] getTravels();
    int size();
    boolean isOnTrip();
    int isOnTrip(LocalDate startTrip, LocalDate endTrip);
}

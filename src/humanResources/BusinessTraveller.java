package humanResources;

import humanResources.exceptions.IllegalDatesException;
import java.time.LocalDate;

public interface BusinessTraveller {

    /*
    2 метода:
    - добавляющий информацию о командировке (в качестве параметра принимает ссылку
на экземпляр класса BusinessTravel).
   - возвращающий массив командировок.
     */

    boolean addTravel(BusinessTravel travel) throws IllegalDatesException;
    BusinessTravel[] getTravels();
    int getTravelsQuantity();
    boolean isOnTrip();
    int isOnTrip(LocalDate startTrip, LocalDate endTrip);
}

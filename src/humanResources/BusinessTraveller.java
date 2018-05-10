package humanResources;

import humanResources.exceptions.IllegalDatesException;
import java.time.LocalDate;
import java.util.Set;
import java.util.SortedSet;
//todo sortedset граничения: по датам начала и датам конца
// exception если добавляются с пересекающимся временем
// дата начала новой поездки соответственно идет после конца предыдущей
//и также при вставке нового элемента это проверять

public interface BusinessTraveller extends SortedSet<BusinessTravel> {

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

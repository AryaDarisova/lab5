package humanResources;

import humanResources.exceptions.*;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeGroup extends List<Employee> {
    Employee[] sortedEmployees();
    Employee[] getEmployees();
    int size();
    boolean remove(String firstName, String secondName);
    Employee getEmployee(String firstName, String secondName);
    String getName();
    void setName(String name);
    Employee mostValuableEmployee();
    public String toString();
    public boolean equals(Object obj);
    public int hashCode();
    Employee[] getEmployees(JobTitlesEnum jobTitle);
    boolean hasEmployee(String firstName, String secondName);
    int getEmployeesQuantity(JobTitlesEnum jobTitle);
    void bonusForBusinessTravellers();
    int travellers();
    Employee[] businessTravellers();
    int partTimeEmployeeQuantity();
    int staffEmployeeQuantity();
    int nowInTravel();
    Employee[] getStaffInTravel(LocalDate startTrip, LocalDate endTrip);
    int staffInTravelQuantity(LocalDate startTrip, LocalDate endTrip);

    /*
    - возвращающий имя группы
    - устанавливающий новое имя группы (принимает новое имя в качестве параметра)
    - добавляющий сотрудника в конец списка (принимает ссылку на сотрудника в качестве параметра)
    - возвращающий сотрудника по имени и фамилии
    - удаляющий сотрудника по имени и фамилии
    - удаляющий сотрудника. Принимает экземпляр сотрудника в качестве параметра.
    - возвращающий сотрудника с наибольшей заработной платой.
    - возвращающий общее число сотрудников в группе.
    - возвращающий массив сотрудников
    - возвращающий массив сотрудников, отсортированный по размеру заработной платы
     */
}

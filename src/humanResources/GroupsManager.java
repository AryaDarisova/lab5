package humanResources;

import humanResources.exceptions.*;
import java.time.LocalDate;
import java.util.List;

public interface GroupsManager extends List<EmployeeGroup> {
    boolean add(EmployeeGroup groupable);
    int removeQuantity(EmployeeGroup group);
    boolean remove(String groupName);
    EmployeeGroup getEmployeeGroup(String name);
    EmployeeGroup[] getEmployeeGroups();
    int size();
    int employeesQuantity();
    int employeesQuantity(JobTitlesEnum jobTitle);
    Employee mostValuableEmployee();
    EmployeeGroup getEmployeesGroup(String firstName, String secondName);
    void setBonus();
    int partTimeEmployeeQuantity();
    int staffEmployeeQuantity();
    int nowInTravel();
    Employee[] getStaffInTravel(LocalDate startTrip, LocalDate endTrip);
    int staffInTravelQuantity(LocalDate startTrip, LocalDate endTrip);

    /*
    - добавления группы в конец списка. Принимает ссылку на экземпляр класса, реализующего интерфейс EmployeeGroup в качестве параметра.
    - удаляющий группу. Принимает группу в качестве параметра. Возвращает число удаленных групп.
    - удаляющий группу по его названию (принимает название в качестве параметра). Возвращает логическое значение.
    - возвращающий группу по ее названию (принимает название в качестве параметра).
    - возвращающий массив групп.
    - возвращающий общее число групп.
    - возвращающий общее число сотрудников во всех группах.
    - возвращающий количество сотрудников, занимающих заданную должность (должность передается в качестве параметра).
    - возвращающий сотрудника с максимальной заработной платой
    - возвращающий ссылку на группу к которой относится сотрудник. Имя и фамилия сотрудника передается в качестве параметра.
     */
}

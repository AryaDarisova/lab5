package humanResources;

import humanResources.exceptions.*;
import java.time.LocalDate;

public class DepartmentsManager implements GroupsManager {
    private String name;
    private Department[] departments;
    private int size = 0;
    private static final int SIZE = 8;

    /*
    Конструкторы: принимающий один параметр – называние организации.
    */

    public DepartmentsManager(String name) {
        this(name, SIZE);
    }

    /*
    принимающий два параметра - назввние и количество департаментов
     */

    public DepartmentsManager(String name, int size) throws NegativeSizeException{
        if(size < 0 ) throw new NegativeSizeException("You enter a negative array size value! Please write size more than zero!");
        this.name = name;
        this.departments = new Department[size];
    }

    /*
    принимающий два параметра – название и массив департаментов.
    */

    public DepartmentsManager(String name, Department[] departments) {
        this.name = name;
        System.arraycopy(departments, 0, this.departments, 0, SIZE);
    }

    /*
    Методы: добавления департамента в организацию. Принимает ссылку на экземпляр класса
    Department в качестве параметра.
    */

    @Override
    public void add(EmployeeGroup groupable) throws AlreadyAddedException {
        for (EmployeeGroup x: departments) {
            if (x == groupable)
                throw new AlreadyAddedException("You are already added this employee!");
        }
        if (size >= departments.length) {
            EmployeeGroup[] departmentResize = new Department[size * 2];
            System.arraycopy(departments, 0, departmentResize, 0, departments.length);
            this.departments = (Department[]) departmentResize;
        }
        departments[size] = (Department) groupable;
        size++;
    }

    /*
    удаляющий департамент по его названию (принимает название в качестве параметра).
    */

    @Override
    public boolean remove(String groupName) {
        boolean remove = false;
        for (int i = 0; i < size; i++) {
            if (departments[i].getName().equals(groupName)) {
                for (int j = i + 1; j < size; j++) {
                    departments[i] = departments[j];
                }
                size--;
                remove = true;
            }
        }
        return remove;
    }

    /*
    - удаляющий группу. Принимает группу в качестве параметра. Возвращает число удаленных групп.
     */

    @Override
    public int remove(EmployeeGroup group) {
        int remove = 0;
        for (int i = 0; i < size; i++) {
            if (departments[i].equals(group)) {
                for (int j = i + 1; j < size; j++) {
                    departments[i] = departments[j];
                }
                size--;
                remove++;
            }
        }
        return remove;
    }

    /*
    возвращающий ссылку на экземпляр класса Department по его названию (принимает
    название в качестве параметра).
    */

    @Override
    public EmployeeGroup getEmployeeGroup(String name) {
        Department getEmployeeGroup = null;
        for (int i = 0; i < size; i++) {
            if (departments[i].getName().equals(name)) {
                getEmployeeGroup = departments[i];
                return getEmployeeGroup;
            }
        }
        return null;
    }

    /*
    возвращающий массив отделов.
    */

    @Override
    public EmployeeGroup[] getEmployeeGroups() {
        EmployeeGroup[] getDepartments = new Department[size];
        System.arraycopy(departments, 0, getDepartments, 0, size);
        return getDepartments;
    }

    /*
    возвращающий общее число отделов.
    */

    @Override
    public int groupsQuantity() {
        return size;
    }

    /*
    возвращающий общее число сотрудников в организации.
    */

    @Override
    public int employeesQuantity() {
        int employeesQuantity = 0;
        for (int i = 0; i < size; i++) {
            employeesQuantity += departments[i].size();
        }
        return employeesQuantity;
    }

    /*
    возвращающий количество сотрудников, занимающих заданную должность (должность
    передается в качестве параметра).
    */

    @Override
    public int employeesQuantity(JobTitlesEnum jobTitle) {
        int employeesQuantity = 0;
        for (int i = 0; i < size; i++) {
            employeesQuantity += departments[i].employeesQuatityByJob(jobTitle);
        }
        return employeesQuantity;
    }

    /*
    возвращающий сотрудника с максимальной заработной платой в организации
    */

    @Override
    public Employee mostValuableEmployee() {
        Employee bestEmployee = null;
        bestEmployee = departments[0].getEmployees()[0];
        for (int i = 0; i < size; i++) {
            if (departments[i].mostValuableEmployee().getSalary() > bestEmployee.getSalary()) {
                bestEmployee = departments[i].mostValuableEmployee();
            }
        }
        return bestEmployee;
    }

    /*
    возвращающий ссылку на отдел в котором работает сотрудник. Имя и фамилия сотрудника
    передается в качестве параметра.
    */

    @Override
    public Department getEmployeesGroup(String firstName, String secondName) {
        Department getEmployeesDepartment = null;
        for (int i = 0; i < size; i++) {
            if (departments[i].hasEmployee(firstName, secondName)) {
                getEmployeesDepartment = departments[i];
            }
        }
        return getEmployeesDepartment;
    }

    @Override
    public void setBonus() {
        for (int i = 0; i < size; i++) {
            departments[i].bonusForBusinessTravellers();
        }
    }

    /*
    Возвращающий число совместителей
    Возвращающий число штатных сотрудников
     */

    @Override
    public int partTimeEmployeeQuantity() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            count += departments[i].partTimeEmployeeQuantity();
        }
        return count;
    }

    @Override
    public int staffEmployeeQuantity() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            count += departments[i].staffEmployeeQuantity();
        }
        return count;
    }

    /*
    Возвращающий число сотрудников, находящихся в командировке в данный
    момент
     */

    @Override
    public int nowInTravel() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            count += departments[i].nowInTravel();
        }
        return count;
    }

    /*
    метод, считающий кол-во сотрудников, которые в данный момент находились в командировке
     */

    public int staffInTravelQuantity(LocalDate startTrip, LocalDate endTrip) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            count += departments[i].staffInTravelQuantity(startTrip, endTrip);
        }
        return count;
    }

    /*
    Возвращающий массив сотрудников, находящихся в командировке в заданный
    период времени
     */

    @Override
    public  Employee[] getStaffInTravel(LocalDate startTrip, LocalDate endTrip) {
        Employee[] getStaffNowInTravel = new Employee[staffInTravelQuantity(startTrip, endTrip)];
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (Employee x: departments[i].getStaffInTravel(startTrip, endTrip)) {
                getStaffNowInTravel[count++] = x;
            }
        }
        return getStaffNowInTravel;
    }
}
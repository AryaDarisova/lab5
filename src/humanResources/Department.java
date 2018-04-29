package humanResources;

import java.time.LocalDate;
import java.util.*;

import humanResources.exceptions.*;

public class Department implements EmployeeGroup{
    private String name;
    private Employee[] employees;
    private static final int SIZE_DEFAULT = 8;
    private int size = 0;

    /*
    Конструкторы:
    - принимающий один параметр – название отдела, инициирующий массив из 8 элементов (сами элементы имеют значение null).
     */

    public Department(String name) {
        this(name,SIZE_DEFAULT);
    }

    /*
    - принимающий название и целое число – емкость массива, инициирующий массив указанным числом элементов (сами элементы имеют значение null).
     */

    public Department(String name, int size) throws NegativeSizeException {
        if(size < 0 ) throw new NegativeSizeException("You enter a negative array size value! Please write size more than zero!");
        this.name = name;
        this.employees = new Employee[size];
    }

    /*
    - принимающий название отдела и массив сотрудников.
     */

    public Department(String name, Employee[] employees) {
        this.name = name;
        System.arraycopy(employees, 0, this.employees, 0, SIZE_DEFAULT);
    }

    /*
    Методы:
    - добавляющий сотрудника в отдел (принимает ссылку на экземпляр класса Employee).
    Емкость – это размер массива. Число элементов – это текущее число элементов, имеющих
    ссылки на экземпляр класса Employee (их может быть меньше, чем размер массива).
    */

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Employee element: employees) {
            if (element.equals(o))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<Employee> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        Employee[] employeesInArray = new Employee[size];
        System.arraycopy(employees, 0, employeesInArray, 0, size);
        return employeesInArray;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            return (T[]) Arrays.copyOf(employees, size, a.getClass());
        System.arraycopy(employees, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    @Override
    public boolean add(Employee employee) {
        if (size >= employees.length) {
            Employee[] employeesResize = new Employee[size * 2];
            System.arraycopy(employees, 0, employeesResize, 0, employees.length);
            this.employees = employeesResize;
        }
        employees[size] = employee;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (employees[i] == o) {
                for (int j = i + 1; j < size; j++) {
                    employees[i] = employees[j];
                }
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element: c) {
            if (!contains(element))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Employee> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Employee> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    /*
    - увольнения сотрудника по его имени и фамилии (принимает 2 параметра – имя и фамилию).
    Возвращает логическое значение (true, если элемент был удален).
    При удалении заказа емкость массива не меняется. Все элементы с 0-го до удаляемого (не
    включительно) остаются без изменений. А элементы, начиная со следующего, после удаляемого,
    копируются в предыдущий элемент. Последний, имеющий значение не null после копирования
    заменяется значением null.
    */

    @Override
    public boolean remove(String firstName, String secondName) {
        boolean remove = false;
        for (int j = 0; j < size; j++) {
            if ((employees[j].getFirstName().equals(firstName)) & (employees[j].getSecondName().equals(secondName))) {
                for (int i = j + 1; i < size; i++) {
                    employees[j] = employees[i];
                }
                size--;
                remove = true;
            }
        }
        return remove;
    }

    /*
    - возвращающий массив сотрудников (значений null в массиве быть не должно).
    */

    @Override
    public Employee[] getEmployees() {
        Employee[] employeesInArray = new Employee[size];
        System.arraycopy(employees, 0, employeesInArray, 0, size);
        return employeesInArray;
    }

    /*
    - подсчет количества сотрудников по фамилии
     */

    @Override
    public int getEmployeesQuantity(JobTitlesEnum jobTitle){
        int employeesQuatity = 0;
        for (int i = 0; i < size; i++) {
            if (employees[i].getJobTitle().equals(jobTitle)) {
                employeesQuatity++;
            }
        }
        return employeesQuatity;
    }

    /*
    - возвращающий массив сотрудников, занимающих заданную должность (должность
    передается в качестве параметра)
    */

    public Employee[] getEmployees(JobTitlesEnum jobTitle) {
        Employee[] employeesByJobTitle = new Employee[getEmployeesQuantity(jobTitle)];
        for (int j = 0, k = 0; j < size; j++) {
            if (employees[j].getJobTitle().equals(jobTitle)) {
                employeesByJobTitle[k] = employees[j];
                k++;
            }
        }
        return employeesByJobTitle;
    }

    /*
    -  возвращающий массив сотрудников, отсортированный по убыванию заработной платы.
    */

    @Override
    public Employee[] sortedEmployees() {
        Employee[] sortedEmployees = getEmployees();
        Arrays.sort(sortedEmployees, Employee.salaryAndBonusComparator.reversed());
        return sortedEmployees;
    }

    /*
    Метод, возвращающий количство сотрудников с заданной должностью
     */

    public int employeesQuatityByJob (JobTitlesEnum jobTitle) {
        return getEmployees(jobTitle).length;
    }

    /*
   - возвращающий сотрудника с наибольшей заработной платой.
     */

    @Override
    public Employee mostValuableEmployee() {
        Employee bestEmployeeInDepartment;
        bestEmployeeInDepartment = getEmployees()[0];
        for (int i = 0; i < size; i++) {
            if (employees[i].getSalary() > bestEmployeeInDepartment.getSalary()) {
                bestEmployeeInDepartment = employees[i];
            }
        }
        return bestEmployeeInDepartment;
    }

    /*
    Метод, определяющий наличие сотрудника в отделе
     */

    public boolean hasEmployee(String firstName, String secondName) {
        for (int i = 0; i < size; i++) {
            if (employees[i].getFirstName().equals(firstName) & employees[i].getSecondName().equals(secondName)) {
                return true;
            }
        }
        return false;
    }

    /*
    - возвращает название отдела
     */

    @Override
    public String getName() {
        return name;
    }

    /*
    - устанавливающий новое имя группы (принимает новое имя в качестве параметра)
     */

    @Override
    public void setName(String name) {
        this.name = name;
    }

    /*
    - возвращающий сотрудника по имени и фамилии
     */

    @Override
    public Employee getEmployee(String firstName, String secondName) {
        Employee getEmployee = null;
        for (int i = 0; i < size; i++) {
            if (employees[i].getFirstName().equals(firstName) & employees[i].getSecondName().equals(secondName)) {
                getEmployee = employees[i];
                return getEmployee;
            }
        }
        return getEmployee;
    }

    /*
    - удаляющий всех сотрудников с заданной должностью (принимает должность в качестве входного параметра).
     */

    public int removeAll(JobTitlesEnum jobTitle) {
        int removeAll = 0;
        for (int i = 0; i < size; i++) {
            if (employees[i].getJobTitle() == jobTitle) {
                for (int j = i + 1; j < size; j++) {
                    employees[i] = employees[j];
                }
                size--;
                removeAll++;
            }
        }
        return removeAll;
    }

    /*
    - возвращающий массив должностей сотрудников (JobTitleEnum) без повтора.
     */

    public JobTitlesEnum[] jobTitles(){
        int num = size;
        for (int i = 0, m; i != num; i++, num = m )
        {
            for ( int j = m = i + 1; j != num; j++ )
            {
                if (!employees[j].getJobTitle().equals(employees[i].getJobTitle()))
                {
                    if ( m != j )
                        employees[m] = employees[j];
                    m++;
                }
            }
        }
        JobTitlesEnum[] jobTitles = new JobTitlesEnum[num];
        if ( num != size)
        {
            for ( int i = 0; i < num; i++ )
                jobTitles[i] = employees[i].getJobTitle();
        }
        return jobTitles;
    }

    /*
    - возвращающий массив сотрудников, которые хоть раз направлялись в командировку.
     */

    @Override
    public int travellers() {
        int travellers = 0;
        for (int i = 0; i < size; i++) {
            if (employees[i] instanceof StaffEmployee)
                if (((StaffEmployee) employees[i]).size() > 0) {
                    travellers++;
                }
        }
        return travellers;
    }

    @Override
    public Employee[] businessTravellers() {
        int k = 0;
        Employee[] businessTravellers = new Employee[travellers()];
        for (Employee x : employees) {
            if (x instanceof StaffEmployee) {
                if (isTraveller(x))
                    businessTravellers[k++] = x;
            }
        }
        return businessTravellers;
    }

    /*
    - проверяется количество командировок
     */

    public boolean isTraveller(Employee x) {
        return ((StaffEmployee) x).size() > 0;
    }

    /*
    - устанавливает бонус в половину зарплаты
     */

    @Override
    public void bonusForBusinessTravellers() {
        for (Employee x: employees) {
            if (x instanceof StaffEmployee)
                if (isTraveller(x))
                    x.setBonus(x.getSalary() / 2);
        }
    }

    @Override
    public String toString(){
    /*
    “Department <name>: <size>
    <строковое представление employee 1>
    <строковое представление employee 2>
    …
    <строковое представление employee N>”
    */
        return getString().toString();
    }

    public StringBuilder getString(){
        StringBuilder line = new StringBuilder();
        line.append("Department ").append(name).append(": ").append(size).append("\n");
        for(Employee x : employees) {
            line.append(x.toString()).append('\n');
        }
        return line;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if (obj==null || !(this.getClass() == obj.getClass()))
            return false;
        Department equalsDepartment = (Department) obj;
        if (!this.name.equals(equalsDepartment.name)) {
            return false;
        }
        if (this.size != equalsDepartment.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (this.employees[i] != equalsDepartment.employees[i])
                return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        int hash = 0;
        for (Employee x: employees) {
            hash ^= x.hashCode();
        }
        return name.hashCode() ^ hash ^ size;
    }

    @Override
    public Employee get(int index) {
        return null;
    }

    @Override
    public Employee set(int index, Employee element) {
        return null;
    }

    @Override
    public void add(int index, Employee element) {

    }

    @Override
    public Employee remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<Employee> listIterator() {
        return null;
    }

    @Override
    public ListIterator<Employee> listIterator(int index) {
        return null;
    }

    @Override
    public List<Employee> subList(int fromIndex, int toIndex) {
        return null;
    }

    /*
    Возвращающий число совместителей
    Возвращающий число штатных сотрудников
     */

    @Override
    public int partTimeEmployeeQuantity() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (employees[i] instanceof PartTimeEmployee)
                count++;
        }
        return count;
    }

    @Override
    public int staffEmployeeQuantity() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (employees[i] instanceof StaffEmployee)
                count++;
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
            if (employees[i] instanceof StaffEmployee) {
                if (((StaffEmployee) employees[i]).isOnTrip())
                    count++;
            }
        }
        return count;
    }

    /*
    в стаффемплоии есть метод считающий количество дней проведенных в командировке в заданный период.
    сделать проверку, если у сотрудника этот метод возвращает значение больше 0, то кол-во сотрудников++
    (это для создания массива)
     */

    @Override
    public int staffInTravelQuantity(LocalDate startTrip, LocalDate endTrip) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (employees[i] instanceof StaffEmployee) {
                if (((StaffEmployee) employees[i]).isOnTrip(startTrip, endTrip) > 0)
                    count++;
            }
        }
        return count;
    }

    /*
    Возвращающий массив сотрудников, находящихся в командировке в заданный
    период времени
     */

    @Override
    public Employee[] getStaffInTravel(LocalDate startTrip, LocalDate endTrip) {
        Employee[] getStaffNowInTravel = new Employee[staffInTravelQuantity(startTrip, endTrip)];
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (employees[i] instanceof StaffEmployee) {
                if (((StaffEmployee) employees[i]).isOnTrip(startTrip, endTrip) > 0)
                    getStaffNowInTravel[count++] = employees[i];
            }
        }
        return getStaffNowInTravel;
    }
}
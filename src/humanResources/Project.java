package humanResources;

import java.time.LocalDate;
import java.util.*;

import humanResources.exceptions.*;

public class Project implements EmployeeGroup{
    private String name;
    private int size;
    private Node head;
    private Node tail;

    /*
    Конструкторы:
    - принимающий название (для списка создается только головной элемент).
    - принимающий название и массив сотрудников (список инициализируется элементами из массива).
     */

    public Project(String name) {
        this.name = name;
        size = 0;
    }

    public Project(String name, Employee[] employees) throws AlreadyAddedException{
        this.name = name;
        for (Employee x: employees) {
            add(x);
            size++;
        }
    }

    @Override
    public Employee[] sortedEmployees() {
        Employee[] sortedEmployees = getEmployees();
        Arrays.sort(sortedEmployees, Employee.salaryAndBonusComparator.reversed());
        return sortedEmployees;
    }

    @Override
    public Employee[] getEmployees() {
        Employee[] getEmployees = new Employee[size];
        Node node = head;
        for (int i = 0; i < size; i++){
            getEmployees[i] = node.value;
            node = node.next;
        }
        return getEmployees;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<Employee> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(Employee employee) {
        Node node = new Node(employee);
        addNode(node);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node previous = null;
        Node current;
        for (current = head; current != null; current = current.next){
            if (current.value.equals(o)) {
                removeNode(previous, current);
                return true;
            } else {
                previous = current;
            }
        }
        tail = previous;
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
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

    @Override
    public int getEmployeesQuantity(JobTitlesEnum jobTitle){
        int employeesQuantity = 0;
        Node node = head;
        while (node != null) {
            if (node.value.getJobTitle().equals(jobTitle)) {
                employeesQuantity++;
            }
            node = node.next;
        }
        return employeesQuantity;
    }

    @Override
    public Employee[] getEmployees(JobTitlesEnum jobTitle) {
        Node node = head;
        Employee[] getEmployees = new Employee[getEmployeesQuantity(jobTitle)];

        for (int i = 0; i < getEmployeesQuantity(jobTitle); i++){
            if (node.value.getJobTitle().equals(jobTitle)) {
                getEmployees[i] = node.value;
            }
            node = node.next;
        }
        return getEmployees;
    }

    @Override
    public boolean remove(String firstName, String secondName) {
        boolean remove = false;
        Node previous = null;
        Node current;

        for (current = head; current != null; current = current.next){
            if (current.value.getFirstName().equals(firstName) & current.value.getSecondName().equals(secondName)) {
                removeNode(previous, current);
            } else {
                previous = current;
            }
        }
        tail = previous;
        return remove;
    }

    @Override
    public Employee getEmployee(String firstName, String secondName) {
        Employee getEmployee = null;
        Node current = head;
        while (current != null) {
            if (current.value.getFirstName().equals(firstName) & current.value.getSecondName().equals(secondName)) {
                getEmployee = current.value;
                return getEmployee;
            }
            current = current.next;
        }
        return getEmployee;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Employee mostValuableEmployee() {
        Node current = head;
        Employee mostValuableEmployee = current.value;
        while (current != null) {
            if (mostValuableEmployee.getSalary() < current.value.getSalary()) {
                mostValuableEmployee = current.value;
            }
            current = current.next;
        }
        return mostValuableEmployee;
    }

    @Override
    public boolean hasEmployee(String firstName, String secondName) {
        Node node = head;
        while (node != null) {
            if (node.value.getFirstName().equals(firstName) &
                    node.value.getSecondName().equals(secondName)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    private void addNode(Node node){
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    private boolean removeNode(Node previous, Node current){
        if (previous != null) {
            previous.next = current.next;
        }
        size--;
        return true;
    }

    private class Node {
        private Node next;
        private Employee value;

        Node(Employee value) {
            this.value = value;
        }

    }

    @Override
    public String toString() {
        /*
        “Project <name>: <size>
        <строковое представление employee 1>
        <строковое представление employee 2>
        …
        <строковое представление employee N>”
        */
        return getString().toString();
    }

    public StringBuilder getString(){
        StringBuilder line = new StringBuilder();
        line.append("Project ").append(name).append(": ").append(size).append("\n");
        Node employee = head;
        while (employee != null){
            line.append(employee.value.toString()).append("\n");
            employee = employee.next;
        }
        return line;
    }

    @Override
    public boolean equals(Object obj)  {
        if (this == obj)
            return true;
        if(obj == null || !(this.getClass() == obj.getClass()))
            return false;

        Project equalsProject = (Project) obj;
        if (!this.name.equals(equalsProject.name))
            return false;
        if (this.size != equalsProject.size){
            return false;
        }
        Node equalsEmployee = head;
        while (equalsEmployee != null) {
            if (this.head.value != equalsEmployee.value) {
                return false;
            }
            this.head = head.next;
            equalsEmployee = equalsEmployee.next;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        Node projects = head;
        while (projects != null) {
            hash ^= projects.value.hashCode();
            projects = projects.next;
        }
        return name.hashCode() ^ size ^ hash;
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
    - возвращающий массив сотрудников, которые хоть раз направлялись в командировку.
     */

    @Override
    public int travellers() {
        int travellers = 0;
        Node node = head;
        while (node != null) {
            if (node.value instanceof StaffEmployee)
                if (((StaffEmployee) node.value).size() > 0) {
                    travellers++;
                }
            node = node.next;
        }
        return travellers;
    }

    @Override
    public Employee[] businessTravellers() {
        Node node = head;
        int k = 0;
        Employee[] businessTravellers = new Employee[travellers()];
        while (node != null){
            if (node.value instanceof StaffEmployee)
                if (((StaffEmployee) node.value).size() > 0)
                    businessTravellers[k++] = node.value;
            node = node.next;
        }
        return businessTravellers;
    }

    /*
    - проверяется количество командировок
     */

    public boolean isTraveller(Node node) {
        return ((StaffEmployee) node.value).size() > 0;
    }

    /*
    - устанавливает бонус в половину зарплаты
    */

    @Override
    public void bonusForBusinessTravellers() {
        Node node = head;
        while (node != null) {
            if (node.value instanceof StaffEmployee)
                if (isTraveller(node))
                    node.value.setBonus(node.value.getSalary() / 2);
            node = node.next;
        }
    }

    /*
    Возвращающий число совместителей
    Возвращающий число штатных сотрудников
     */

    @Override
    public int partTimeEmployeeQuantity() {
        int count = 0;
        Node node = head;
        while (node != null) {
            if (node.value instanceof PartTimeEmployee)
                count++;
            node = node.next;
        }
        return count;
    }

    @Override
    public int staffEmployeeQuantity() {
        int count = 0;
        Node node = head;
        while (node != null) {
            if (node.value instanceof PartTimeEmployee)
                count++;
            node = node.next;
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
        Node node = head;
        while (node != null) {
            if (node.value instanceof StaffEmployee) {
                if (((StaffEmployee) node.value).isOnTrip())
                    count++;
            }
            node = node.next;
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
        Node node = head;
        while (node != null) {
            if (node.value instanceof StaffEmployee)
                if (((StaffEmployee) node.value).isOnTrip(startTrip, endTrip) > 0)
                    count++;
            node = node.next;
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
        Node node = head;
        while (node != null) {
            if (node.value instanceof StaffEmployee) {
                if (((StaffEmployee) node.value).isOnTrip(startTrip, endTrip) > 0)
                    getStaffNowInTravel[count++] = node.value;
            }
            node = node.next;
        }
        return getStaffNowInTravel;
    }
}

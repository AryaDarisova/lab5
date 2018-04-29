package humanResources;

import java.time.LocalDate;
import humanResources.exceptions.*;

public class ProjectManager implements GroupsManager{
    private ProjectsNode head;
    private ProjectsNode tail;
    private int size;

    /*
    Конструкторы:
    - по умолчанию (список групп пустой).
    - принимающий параметр – массив групп. Список инициализируется элементами из массива.
     */

    public ProjectManager() {
        size = 0;
    }

    public ProjectManager(EmployeeGroup[] group) throws AlreadyAddedException {
        for (EmployeeGroup x: group) {
            add(x);
            size++;
        }
    }

    /*
    - добавления группы в конец списка. Принимает ссылку на экземпляр класса, реализующего интерфейс EmployeeGroup в качестве параметра.
     */

    @Override
    public void add(EmployeeGroup group) throws AlreadyAddedException {
        ProjectsNode check = head;
        while (check != null) {
            if (check.value == group)
                throw new AlreadyAddedException("You are already added this employee!");
            check = check.next;
        }
        ProjectsNode node = new ProjectsNode(group);
        addNode(node);
    }

    /*
    - удаляющий группу. Принимает группу в качестве параметра. Возвращает число удаленных групп.
     */

    @Override
    public int remove(EmployeeGroup group) {
        int remove = 0;
        ProjectsNode previous = null;
        ProjectsNode current;

        for (current = head; current != null; current = current.next){
            if (current.value.equals(group)) {
                if(removeNode(previous, current))
                    remove++;
            } else {
                previous = current;
            }
        }
        tail = previous;
        return remove;
    }

    /*
    - удаляющий группу по его названию (принимает название в качестве параметра). Возвращает логическое значение.
     */

    @Override
    public boolean remove(String groupName) {
        boolean remove = false;
        ProjectsNode previous = null;
        ProjectsNode current;

        for (current = head; current != null; current = current.next){
            if (current.value.getName().equals(groupName)) {
                if (removeNode(previous, current))
                    remove = true;
            } else {
                previous = current;
            }
        }
        tail = previous;
        return remove;
    }

    /*
    - возвращающий группу по ее названию (принимает название в качестве параметра).
     */

    @Override
    public EmployeeGroup getEmployeeGroup(String name) {
        EmployeeGroup group = null;
        ProjectsNode node = head;
        while (node != null) {
            if (node.value.getName().equals(name)) {
                group = node.value;
                return group;
            }
            node = node.next;
        }
        return group;
    }

    /*
    - возвращающий массив групп.
     */

    @Override
    public EmployeeGroup[] getEmployeeGroups() {
        EmployeeGroup[] getGroups = new EmployeeGroup[size];
        ProjectsNode node = head;
        for (int i = 0; i < size; i++) {
            getGroups[i] = node.value;
            node = node.next;
        }
        return getGroups;
    }

    /*
    - возвращающий общее число групп.
     */

    @Override
    public int groupsQuantity() {
        return size;
    }

    /*
    - возвращающий общее число сотрудников во всех группах.
     */

    @Override
    public int employeesQuantity() {
        ProjectsNode node = head;
        int employeesQuantity = 0;
        while (node != null) {
            employeesQuantity += node.value.size();
            node = node.next;
        }
        return employeesQuantity;
    }

    /*
    - возвращающий количество сотрудников, занимающих заданную должность (должность передается в качестве параметра).
     */

    @Override
    public int employeesQuantity(JobTitlesEnum jobTitle) {
        ProjectsNode node = head;
        int employeesQuantity = 0;

        if (size !=0) {
            for (int i = 0; i < size; i++) {
                employeesQuantity += node.value.getEmployeesQuantity(jobTitle);
                node = node.next;
            }
            return employeesQuantity;
        }
        else
            return 0;
    }

    /*
    - возвращающий сотрудника с максимальной заработной платой
     */

    @Override
    public Employee mostValuableEmployee() {
        ProjectsNode node = head;
        Employee mostValuableEmployee = head.value.mostValuableEmployee();
        while (node != null) {
            Employee currentMostValuableEmployee = node.value.mostValuableEmployee();
            if (mostValuableEmployee.getSalary() < currentMostValuableEmployee.getSalary()) {
                mostValuableEmployee = currentMostValuableEmployee;
            }
            node = node.next;
        }
        return mostValuableEmployee;
    }

    /*
    - возвращающий ссылку на группу к которой относится сотрудник. Имя и фамилия сотрудника передается в качестве параметра.
     */

    @Override
    public EmployeeGroup getEmployeesGroup(String firstName, String secondName) {
        ProjectsNode node = head;
        EmployeeGroup getEmployeesGroup = null;
        while (node != null) {
            if (node.value.hasEmployee(firstName, secondName)) {
                getEmployeesGroup = node.value;
            }
            node = node.next;
        }
        return getEmployeesGroup;
    }

    private void addNode(ProjectsNode node){
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    private boolean removeNode(ProjectsNode previous, ProjectsNode current){
        if (previous != null) {
            previous.next = current.next;
        }
        size--;
        return true;
    }

    private class ProjectsNode {
        private ProjectsNode next;
        private EmployeeGroup value;

        ProjectsNode(EmployeeGroup value) {
            this.value = value;
        }

    }

    @Override
    public void setBonus(){
        ProjectsNode node = head;
        while (node != null) {
            node.value.bonusForBusinessTravellers();
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
        ProjectsNode node = head;
        while (node != null) {
            count += node.value.partTimeEmployeeQuantity();
            node = node.next;
        }
        return count;
    }

    @Override
    public int staffEmployeeQuantity() {
        int count = 0;
        ProjectsNode node = head;
        while (node != null) {
            count += node.value.staffEmployeeQuantity();
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
        ProjectsNode node = head;
        while (node != null) {
            count += node.value.nowInTravel();
            node = node.next;
        }
        return count;
    }

    /*
    метод, считающий кол-во сотрудников, которые в данный момент находились в командировке
     */

    public int staffInTravelQuantity(LocalDate startTrip, LocalDate endTrip) {
        int count = 0;
        ProjectsNode node = head;
        while (node != null) {
            count += node.value.staffInTravelQuantity(startTrip, endTrip);
            node = node.next;
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
        ProjectsNode node = head;
        while (node != null) {
            for (Employee x: node.value.getStaffInTravel(startTrip, endTrip)) {
                getStaffNowInTravel[count++] = x;
            }
            node = node.next;
        }
        return getStaffNowInTravel;
    }
}

package humanResources;

import java.time.LocalDate;
import java.util.*;

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
    public boolean add(EmployeeGroup group) {
        ProjectsNode node = new ProjectsNode(group);
        addNode(node);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ProjectsNode previous = null;
        ProjectsNode current = head;
        while (current != null) {
            if (current.value.equals(o)) {
                removeNode(previous, current);
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends EmployeeGroup> c) {
        for (EmployeeGroup element: c) {
            add(element);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends EmployeeGroup> c) {
        for (EmployeeGroup element: c) {
            add(index, element);
            index++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object element: c) {
            remove(element);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        ProjectsNode node = head;
        ProjectsNode previous = null;
        while (node != null) {
            if (!c.contains(node.value)) {
                removeNode(previous, node);
                if (previous != null) {
                    node = previous.next;
                }
            }
            else {
                previous = node;
                node = node.next;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        ProjectsNode current = head;
        ProjectsNode next;
        while (current.next != null) {
            next = current.next;
            current.next = null;
            current.value = null;
            current = next;
        }
        head = null;
        tail = null;
    }

    @Override
    public EmployeeGroup get(int index) {
        ProjectsNode node = head;
        for (int i = 0; i < size; i++) {
            if (i == index)
                return node.value;
            node = node.next;
        }
        return null;
    }

    @Override
    public EmployeeGroup set(int index, EmployeeGroup element) {
        ProjectsNode node = head;
        EmployeeGroup getGroups;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                getGroups = node.value;
                node.value = element;
                return getGroups;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public void add(int index, EmployeeGroup element) {
        ProjectsNode current = head;
        ProjectsNode previous = null;
        int i = 0;
        while (current != null) {
            if (i == index) {
                ProjectsNode nodeElement = new ProjectsNode(element);
                nodeElement.next = current;
                if (previous != null) {
                    previous.next = nodeElement;
                }
                size++;
            }
            i++;
            previous = current;
            current = current.next;
        }
    }

    @Override
    public EmployeeGroup remove(int index) {
        ProjectsNode previous = null;
        ProjectsNode current = head;
        EmployeeGroup removed = null;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                removed = current.value;
                removeNode(previous, current);
                return removed;
            }
            previous = current;
            current = current.next;
        }
        return removed;
    }

    @Override
    public int indexOf(Object o) {
        ProjectsNode node = head;
        for (int i = 0; i < size; i++) {
            if (node.value.equals(o))
                return i;
            node = node.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        ProjectsNode node = head;
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (node.value.equals(o))
                index = i;
            node = node.next;
        }
        return index;
    }

    @Override
    public ListIterator<EmployeeGroup> listIterator() {
        return listIterator(-1);
    }

    @Override
    public ListIterator<EmployeeGroup> listIterator(int index) {
        return new ListIterator<EmployeeGroup>() {
            ProjectsNode node = head;
            ProjectsNode previousNode = null;
            int currentIndex = index;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public EmployeeGroup next() {
                if (hasNext()) {
                    currentIndex++;
                    previousNode = node;
                    node = node.next;
                }
                return node.value;
            }

            @Override
            public boolean hasPrevious() {
                return currentIndex > 0;
            }

            @Override
            public EmployeeGroup previous() {
                if (hasPrevious()) {
                    currentIndex--;
                    node = previousNode;
                }
                return node.value;
            }

            @Override
            public int nextIndex() {
                if (hasNext())
                    return currentIndex + 1;
                return currentIndex;
            }

            @Override
            public int previousIndex() {
                if (hasPrevious())
                    return currentIndex - 1;
                return currentIndex;
            }

            @Override
            public void remove() {
                ProjectManager.this.remove(currentIndex);
            }

            @Override
            public void set(EmployeeGroup group) {
                ProjectManager.this.set(currentIndex, group);
            }

            @Override
            public void add(EmployeeGroup group) {
                ProjectManager.this.add(currentIndex, group);
            }
        };
    }

    @Override
    public List<EmployeeGroup> subList(int fromIndex, int toIndex) {
        List<EmployeeGroup> subList = new ProjectManager();
        ProjectsNode node = head;
        int index = 0;
        while (node != null) {
            if (index >= fromIndex && index <= toIndex) {
                subList.add(node.value);
            }
            index++;
            node = node.next;
        }
        return subList;
    }

    /*
    - удаляющий группу. Принимает группу в качестве параметра. Возвращает число удаленных групп.
     */

    @Override
    public int removeQuantity(EmployeeGroup group) {
        int remove = 0;
        ProjectsNode previous = null;
        ProjectsNode current = head;
        while (current != null) {
            if (current.value.equals(group)) {
                if(removeNode(previous, current))
                    remove++;
            } else {
                previous = current;
            }
            current = current.next;
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
        ProjectsNode current = head;

        while (current != null) {
            if (current.value.getName().equals(groupName)) {
                if (removeNode(previous, current))
                    remove = true;
            } else {
                previous = current;
            }
            current = current.next;
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
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (EmployeeGroup element: this) {
            if (element.equals(o))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<EmployeeGroup> iterator() {
        return new Iterator<EmployeeGroup>() {
            ProjectsNode node = head;
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < size;
            }

            @Override
            public EmployeeGroup next() {
                if(hasNext()){
                    node = node.next;
                    i++;
                }
                return node.value;
            }
        };
    }

    @Override
    public Object[] toArray() {
        ProjectsNode node = head;
        EmployeeGroup[] arrayOfProjects = new EmployeeGroup[size];
        int k = 0;
        while (node != null) {
            arrayOfProjects[k] = node.value;
            k++;
            node = node.next;
        }
        return arrayOfProjects;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        return (T[]) Arrays.copyOf(toArray(), size, a.getClass());
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
            while (node != null) {
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

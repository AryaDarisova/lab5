package humanResources;

import humanResources.exceptions.*;
import java.time.LocalDate;
import java.util.*;

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
    public boolean add(EmployeeGroup groupable) {
        if (size >= departments.length) {
            EmployeeGroup[] departmentResize = new Department[size * 2];
            System.arraycopy(departments, 0, departmentResize, 0, departments.length);
            this.departments = (Department[]) departmentResize;
        }
        departments[size] = (Department) groupable;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (departments[i].equals(o)) {
                for (int j = i + 1; j < size; j++) {
                    departments[i] = departments[j];
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
    public boolean addAll(Collection<? extends EmployeeGroup> c) {
        if (c.size() + size > departments.length) {
            Department[] groupsResize = new Department[departments.length + c.size()];
            System.arraycopy(departments, 0, groupsResize, 0, departments.length);
            departments = groupsResize;
        }
        for (EmployeeGroup element: c) {
            add(element);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends EmployeeGroup> c) {
        if (c.size() + size > departments.length) {
            Department[] groupsResize = new Department[departments.length + c.size()];
            System.arraycopy(departments, 0, groupsResize, 0, departments.length);
            departments = groupsResize;
        }
        System.arraycopy(departments, index, departments, index + c.size(), c.size());
        int k = index;
        for (EmployeeGroup element : c) {
            departments[k] = (Department) element;
            k++;
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
        for (EmployeeGroup element: departments) {
            if (!c.contains(element))
                remove(element);
        }
        return true;
    }

    @Override
    public void clear() {
        for (EmployeeGroup element: departments) {
            element = null;
        }
        size = 0;
    }

    @Override
    public EmployeeGroup get(int index) {
        return departments[index];
    }

    @Override
    public EmployeeGroup set(int index, EmployeeGroup element) {
        Department getGroup = departments[index];
        departments[index] = (Department) element;
        return getGroup;
    }

    @Override
    public void add(int index, EmployeeGroup element) {
        if (size + 1 >= departments.length) {
            Department[] groupsResize = new Department[departments.length + 1];
            System.arraycopy(departments, 0, groupsResize, 0, departments.length);
            departments = groupsResize;
        }
        System.arraycopy(departments, index, departments, index + 1, size - index);
        departments[index] = (Department) element;
    }

    @Override
    public EmployeeGroup remove(int index) {
        EmployeeGroup removedElement = departments[index];
        System.arraycopy(departments, index + 1, departments, index, size - index);
        size--;
        return removedElement;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (departments[i].equals(o))
                return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size; i > 0; i--) {
            if (departments[i].equals(o))
                return i;
        }
        return -1;
    }

    @Override
    public ListIterator<EmployeeGroup> listIterator() {
        return listIterator(-1);
    }

    @Override
    public ListIterator<EmployeeGroup> listIterator(int index) {
        return new ListIterator<EmployeeGroup>() {
            int current = index;

            @Override
            public boolean hasNext() {
                return current < size;
            }

            @Override
            public EmployeeGroup next() {
                if (hasNext())
                    return departments[current++];
                return departments[current];
            }

            @Override
            public boolean hasPrevious() {
                return current > 0;
            }

            @Override
            public EmployeeGroup previous() {
                if (hasPrevious())
                    return departments[current--];
                return departments[current];
            }

            @Override
            public int nextIndex() {
                if (hasNext())
                    return current + 1;
                return current;
            }

            @Override
            public int previousIndex() {
                if (hasPrevious())
                    return current - 1;
                return current;
            }

            @Override
            public void remove() {
                DepartmentsManager.this.remove(current);
            }

            @Override
            public void set(EmployeeGroup group) {
                DepartmentsManager.this.set(current, group);
            }

            @Override
            public void add(EmployeeGroup group) {
                DepartmentsManager.this.add(current, group);
            }
        };
    }

    @Override
    public List<EmployeeGroup> subList(int fromIndex, int toIndex) {
        List<EmployeeGroup> subList = new DepartmentsManager(this.name, toIndex - fromIndex);
        subList.addAll(Arrays.asList(departments).subList(fromIndex, toIndex));
        return subList;
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
    public int removeQuantity(EmployeeGroup group) {
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
        Department getEmployeeGroup;
        for (Department element: departments) {
            if (element.getName().equals(name)) {
                getEmployeeGroup = element;
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
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Department element: departments) {
            if (element.equals(o))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<EmployeeGroup> iterator() {
        return new Iterator<EmployeeGroup>() {
            int i = -1;

            @Override
            public boolean hasNext() {
                return i < size;
            }

            @Override
            public EmployeeGroup next() {
                if (hasNext())
                    return departments[i++];
                return departments[i];
            }
        };
    }

    @Override
    public Object[] toArray() {
        Department[] groupsInArray = new Department[size];
        System.arraycopy(departments, 0, groupsInArray, 0, size);
        return groupsInArray;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            return (T[]) Arrays.copyOf(departments, size, a.getClass());
        System.arraycopy(departments, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    /*
    возвращающий общее число сотрудников в организации.
    */

    @Override
    public int employeesQuantity() {
        int employeesQuantity = 0;
        for (Department element: departments) {
            employeesQuantity += element.size();
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
        for (Department element: departments) {
            employeesQuantity += element.employeesQuatityByJob(jobTitle);
        }
        return employeesQuantity;
    }

    /*
    возвращающий сотрудника с максимальной заработной платой в организации
    */

    @Override
    public Employee mostValuableEmployee() {
        Employee bestEmployee;
        bestEmployee = departments[0].getEmployees()[0];
        for (Department element: departments) {
            if (element.mostValuableEmployee().getSalary() > bestEmployee.getSalary()) {
                bestEmployee = element.mostValuableEmployee();
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
        for (Department element: departments) {
            if (element.hasEmployee(firstName, secondName)) {
                getEmployeesDepartment = element;
            }
        }
        return getEmployeesDepartment;
    }

    @Override
    public void setBonus() {
        for (Department element: departments) {
            element.bonusForBusinessTravellers();
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
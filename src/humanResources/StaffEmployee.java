package humanResources;

import humanResources.exceptions.IllegalDatesException;

import java.time.LocalDate;
import java.util.*;

public class StaffEmployee extends Employee implements BusinessTraveller{
    private int bonus;
    private ListNode head;
    private ListNode tail;
    private int travelsQuantity;

    private static final JobTitlesEnum JOB_TITLE = JobTitlesEnum.NONE;
    private static final int SALARY = 0;
    private static final int BONUS = 0;

    /*
    Конструкторы:
    - принимающий два параметра – имя и фамилию. Должность при этом
инициализируется значением NONE, заработная плата и премия – 0; список командировок – пустой.
    - принимающий четыре параметра – имя, фамилия, должность и заработная плата.
Список командировок – пустой. Премия – 0.
    - принимающий четыре параметра – имя, фамилия, должность, заработная плата,
массив командировок. Список инициируется значениями из массива. Премия – 0.
     */

    public StaffEmployee(String firstName, String secondName) {
        super(firstName, secondName, JOB_TITLE, SALARY);
        bonus = BONUS;
    }

    public StaffEmployee(String firstName, String secondName, JobTitlesEnum jobTitle, int salary) {
        super(firstName, secondName, jobTitle, salary);
        bonus = BONUS;
    }

    public StaffEmployee(String firstName, String secondName, JobTitlesEnum jobTitle, int salary, BusinessTravel[] travels) throws IllegalDatesException {
        super(firstName, secondName, jobTitle, salary);
        for (BusinessTravel temp : travels) {
            add(temp);
            travelsQuantity++;
        }
        bonus = BONUS;
    }

    /*
    Методы:
    - реализация метода, возвращающего размер премии.
    - реализация метода, устанавливающего размер премии.
    - добавляющий информацию о командировке (в качестве параметра принимает ссылку
на экземпляр класса BusinessTravel).
    - возвращающий массив командировок.
     */

    @Override
    public int getBonus() {
        return bonus;
    }

    @Override
    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    private void addNode(ListNode node) {
        if (head == null) {
            head = node;
            tail = node;
        }
        else {
            tail.next = node;
            tail = node;
        }
        travelsQuantity++;
    }

    @Override
    public BusinessTravel[] getTravels() {
        BusinessTravel[] getTravels = new BusinessTravel[travelsQuantity];
        ListNode node = head;
        int k = 0;
        while (node != null) {
            getTravels[k++] = node.value;
            node = node.next;
        }
        return getTravels;
    }

    @Override
    public SortedSet<BusinessTravel> subSet(BusinessTravel fromElement, BusinessTravel toElement) {
        SortedSet<BusinessTravel> subList = new StaffEmployee(this.getFirstName(), this.getSecondName());
        ListNode node = head;
        int indexFromElement = 0, indexToElement = 0;
        for (int i = 0; i < travelsQuantity; i++) {
            if (node.value == fromElement) {
                indexFromElement = i;
            }
            if (node.value == toElement) {
                indexToElement = i;
            }
            node = node.next;
        }
        int index = 0;
        while (node != null) {
            if (index >= indexFromElement && index <= indexToElement) {
                subList.add(node.value);
            }
            index++;
            node = node.next;
        }
        return subList;
    }

    @Override
    public SortedSet<BusinessTravel> headSet(BusinessTravel toElement) {
        SortedSet<BusinessTravel> headSet = new StaffEmployee("", "");
        ListNode node = head;
        int indexToElement = 0;
        for (int i = 0; i < travelsQuantity; i++) {
            if (node.value == toElement) {
                indexToElement = i;
            }
            node = node.next;
        }
        int index = 0;
        while (node != null) {
            if (index <= indexToElement) {
                headSet.add(node.value);
            }
            index++;
            node = node.next;
        }
        return headSet;
    }

    @Override
    public SortedSet<BusinessTravel> tailSet(BusinessTravel fromElement) {
        SortedSet<BusinessTravel> tailSet = new StaffEmployee("", "");
        ListNode node = head;
        int indexFromElement = 0;
        for (int i = 0; i < travelsQuantity; i++) {
            if (node.value == fromElement) {
                indexFromElement = i;
            }
            node = node.next;
        }
        int index = 0;
        while (node != null) {
            if (index >= indexFromElement) {
                tailSet.add(node.value);
            }
            index++;
            node = node.next;
        }
        return tailSet;
    }

    @Override
    public BusinessTravel first() {
        ListNode node = head;
        return node.value;
    }

    @Override
    public BusinessTravel last() {
        ListNode node = tail;
        return node.value;
    }

    private class ListNode {
        ListNode next, previous;
        BusinessTravel value;

        ListNode(BusinessTravel value) {
            this.value = value;
        }

    }

    //“<secondName> <firstName>, <jobTitle>, <salary>р., <bonus>р.
    //Командировки:
    //{<BusinessTravelsInfo>}”

    @Override
    public StringBuilder getString() {
        StringBuilder line = new StringBuilder();
        line.append(super.toString()).append(", ");
        if (bonus != 0) {
            line.append(bonus).append("p.\n");
        }
        line.append("Командировки:\n");
        ListNode travels = head;
        if (head != null) {
            for (int i = 0; i < travelsQuantity; i++) {
                line.append(travels.value.toString()).append("\n");
                travels = travels.next;
            }
        }
        return line;
    }

    @Override
    public int size() {
        return travelsQuantity;
    }

    @Override
    public boolean isEmpty() {
        return travelsQuantity == 0;
    }

    @Override
    public boolean contains(Object o) {
        ListNode node = head;
        while (node != null) {
            if (node.value.equals(o))
                return true;
            node = node.next;
        }
        return false;
    }

    @Override
    public Comparator<? super BusinessTravel> comparator() {
        return new sortTravels();
    }

    @Override
    public Iterator<BusinessTravel> iterator() {
        return new Iterator<BusinessTravel>() {
            ListNode node = head;
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < size();
            }

            @Override
            public BusinessTravel next() {
                if (hasNext()) {
                    node = node.next;
                    i++;
                }
                return node.value;
            }
        };
    }

    @Override
    public Object[] toArray() {
        BusinessTravel[] arrayOfTravels = new BusinessTravel[travelsQuantity];
        ListNode node = head;
        int k = 0;
        while (node != null) {
            arrayOfTravels[k] = node.value;
            node = node.next;
            k++;
        }
        return arrayOfTravels;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        T[] arrayOfTravels = (T[]) toArray();
        if (a.length < travelsQuantity)
            return (T[]) Arrays.copyOf(arrayOfTravels, travelsQuantity, a.getClass());
        System.arraycopy(arrayOfTravels, 0, a, 0, travelsQuantity);
        if (a.length > travelsQuantity)
            a[travelsQuantity] = null;
        return a;
    }

    @Override
    public boolean add(BusinessTravel travel) {
        //т.е. если пересечения в датах метод isOnTrip не имеется (возращает значение 0),
        //то мы будем добавлять эту поездку
        if (isOnTrip(travel.getStartTrip(), travel.getEndTrip()) == 0) {
            ListNode current = head;
            ListNode previous = null;
            while (current != null) {
                //если конец рассматриваемой поездки раньше начала добавляемой, то смотрим
                //не раньше ли конец и следующей поездки, потому что если у следующей тоже
                //раньше, то мы должны current.next и уже его начинвать заново рассматривать
                if (current.value.getEndTrip().isBefore(travel.getStartTrip())) {
                    if (!current.next.value.getStartTrip().isBefore(travel.getEndTrip())) {
                        ListNode nodeElement = new ListNode(travel);
                        nodeElement.next = current;
                        if (previous != null) {
                            previous.next = nodeElement;
                        }
                        travelsQuantity++;
                    }
                }
                previous = current;
                current = current.next;
            }
        }
        else {
            throw new IllegalDatesException("Dates of travel that you would to add intersects " +
                    "with the travel dates of this Employee! ");
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode current = head;
        ListNode previous = null;

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

    private boolean removeNode(ListNode previous, ListNode current){
        if (previous != null) {
            previous.next = current.next;
        }
        travelsQuantity--;
        return true;
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
    public boolean addAll(Collection<? extends BusinessTravel> c) {
        for (BusinessTravel element : c) {
            add(element);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        ListNode current = head;
        ListNode previous = null;
        while (current != null) {
            if (!c.contains(current.value)) {
                removeNode(previous, current);
                current = previous.next;
            } else {
                previous = current;
                current = current.next;
            }

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
    public void clear() {
        ListNode current = head;
        ListNode next;
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
    public boolean equals(Object obj)  {
        if (super.equals(obj)) {
            StaffEmployee equalsEmployee = (StaffEmployee) obj;
            ListNode current = head;
            while(current != null) {
                if (this.travelsQuantity != equalsEmployee.travelsQuantity)
                    return false;
                current = current.next;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode() ^ bonus ^ travelsQuantity;;
        ListNode travels = head;
        while (travels != null) {
            hash ^= travels.value.hashCode();
            travels = travels.next;
        }
        return hash;
    }

    /*
    возвращающий логическое значение – находится ли сотрудник в данный
момент в командировке
     */

    @Override
    public boolean isOnTrip() {
        for (BusinessTravel x: this.getTravels()) {
            if (LocalDate.now().isAfter(x.getStartTrip()) & LocalDate.now().isBefore(x.getEndTrip()))
                return true;
        }
        return false;
    }

    /*
    Проверяющий, находился ли сотрудник в заданный период времени
(принимает параметры – даты начала и конца проверяемого периода времени).
Метод возвращает число дней из заданного периода в течение которых
сотрудник находился в командировке
     */

    @Override
    public int isOnTrip(LocalDate startTrip, LocalDate endTrip) {
        int countDay = 0;
        LocalDate day = startTrip;
        for (int i = 0; i < (endTrip.getDayOfYear() - startTrip.getDayOfYear() + 1); i++) {
            for (BusinessTravel x: this.getTravels()) {
                LocalDate dayTravel = x.getStartTrip();
                for (int j = 0; j < x.getDaysCount(); j++) {
                    if (day.isEqual(dayTravel))
                        countDay++;
                    dayTravel = dayTravel.plusDays(1);
                }
            }
            day = day.plusDays(1);
        }
        return countDay;
    }
}

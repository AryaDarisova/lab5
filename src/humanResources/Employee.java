package humanResources;

public abstract class Employee implements Comparable {
    private String firstName;
    private String secondName;
    private JobTitlesEnum jobTitle;
    private int salary;

    private static final JobTitlesEnum JOB_TITLE = JobTitlesEnum.NONE;
    private static final int SALARY = 0;

    /*
    Конструкторы:
    - принимающий два параметра – имя и фамилию. Должность при этом инициализируется пустой строкой, а заработная плата – 0;
    - принимающий четыре параметра – имя, фамилия, должность и заработная плата.
    */

    protected Employee(String firstName, String secondName) {
        this(firstName, secondName, JOB_TITLE, SALARY);
    }

    protected Employee(String firstName, String secondName, JobTitlesEnum jobTitle, int salary) {
        if(salary < 0)
            throw new java.lang.IllegalArgumentException("Salary can't be less than zero!");
        this.firstName = firstName;
        this.secondName = secondName;
        this.jobTitle = jobTitle;
        this.salary = salary;
    }

    /*
    Методы:
    - возвращающий имя.
    - устанавливающий новое значение имени.
    - возвращающий фамилию.
    - устанавливающий новое значение фамилии.
    - возвращающий должность.
    - устанавливающий новое значение должности.
    - возвращающий заработную плату.
    - устанавливающий новое значение заработной платы.
     */

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public JobTitlesEnum getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitlesEnum jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public abstract int getBonus();

    public abstract void setBonus(int bonus);

    /*
    Переопределите методы класса Object:
     public String toString(). Возвращаемая строка составляется в формате:
    “<secondName> <firstName>, <jobTitle>, <salary>р.”
    При этом в результирующую строку включаются только поля с нормальными значениями
    (пустые строки, null, NONE и 0 – не включаются).
     public boolean equals(Object obj). 2 объекта считаются равными (equal), если у них все
    поля совпадают.
     public int hashCode(). Вычисляется как последовательное “исключающее или” хэш-
    кодов всех полей.
     */

    @Override
    public String toString(){
        //“<secondName> <firstName>, <jobTitle>, <salary>р.”
        return String.format("%s %s %s %dр.", secondName, firstName, jobTitle.toString(), salary);
    }

    public StringBuilder getString(){
        return new StringBuilder(this.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(this.getClass() == obj.getClass())) {
            return false;
        }
        Employee matchedEmployee = (Employee) obj;
        return (this.secondName.equals(matchedEmployee.secondName)) &&
                (this.firstName.equals(matchedEmployee.firstName)) &&
                (this.jobTitle == matchedEmployee.jobTitle) &&
                (this.salary == matchedEmployee.salary);
    }

    @Override
    public int hashCode() {
        return secondName.hashCode() ^ firstName.hashCode() ^ jobTitle.hashCode() ^ salary;
    }

    @Override
    public int compareTo (Employee o) {
        return Integer.compare(salary + getBonus(), o.salary + o.getBonus());
    }
}
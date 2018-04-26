package humanResources;

public class PartTimeEmployee extends Employee {
    private static final JobTitlesEnum JOB_TITLE = JobTitlesEnum.NONE;
    private static final int SALARY = 0;

    /*
    Конструкторы: такие же, как у суперкласса
     */

    public PartTimeEmployee (String firstName, String secondName) {
        super(firstName, secondName, JOB_TITLE, SALARY);
    }

    public PartTimeEmployee (String firstName, String secondName, JobTitlesEnum jobTitle, int salary) {
        super(firstName, secondName, jobTitle, salary);
    }

    /*
    Методы:
    - реализация метода, возвращающего размер премии – возвращается всегда 0.
    - реализация метода, устанавливающего размер премии – ничего не делает.
     */

    public int getBonus() {
        return 0;
    }

    public void setBonus(int bonus) {
    }

    /*
    “<secondName> <firstName>, <jobTitle> (внешний совместитель), <salary>р.”
     */

    @Override
    public String toString() {
        return String.format("%s", super.getString().insert(super.getString().lastIndexOf(","), " (внешний совместитель)"));
    }

    @Override
    public int hashCode(){
        return super.hashCode() ^ getJobTitle().hashCode();
    }
}

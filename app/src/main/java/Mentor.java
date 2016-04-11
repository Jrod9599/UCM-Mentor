/**
 * Created by Alex on 2/28/2016.
 */
public class Mentor extends Student {

    //Skeleton test Java class for Mentor, will be replaced by Database

    private String name;
    private String email;
    private int maxMentees; //Maximum mentees this mentor can have
    private int numMentees = 0; //Current amount of assigned mentees
    private String alias; //Name that the mentee's see
    private Mentee mentees[]; //array holding all mentees

    public Mentor()
    {
        name = " ";
        email = " ";
        alias = " ";
        maxMentees = 8;
    }

    public Mentor(String name, String email, String alias)
    {
        this.name = name;
        this.email = email;
        this.alias = alias;
        maxMentees = 8;
    }

    public Mentor(String name, String email, String alias, int max)
    {
        this.name = name;
        this.email = email;
        this.alias = alias;
        maxMentees = max;
    }

    void changeMax(int newMax)
    {
        maxMentees = newMax;
    }

    void updateMentees()
    {
        numMentees++;
    }


    boolean notCapacityyMentees()
    {
       return (numMentees < maxMentees);
    }

    void setMentee(Mentee newMentee)
    {
        if(notCapacityyMentees()) {
            mentees[numMentees] = newMentee;
            updateMentees();
        }
    }


}

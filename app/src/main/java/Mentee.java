/**
 * Created by Alex on 2/28/2016.
 */
public class Mentee extends Student {

    //Skeleton test Java class for Mentee, will be replaced by Database

    private boolean hasMentor = false; //boolean statement to state whether a Mentor has been assigned or not
    private Mentor mentor; //Holds what is the Matched Mentor may not be used
    private String name; //Name of Mentee
    private String email; //Email of Mentee

    public Mentee()
    {
        name = " ";
        email = " ";
    }

    public Mentee(String name, String email)
    {
        this.name = name;
        this.email = email;
    }

    void matchMentor(Mentor preferredMentor)
    {
        if(preferredMentor.notCapacityyMentees())
        {
            hasMentor = true;
            preferredMentor.setMentee(this);
            mentor = preferredMentor;
        }
    }


}

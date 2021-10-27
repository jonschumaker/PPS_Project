package bean;

public class People {
    private static final String String = null;
	protected String emailid;
    protected String password;
    protected String password2;
    protected String fName;
    protected String lname;
    protected String address;
    protected String birthday;
 
    public People() {
    }
 
    public People(String emailid) {
        this.emailid = emailid;
    }
 
    public People(String emailid,String password,String password2, String fName,String lname, String address, String birthday) {
       // this(String password,String password2, String fName,String lname, String address, String birthday);
        this.emailid =emailid;
    }
     
    public People(String password,String password2, String fName,String lname, String address, String birthday) {
       this.password=password;
       this.password2=password;
        this.fName = fName;
        this.lname=lname;
        this.address = address;
        this.birthday = birthday;
    }
 
    public String getemailid() {
        return emailid;
    }
 
    public void setemailid(String emailid) {
        this.emailid = emailid;
    }
 
    public String getfName() {
        return fName;
    }
 
    public void setfName(String fName) {
        this.fName = fName;
    }
 

    public String getlname() {
        return lname;
    }
 
    public void setlname(String lname) {
        this.lname = lname;
    }

  public String getpassword() {
        return password;
    }
 
    public void setpassword(String password) {
        this.password = password;
    }

  public String getpassword2() {
        return password2;
    }
 
    public void setpassword2(String password2) {
        this.password2 = password2;
    }
    public String getAddress() {
        return address;
    }
 
    public void setAddress(String address) {
        this.address = address;
    }
 
    public String getbirthday() {
        return birthday;
    }
 
    public void setbirthday(String birthday) {
        this.birthday = birthday;
    }
}
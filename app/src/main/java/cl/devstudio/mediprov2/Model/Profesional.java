package cl.devstudio.mediprov2.Model;

public class Profesional {
    private String rut;
    private String names;
    private String last_name1;
    private String last_name2;
    private String password;
    private String email;
    private String address;
    private String phone;
    private String sexo;
    private String birth_date;



    public Profesional(String rut, String names, String last_name1, String last_name2,
                       String password, String email, String address, String phone, String sexo, String birth_date) {
        this.rut = rut;
        this.names = names;
        this.last_name1 = last_name1;
        this.last_name2 = last_name2;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.sexo = sexo;
        this.birth_date = birth_date;
    }

    public Profesional() {

    }


    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLast_name1() {
        return last_name1;
    }

    public void setLast_name1(String last_name1) {
        this.last_name1 = last_name1;
    }

    public String getLast_name2() {
        return last_name2;
    }

    public void setLast_name2(String last_name2) {
        this.last_name2 = last_name2;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}

package org.stanislav.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * @author Stanislav Hlova
 */
public class Employee {
    private int id;
    private String name;
    private String telephoneNumber;
    private MaritalStatus maritalStatus;
    private Date dateOfBirthday;
    private String address;
    private BigDecimal salary;
    private Position position;

    public Employee() {
    }

    private Employee(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.telephoneNumber = builder.telephoneNumber;
        this.maritalStatus = builder.maritalStatus;
        this.dateOfBirthday = builder.dateOfBirthday;
        this.address = builder.address;
        this.salary = builder.salary;
        this.position = builder.position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Date getDateOfBirthday() {
        return dateOfBirthday;
    }

    public void setDateOfBirthday(Date dateOfBirthday) {
        this.dateOfBirthday = dateOfBirthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != employee.id) return false;
        if (!Objects.equals(name, employee.name)) return false;
        if (!Objects.equals(telephoneNumber, employee.telephoneNumber))
            return false;
        if (maritalStatus != employee.maritalStatus) return false;
        if (!Objects.equals(dateOfBirthday, employee.dateOfBirthday))
            return false;
        if (!Objects.equals(address, employee.address)) return false;
        if (!Objects.equals(salary, employee.salary)) return false;
        return position == employee.position;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (telephoneNumber != null ? telephoneNumber.hashCode() : 0);
        result = 31 * result + (maritalStatus != null ? maritalStatus.hashCode() : 0);
        result = 31 * result + (dateOfBirthday != null ? dateOfBirthday.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (salary != null ? salary.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", maritalStatus=" + maritalStatus +
                ", dateOfBirthday=" + dateOfBirthday +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                ", position=" + position +
                '}';
    }

    public static class Builder<T extends Builder<T>> {
        private int id;
        private String name;
        private String telephoneNumber;
        private MaritalStatus maritalStatus;
        private Date dateOfBirthday;
        private String address;
        private BigDecimal salary;
        private Position position;

        public T id(int id) {
            this.id = id;
            return self();
        }

        public T name(String name) {
            this.name = name;
            return self();
        }

        public T telephoneNumber(String telephoneNumber) {
            this.telephoneNumber = telephoneNumber;
            return self();
        }

        public T maritalStatus(MaritalStatus maritalStatus) {
            this.maritalStatus = maritalStatus;
            return self();
        }

        public T dateOfBirthday(Date dateOfBirthday) {
            this.dateOfBirthday = dateOfBirthday;
            return self();
        }

        public T address(String address) {
            this.address = address;
            return self();
        }

        public T salary(BigDecimal salary) {
            this.salary = salary;
            return self();
        }

        public T position(Position position) {
            this.position = position;
            return self();
        }

        public Employee build() {
            return new Employee(this);
        }

        protected T self() {
            @SuppressWarnings("unchecked")
            T builder = (T) this;
            return builder;
        }
    }
}

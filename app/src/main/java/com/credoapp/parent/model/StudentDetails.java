package com.credoapp.parent.model;

import org.parceler.Parcel;

@Parcel
public class StudentDetails {
    public String id;
    public String name;
    public String birthday;
    public String date_of_join;
    public String gender;
    public String religion;
    public String nationality;
    public String academic_year_id;
    public String academic_year;
    public String mother_name;
    public String mobile_number;
    public String roll_number;
    public String photo;
    public String class_id;
    public String class_name;
    public String student_address;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDate_of_join() {
        return date_of_join;
    }

    public void setDate_of_join(String date_of_join) {
        this.date_of_join = date_of_join;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAcademic_year_id() {
        return academic_year_id;
    }

    public void setAcademic_year_id(String academic_year_id) {
        this.academic_year_id = academic_year_id;
    }

    public String getAcademic_year() {
        return academic_year;
    }

    public void setAcademic_year(String academic_year) {
        this.academic_year = academic_year;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getRoll_number() {
        return roll_number;
    }

    public void setRoll_number(String roll_number) {
        this.roll_number = roll_number;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }


    public String getStudent_address() {
        return student_address;
    }

    public void setStudent_address(String student_address) {
        this.student_address = student_address;
    }

    @Override
    public String toString() {
        return "StudentDetails{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", date_of_join='" + date_of_join + '\'' +
                ", gender='" + gender + '\'' +
                ", religion='" + religion + '\'' +
                ", nationality='" + nationality + '\'' +
                ", academic_year_id='" + academic_year_id + '\'' +
                ", academic_year='" + academic_year + '\'' +
                ", mother_name='" + mother_name + '\'' +
                ", mobile_number='" + mobile_number + '\'' +
                ", roll_number='" + roll_number + '\'' +
                ", photo='" + photo + '\'' +
                ", class_id='" + class_id + '\'' +
                ", class_name='" + class_name + '\'' +
                ", student_address='" + student_address + '\'' +
                '}';
    }
}

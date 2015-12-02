package com.jph.xibaibai.model.entity;

import java.io.Serializable;

/**
 * 汽车品牌
 * Created by jph on 2015/9/21.
 */
public class CarBrand implements Serializable{

    private static final long serialVersionUID = 3660775387925713907L;
    private int id;
    private String first_letter;
    private String make_name;
    private String country;
    private int state;
    private int placeholder;

    private String sortLetters;// 拼音首字母

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_letter() {
        return first_letter;
    }

    public void setFirst_letter(String first_letter) {
        this.first_letter = first_letter;
    }

    public String getMake_name() {
        return make_name;
    }

    public void setMake_name(String make_name) {
        this.make_name = make_name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(int placeholder) {
        this.placeholder = placeholder;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarBrand carBrand = (CarBrand) o;

        if (id != carBrand.id) return false;
        if (state != carBrand.state) return false;
        if (placeholder != carBrand.placeholder) return false;
        if (first_letter != null ? !first_letter.equals(carBrand.first_letter) : carBrand.first_letter != null)
            return false;
        if (make_name != null ? !make_name.equals(carBrand.make_name) : carBrand.make_name != null)
            return false;
        if (country != null ? !country.equals(carBrand.country) : carBrand.country != null)
            return false;
        return !(sortLetters != null ? !sortLetters.equals(carBrand.sortLetters) : carBrand.sortLetters != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (first_letter != null ? first_letter.hashCode() : 0);
        result = 31 * result + (make_name != null ? make_name.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + state;
        result = 31 * result + placeholder;
        result = 31 * result + (sortLetters != null ? sortLetters.hashCode() : 0);
        return result;
    }
}

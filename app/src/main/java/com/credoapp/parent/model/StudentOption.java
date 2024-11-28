package com.credoapp.parent.model;

public class StudentOption {
    public String optionName;
    public int resourceName;

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public int getResourceName() {
        return resourceName;
    }

    public void setResourceName(int resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String toString() {
        return "StudentOption{" +
                "optionName='" + optionName + '\'' +
                ", resourceName=" + resourceName +
                '}';
    }
}

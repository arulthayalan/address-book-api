package au.com.xyz.addressbook.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@ApiModel("Address book phone contact")
public class PhoneContact {

    @ApiModelProperty(value = "Contact name", required = true)
    @NotNull
    private String name;

    @ApiModelProperty(value = "Contact mobile number", required = true, position = 1)
    @NotNull
    private String mobileNumber;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneContact that = (PhoneContact) o;
        return that.name != null && that.name.equalsIgnoreCase(name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

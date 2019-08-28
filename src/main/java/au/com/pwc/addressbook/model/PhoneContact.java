package au.com.pwc.addressbook.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

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
}

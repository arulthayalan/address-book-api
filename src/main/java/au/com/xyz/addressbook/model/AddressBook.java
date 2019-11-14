package au.com.xyz.addressbook.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@ApiModel("Phone address book ")
public class AddressBook {

    @ApiModelProperty(value = "Address book name. Name must be unique", required = true)
    @NotNull
    private String name;

    @ApiModelProperty(value = "Address book contact", required = true)
    @Valid
    @Size(min = 1)
    private List<PhoneContact> phoneContacts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PhoneContact> getPhoneContacts() {
        return phoneContacts;
    }

    public void setPhoneContacts(List<PhoneContact> phoneContacts) {
        this.phoneContacts = phoneContacts;
    }
}

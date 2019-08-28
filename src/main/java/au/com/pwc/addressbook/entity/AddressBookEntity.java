package au.com.pwc.addressbook.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "address_book")
public class AddressBookEntity {

    @Id
    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_book_name")
    private List<PhoneContactEntity> phoneContacts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PhoneContactEntity> getPhoneContacts() {
        return phoneContacts;
    }

    public void setPhoneContacts(List<PhoneContactEntity> phoneContacts) {
        this.phoneContacts = phoneContacts;
    }
}

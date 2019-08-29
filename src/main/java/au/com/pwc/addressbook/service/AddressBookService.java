package au.com.pwc.addressbook.service;

import au.com.pwc.addressbook.model.AddressBook;
import au.com.pwc.addressbook.model.PhoneContact;

import java.util.List;

public interface AddressBookService {

    AddressBook saveAddressBook(AddressBook addressBook);

    List<AddressBook> getAllAddressBooks();

    List<PhoneContact> getUniquePhoneContacts();
}

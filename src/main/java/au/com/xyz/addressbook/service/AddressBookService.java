package au.com.xyz.addressbook.service;

import au.com.xyz.addressbook.model.AddressBook;
import au.com.xyz.addressbook.model.PhoneContact;

import java.util.List;

public interface AddressBookService {

    AddressBook saveAddressBook(AddressBook addressBook);

    List<AddressBook> fetchAllAddressBooks();

    List<PhoneContact> fetchAllPhoneContacts();

    List<PhoneContact> fetchUniquePhoneContacts();
}

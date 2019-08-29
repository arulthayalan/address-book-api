package au.com.pwc.addressbook.service;

import au.com.pwc.addressbook.configuration.TransferObjectMapper;
import au.com.pwc.addressbook.entity.AddressBookEntity;
import au.com.pwc.addressbook.model.AddressBook;
import au.com.pwc.addressbook.model.PhoneContact;
import au.com.pwc.addressbook.respository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

@Component
public class AddressBookServiceImpl implements AddressBookService {

    private AddressBookRepository addressBookRepository;

    private TransferObjectMapper transferObjectMapper;

    @Autowired
    public AddressBookServiceImpl(AddressBookRepository addressBookRepository, TransferObjectMapper transferObjectMapper) {
        this.addressBookRepository = addressBookRepository;
        this.transferObjectMapper = transferObjectMapper;
    }

    @Override
    public AddressBook saveAddressBook(AddressBook addressBook) {
        Assert.notNull(addressBook, "Address book object can't be null");

        AddressBookEntity addressBookEntity =  addressBookRepository.saveAndFlush(transferObjectMapper.toAddressBookEntity(addressBook));

        return transferObjectMapper.toAddressBook(addressBookEntity);
    }

    @Override
    public List<AddressBook> getAllAddressBooks() {
        List<AddressBookEntity> entities = addressBookRepository.findAll();
        return transferObjectMapper.toAddressBook(entities);
    }

    @Override
    public List<PhoneContact> getUniquePhoneContacts() {
        List<AddressBook> addressBooks = getAllAddressBooks();

        addressBooks.stream().
    }
}

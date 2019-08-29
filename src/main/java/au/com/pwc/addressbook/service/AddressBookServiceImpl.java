package au.com.pwc.addressbook.service;

import au.com.pwc.addressbook.configuration.TransferObjectMapper;
import au.com.pwc.addressbook.entity.AddressBookEntity;
import au.com.pwc.addressbook.model.AddressBook;
import au.com.pwc.addressbook.model.PhoneContact;
import au.com.pwc.addressbook.respository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

        AddressBookEntity toBeSaved = transferObjectMapper.toAddressBookEntity(addressBook);
        AddressBookEntity addressBookEntity = addressBookRepository.saveAndFlush(toBeSaved);

        return transferObjectMapper.toAddressBook(addressBookEntity);
    }

    @Override
    public List<AddressBook> fetchAllAddressBooks() {
        List<AddressBookEntity> entities = addressBookRepository.findAll();
        return transferObjectMapper.toAddressBook(entities);
    }

    @Override
    public List<PhoneContact> fetchAllPhoneContacts() {
        List<AddressBook> addressBooks = fetchAllAddressBooks();

        return addressBooks.stream()
                .map(a -> a.getPhoneContacts())
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(PhoneContact::getName))
                .collect(Collectors.toList());
    }

    @Override
    public List<PhoneContact> fetchUniquePhoneContacts() {
        return fetchAllPhoneContacts()
                .stream()
                .collect(Collectors.groupingBy(PhoneContact::getName))
                .entrySet()
                .stream()
                .filter(e -> e.getValue().size() == 1)
                .map(e -> e.getValue())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}

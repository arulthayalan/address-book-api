package au.com.xyz.addressbook.service

import au.com.xyz.addressbook.configuration.TransferObjectMapper
import au.com.xyz.addressbook.entity.AddressBookEntity
import au.com.xyz.addressbook.entity.PhoneContactEntity
import au.com.xyz.addressbook.model.AddressBook
import au.com.xyz.addressbook.model.PhoneContact
import au.com.xyz.addressbook.respository.AddressBookRepository
import au.com.xyz.addressbook.configuration.TransferObjectMapperImpl
import spock.lang.Specification
import spock.lang.Subject

class AddressBookServiceImplSpec extends Specification {

    AddressBookRepository addressBookRepository

    TransferObjectMapper transferObjectMapper

    @Subject
    AddressBookServiceImpl addressBookService

    def setup() {
        transferObjectMapper = new TransferObjectMapperImpl()
        addressBookRepository = Mock(AddressBookRepository.class)
        addressBookService = new AddressBookServiceImpl(addressBookRepository, transferObjectMapper)
    }

    def "Should save address book entity"() {

        given:
        AddressBook addressBook = new AddressBook([name         : 'mybook',
                                                   phoneContacts: [new PhoneContact(name: 'Bob', mobileNumber: '043456789')]])

        and:
        AddressBookEntity entity = new AddressBookEntity([name         : 'mybook',
                                                          phoneContacts: [new PhoneContactEntity(name: 'Bob', mobileNumber: '043456789')]])

        when:
        AddressBook actualAddressBook = addressBookService.saveAddressBook(addressBook)

        then:
        assert actualAddressBook != null
        assert actualAddressBook.name == 'mybook'
        assert actualAddressBook.phoneContacts.size() == 1

        interaction {
            1 * addressBookRepository.saveAndFlush(_ as AddressBookEntity) >> entity
        }
    }

    def "Should return all address books and contacts"() {
        when:
        List<AddressBook> addressBooks = addressBookService.fetchAllAddressBooks()

        then:
        assert addressBooks.size() == 2
        interaction {
            1 * addressBookRepository.findAll() >> [new AddressBookEntity(), new AddressBookEntity()]
        }
    }

    def "Should return all phone contacts from all address books"() {
        given:
        AddressBookEntity addressBookEntity_1 = new AddressBookEntity([name: 'book1', phoneContacts: book1PhoneContactEntities()])
        AddressBookEntity addressBookEntity_2 = new AddressBookEntity([name: 'book2', phoneContacts: book2PhoneContactEntities()])


        when:
        List<PhoneContact> phoneContacts = addressBookService.fetchAllPhoneContacts()

        then:
        assert phoneContacts.size() == 6
        assert phoneContacts.groupBy {it.name}.size() == 4

        interaction {
            1 * addressBookRepository.findAll() >> [addressBookEntity_1, addressBookEntity_2]
        }
    }

    def "Should return unique phone contacts from all address books"() {
        given:
        AddressBookEntity addressBookEntity_1 = new AddressBookEntity([name: 'book1', phoneContacts: book1PhoneContactEntities()])
        AddressBookEntity addressBookEntity_2 = new AddressBookEntity([name: 'book2', phoneContacts: book2PhoneContactEntities()])

        when:
        List<PhoneContact> phoneContacts = addressBookService.fetchUniquePhoneContacts()

        then:
        assert phoneContacts.size() == 2
        assert phoneContacts.groupBy {it.name}.size() == 2

        phoneContacts.each { assert it.name == 'Bob' || it.name == 'John'}

        interaction {
            1 * addressBookRepository.findAll() >> [addressBookEntity_1, addressBookEntity_2]
        }
    }


    def List<PhoneContactEntity> book1PhoneContactEntities() {
        [new PhoneContactEntity([name: 'Bob', mobileNumber: '123456']),
         new PhoneContactEntity([name: 'Mary', mobileNumber: '123456']),
         new PhoneContactEntity([name: 'Jane', mobileNumber: '123456'])]
    }

    def List<PhoneContactEntity> book2PhoneContactEntities() {
        [new PhoneContactEntity([name: 'Mary', mobileNumber: '123456']),
         new PhoneContactEntity([name: 'John', mobileNumber: '123456']),
         new PhoneContactEntity([name: 'Jane', mobileNumber: '123456'])]
    }

}

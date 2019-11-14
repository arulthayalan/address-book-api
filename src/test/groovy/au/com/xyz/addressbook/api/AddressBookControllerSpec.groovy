package au.com.xyz.addressbook.api

import au.com.xyz.addressbook.model.AddressBook
import au.com.xyz.addressbook.model.AddressBookApiResponse
import au.com.xyz.addressbook.model.PhoneContact
import au.com.xyz.addressbook.model.PhoneContacts
import au.com.xyz.addressbook.service.AddressBookService
import au.com.xyz.addressbook.model.AddressBooks
import spock.lang.Specification
import spock.lang.Subject

class AddressBookControllerSpec extends Specification {

    AddressBookService addressBookService

    @Subject
    AddressBookController addressBookController

    def setup() {
        addressBookService = Mock()
        addressBookController = new AddressBookController(addressBookService)
    }

    def "Should return all address books"() {
        given:
        List addressBooks = [new AddressBook([name: 'book1']), new AddressBook(name: 'book2')]

        when:
        AddressBookApiResponse<AddressBooks> response = addressBookController.getAllAddressBooks()

        then:
        assert response.data.addressBooks.size() == 2
        interaction {
            1 * addressBookService.fetchAllAddressBooks() >> addressBooks
        }
    }

    def "Should create an address book"() {
        given:
        AddressBook addressBook = new AddressBook([name: 'book1'])

        when:
        AddressBookApiResponse<AddressBook> response = addressBookController.createAnAddressBook(addressBook)

        then:
        assert response.data.name == 'book1'
        interaction {
            1 * addressBookService.saveAddressBook(addressBook) >> addressBook
        }
    }

    def "Should return all phone contacts"() {
        when:
        AddressBookApiResponse<PhoneContacts> response = addressBookController.getPhoneContacts(QueryFilter.ALL)

        then:
        assert response.data.phoneContacts.size() == 2

        interaction {
            1 * addressBookService.fetchAllPhoneContacts() >> [new PhoneContact([name: 'Bob']),
                                                               new PhoneContact([name: 'Mary'])]
            0 * addressBookService.fetchUniquePhoneContacts()
        }
    }

    def "Should return unique phone contact"() {
        when:
        AddressBookApiResponse<PhoneContacts> response = addressBookController.getPhoneContacts(QueryFilter.UNIQUE)

        then:
        assert response.data.phoneContacts.size() == 2

        interaction {
            1 * addressBookService.fetchUniquePhoneContacts() >> [new PhoneContact([name: 'Bob']),
                                                               new PhoneContact([name: 'Mary'])]
            0 * addressBookService.fetchAllPhoneContacts()
        }
    }
}

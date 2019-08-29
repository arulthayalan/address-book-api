package au.com.pwc.addressbook.api;

import au.com.pwc.addressbook.model.AddressBook;
import au.com.pwc.addressbook.model.AddressBookApiResponse;
import au.com.pwc.addressbook.model.AddressBooks;
import au.com.pwc.addressbook.model.PhoneContact;
import au.com.pwc.addressbook.model.PhoneContacts;
import au.com.pwc.addressbook.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Address books")
@RestController
public class AddressBookController {

    private AddressBookService addressBookService;

    @Autowired
    public AddressBookController(AddressBookService addressBookService) {
        this.addressBookService = addressBookService;
    }

    @ApiOperation(value = "Get address books", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(code = 200, message = "List of address books")
    @GetMapping(value = "/addressbooks", produces = MediaType.APPLICATION_JSON_VALUE)
    public AddressBookApiResponse<AddressBooks> getAllAddressBooks() {
        AddressBookApiResponse<AddressBooks> apiResponse = new AddressBookApiResponse<>();
        AddressBooks addressBooks = new AddressBooks();
        addressBooks.setAddressBooks(addressBookService.fetchAllAddressBooks());
        apiResponse.setData(addressBooks);
        return apiResponse;
    }

    @ApiOperation(value = "Add an address book", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(code = 200, message = "Address book is created")
    @PostMapping(value = "/addressbook" ,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AddressBookApiResponse<AddressBook> createAnAddressBook(@Valid @RequestBody AddressBook addressBook) {

        AddressBookApiResponse<AddressBook> addressBookApiResponse = new AddressBookApiResponse();
        addressBookApiResponse.setData(addressBookService.saveAddressBook(addressBook));
        return addressBookApiResponse;
    }

    @ApiOperation(value = "Get contacts from all address books", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(code = 200, message = "List of contacts")
    @GetMapping(value = "/addressbooks/contacts/{queryFilter}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AddressBookApiResponse<PhoneContacts> getPhoneContacts(
            @ApiParam(allowableValues = "ALL, UNIQUE")
            @PathVariable QueryFilter queryFilter) {

        AddressBookApiResponse<PhoneContacts> apiResponse = new AddressBookApiResponse<>();
        PhoneContacts phoneContacts = new PhoneContacts();

        phoneContacts.setPhoneContacts(getPhoneContactsByFilter(queryFilter));
        apiResponse.setData(phoneContacts);
        return apiResponse;
    }

    private List<PhoneContact> getPhoneContactsByFilter(QueryFilter queryFilter) {
        if (queryFilter == QueryFilter.UNIQUE) {
          return addressBookService.fetchUniquePhoneContacts();
        }
        return addressBookService.fetchAllPhoneContacts();
    }

}

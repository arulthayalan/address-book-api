package au.com.pwc.addressbook.api;

import au.com.pwc.addressbook.model.AddressBook;
import au.com.pwc.addressbook.model.AddressBookApiResponse;
import au.com.pwc.addressbook.model.PhoneContact;
import au.com.pwc.addressbook.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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
    public AddressBookApiResponse<List<AddressBook>> addressBooks() {
        AddressBookApiResponse<List<AddressBook>> apiResponse = new AddressBookApiResponse<>();
        apiResponse.setData(addressBookService.getAllAddressBooks());
        return apiResponse;
    }

    @ApiOperation(value = "Add an address book", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(code = 200, message = "Address book is created")
    @PostMapping(value = "/addressbook" ,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AddressBookApiResponse<AddressBook> addressBook(@Valid @RequestBody AddressBook addressBook) {

        AddressBookApiResponse addressBookApiResponse = new AddressBookApiResponse();
        addressBookApiResponse.setData(addressBookService.saveAddressBook(addressBook));
        return addressBookApiResponse;
    }

    @ApiOperation(value = "Get unique contacts from all address books", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(code = 200, message = "List of unique contacts")
    @GetMapping(value = "/addressbooks/contacts", produces = MediaType.APPLICATION_JSON_VALUE)
    public AddressBookApiResponse<List<PhoneContact>> getUniquePhoneContacts() {
        AddressBookApiResponse<List<AddressBook>> apiResponse = new AddressBookApiResponse<>();
        apiResponse.setData(addressBookService.getAllAddressBooks());
        return apiResponse;
    }

}

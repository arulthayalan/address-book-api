package au.com.pwc.addressbook.configuration;

import au.com.pwc.addressbook.entity.AddressBookEntity;
import au.com.pwc.addressbook.model.AddressBook;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TransferObjectMapper {

    AddressBook toAddressBook(AddressBookEntity addressBookEntity);

    AddressBookEntity toAddressBookEntity(AddressBook addressBook);

    List<AddressBook> toAddressBook(Collection<AddressBookEntity> transactions);
}

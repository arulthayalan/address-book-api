package au.com.xyz.addressbook.configuration;

import au.com.xyz.addressbook.entity.AddressBookEntity;
import au.com.xyz.addressbook.model.AddressBook;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TransferObjectMapper {

    AddressBook toAddressBook(AddressBookEntity addressBookEntity);

    AddressBookEntity toAddressBookEntity(AddressBook addressBook);

    List<AddressBook> toAddressBook(Collection<AddressBookEntity> transactions);
}

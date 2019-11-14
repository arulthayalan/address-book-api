package au.com.xyz.addressbook.respository;

import au.com.xyz.addressbook.entity.AddressBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressBookRepository extends JpaRepository<AddressBookEntity, String> {


}

package au.com.pwc.addressbook.respository;

import au.com.pwc.addressbook.entity.AddressBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressBookRepository extends JpaRepository<AddressBookEntity, String> {


}

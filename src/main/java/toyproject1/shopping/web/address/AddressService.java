package toyproject1.shopping.web.address;

import org.springframework.stereotype.Service;
import toyproject1.shopping.domain.entity.Address;

@Service
public class AddressService {

    private Address newAddress;

    public Address createAddress(Address address){
        newAddress = new Address();
        newAddress.setZipcode(address.getZipcode());
        newAddress.setMainAddress(address.getMainAddress());
        newAddress.setDetailAddress(address.getDetailAddress());
        newAddress.setExtraAddress(address.getExtraAddress());

        newAddress.setTotalAddress(newAddress.getZipcode() +" "+ newAddress.getMainAddress()+" "+ newAddress.getDetailAddress()+" "+ newAddress.getExtraAddress());

        return newAddress;
    }
}

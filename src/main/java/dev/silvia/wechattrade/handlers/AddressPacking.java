package dev.silvia.wechattrade.handlers;

import dev.silvia.wechattrade.vo.AddressVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddressPacking {
    public AddressVo StringToAddressVo(String address_str, Integer rank){
        if(address_str != null){
            AddressVo addressVo = new AddressVo();
            String[] address_parts = address_str.split("%");
            addressVo.setReceiverName(address_parts[0]);
            addressVo.setReceiverPhone(address_parts[1]);
            addressVo.setRegion(address_parts[2]);
            addressVo.setAddressDetail(address_parts[3]);
            addressVo.setRank(rank);
            return addressVo;
        }
        return null;
    }

    public List<AddressVo> StringToAddressVo(String address_str, String default_address){
        List<AddressVo> addressVos = new ArrayList<>();
        if(default_address != null){
            addressVos.add(StringToAddressVo(default_address,0));
        }
        if(address_str!=null){
            String[] addresses = address_str.split("#");
            for(int i = 0 ; i < addresses.length; i++){
                addressVos.add(StringToAddressVo(addresses[i], i+1));
            }
        }
        return addressVos;
    }

    public String ObjectToAddress(String name, String phone, String region, String detail){
        String address = name+"%"+phone+"%"+region+"%"+detail;
        return address;
    }
}

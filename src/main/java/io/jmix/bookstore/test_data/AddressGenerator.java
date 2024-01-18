package io.jmix.bookstore.test_data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jmix.bookstore.entity.Address;
import io.jmix.core.DataManager;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

import static io.jmix.bookstore.test_data.data_provider.RandomValues.randomOfList;

@Component("bookstore_AddressGenerator")
public class AddressGenerator {

    protected final ResourceLoader resourceLoader;
    protected final ObjectMapper objectMapper;
    private final DataManager dataManager;

    private static final String ADDRESS_FILE_NAME = "classpath:test-data/addresses-us-all.json";
    private List<AddressEntry> loadedAddresses;


    /**
     *         {
     *             "address1": "1745 T Street Southeast",
     *             "address2": "",
     *             "city": "Washington",
     *             "state": "DC",
     *             "postalCode": "20020",
     *             "coordinates": {
     *                 "lat": 38.867033,
     *                 "lng": -76.979235
     *             }
     *         }
     */
    public record AddressEntryCoordinates(double lat, double lng){}
    public record AddressEntry(String address1, String city, String state, String postalCode, AddressEntryCoordinates coordinates){}
    public record AddressEntries(List<AddressEntry> addresses){}

    public AddressGenerator(ResourceLoader resourceLoader, ObjectMapper objectMapper, DataManager dataManager) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
        this.dataManager = dataManager;
    }


    @PostConstruct
    public void loadAddressesFromFile() {
        loadedAddresses = addresses().addresses();
    }

    public Address generate() {
        return toAddress(
                randomOfList(loadedAddresses)
        );
    }

    private AddressEntries addresses() {
        Resource addressesFile = resourceLoader.getResource(ADDRESS_FILE_NAME);
        try {
            byte[] zipBytes = addressesFile.getInputStream().readAllBytes();
            return objectMapper.readValue(zipBytes, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Error while importing addresses", e);
        }
    }


    private io.jmix.bookstore.entity.Address toAddress(AddressEntry address) {
        io.jmix.bookstore.entity.Address addressEntity = dataManager.create(io.jmix.bookstore.entity.Address.class);
        addressEntity.setCity(address.city());
        addressEntity.setStreet(address.address1());
        addressEntity.setPostCode(address.postalCode());
        addressEntity.setState(address.state());
        addressEntity.setCountry("USA");
        Point point = new GeometryFactory().createPoint(new Coordinate(address.coordinates().lng(), address.coordinates().lat()));
        addressEntity.setPosition(point);

        return addressEntity;
    }

}
